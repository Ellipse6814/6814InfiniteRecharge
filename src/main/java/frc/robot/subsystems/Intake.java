package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;

public class Intake extends SubsystemBase {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("Intake", msg);
    }

    private static Intake instance;

    public static Intake getInstance() {
        if (instance == null)
            instance = new Intake();
        return instance;
    }

    private final DoubleSolenoid piston;

    private final int pidIdx = 0;
    public final TalonSRX angleMotor = new TalonSRX(Const.kTableMotorPort);
    public final TalonSRX rollerMotor = new TalonSRX(Const.kTableMotorPort);// TODO: move these to the constructor

    private boolean isDeployed = false; // TODO:

    public static void main(String[] args) {
        // System.out.println((int) (0.75 * Const.kTableRot2RollerRot *
        // Const.kSec2Talon100Ms * Const.kRot2TalonRaw));
        // System.out.println(506 * Const.kDeg2Rot * Const.kRot2TalonRaw);
    }

    private Intake() {
        // // TODO: put consts in @Link{Const.java}
        piston = new DoubleSolenoid(Const.kTableSolonoidPort1, Const.kTableSolonoidPort2);
        // motor.configFactoryDefault(Const.kTimeout);
        // motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        // motor.setSelectedSensorPosition(0);
        // motor.setSensorPhase(true);
        // motor.selectProfileSlot(pidIdx, pidIdx);
        // motor.config_kF(pidIdx, 0., Const.kTimeout);
        // motor.config_kP(pidIdx, 0.25, Const.kTimeout);
        // motor.config_kI(pidIdx, 0.01, Const.kTimeout);
        // motor.config_kD(pidIdx, 0.0, Const.kTimeout);
        // motor.config_IntegralZone(pidIdx, 500);
        // motor.configMotionCruiseVelocity(
        // (int) (0.75 * Const.kTableRot2RollerRot * Const.kSec2Talon100Ms *
        // Const.kRot2TalonRaw));
        // motor.configMotionAcceleration(2000);

        log("Init");
    }

    public void setRoller(double speed) {
        rollerMotor.set(ControlMode.PercentOutput, speed);
    }

    public void setAngleMotor(double speed) {
        angleMotor.set(ControlMode.PercentOutput, speed);
    }

    public void resetEncoder(double resetToDeg) {
        int intVal = (int) (resetToDeg * Const.kDeg2Rot * Const.kRot2TalonRaw);
        angleMotor.setSelectedSensorPosition(intVal, pidIdx, 30);
    }

    public double getEncoderPosition() {
        return angleMotor.getSelectedSensorPosition() * Const.kTalonRaw2Rot * Const.kRot2Deg;
    }

    public double getEncoderVelocity() {
        return angleMotor.getSelectedSensorVelocity() * Const.kTalon100Ms2sec * Const.kTalonRaw2Rot * Const.kRot2Deg;
    }

    public void setSetpoint(double degs) {
        angleMotor.set(ControlMode.MotionMagic, degs * Const.kDeg2Rot * Const.kRot2TalonRaw);
        log("setSetpoint: " + degs + "degs");
    }

    public boolean deployed() {
        return isDeployed;
    }

    public void engageRoller(boolean engageRoller) {
        if (engageRoller) {
            piston.set(Value.kForward);
        } else {
            piston.set(Value.kReverse);
        }
    }

    public void debug() {
        SmartDashboard.putNumber("Setpoint", angleMotor.getClosedLoopTarget() * Const.kDeg2Rot * Const.kRot2TalonRaw);
    }
}