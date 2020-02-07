package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;

public class Elevator extends SubsystemBase {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("Elevator", msg);
    }

    private static Elevator instance;

    public static Elevator getInstance() {
        if (instance == null)
            instance = new Elevator();
        return instance;
    }

    private final DoubleSolenoid piston;

    private final int pidIdx = 0;
    public final TalonSRX motor = new TalonSRX(Const.kTableMotorPort);

    public static void main(String[] args) {
        // System.out.println((int) (0.75 * Const.kTableRot2RollerRot *
        // Const.kSec2Talon100Ms * Const.kRot2TalonRaw));
        // System.out.println(506 * Const.kDeg2Rot * Const.kRot2TalonRaw);
    }

    private Elevator() {
        // TODO: put consts in @Link{Const.java}
        piston = new DoubleSolenoid(Const.kElevatorSolonoidPort1, Const.kElevatorSolonoidPort2);
        motor.configFactoryDefault(Const.kTimeout);
        motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        motor.setSelectedSensorPosition(0);
        motor.setSensorPhase(true);
        motor.selectProfileSlot(pidIdx, pidIdx);
        motor.config_kF(pidIdx, 0., Const.kTimeout);
        motor.config_kP(pidIdx, 0.25, Const.kTimeout);
        motor.config_kI(pidIdx, 0.01, Const.kTimeout);
        motor.config_kD(pidIdx, 0.0, Const.kTimeout);
        motor.config_IntegralZone(pidIdx, 500);
        motor.configMotionCruiseVelocity(
                (int) (0.75 * Const.kTableRot2RollerRot * Const.kSec2Talon100Ms * Const.kRot2TalonRaw));
        motor.configMotionAcceleration(2000);

        log("Init");
    }

    public void resetEncoder(double resetToDeg) {
        int intVal = (int) (resetToDeg * Const.kDeg2Rot * Const.kRot2TalonRaw);
        // log("Should reset encoder to: " + intVal);
        motor.setSelectedSensorPosition(intVal, pidIdx, 30);
        // log(errorVal.toString());
        // log("Encoder actually reset to: " +
        // motor.getSelectedSensorPosition());

    }

    public double getEncoderPosition() {
        return motor.getSelectedSensorPosition() * Const.kTalonRaw2Rot * Const.kRot2Deg;
    }

    public double getEncoderVelocity() {
        return motor.getSelectedSensorVelocity() * Const.kTalon100Ms2sec * Const.kTalonRaw2Rot * Const.kRot2Deg;
    }

    public void setSetpoint(double degs) {
        motor.set(ControlMode.MotionMagic, degs * Const.kDeg2Rot * Const.kRot2TalonRaw);
        log("setSetpoint: " + degs + "degs");
    }

    public void engageRoller(boolean engageRoller) {
        if (engageRoller) {
            piston.set(Value.kForward);
        } else {
            piston.set(Value.kReverse);
        }
    }

    public void stopMotor() {
        motor.set(ControlMode.PercentOutput, 0);
    }

    public void debug() {
        SmartDashboard.putNumber("Setpoint", motor.getClosedLoopTarget() * Const.kDeg2Rot * Const.kRot2TalonRaw);
    }
}