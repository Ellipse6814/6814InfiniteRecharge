package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.ElevatorFeedforward;
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
    public final TalonSRX motor = new TalonSRX(Const.kElevatorMotorPort);

    private ElevatorFeedforward elevatorFeedforward = new ElevatorFeedforward(Const.kElevatorkS, Const.kElevatorkG,
            Const.kElevatorkV, Const.kElevatorkA);

    private double positionState;
    private boolean pistonState;
    private double pistonStateValidAfter;

    public static void main(String[] args) {
        // System.out.println((int) (0.75 * Const.kTableRot2RollerRot *
        // Const.kSec2Talon100Ms * Const.kRot2TalonRaw));
        // System.out.println(506 * Const.kDeg2Rot * Const.kRot2TalonRaw);
    }

    private Elevator() {
        // TODO: put consts in @Link{Const.java}
        piston = new DoubleSolenoid(Const.kElevatorSolonoidPort1, Const.kElevatorSolonoidPort2);
        motor.configFactoryDefault(Const.kCANTimeout);
        motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        motor.setSelectedSensorPosition(0);
        motor.setSensorPhase(true);
        motor.selectProfileSlot(pidIdx, pidIdx);
        motor.config_kF(pidIdx, 0., Const.kCANTimeout);
        motor.config_kP(pidIdx, 0.25, Const.kCANTimeout);
        motor.config_kI(pidIdx, 0.01, Const.kCANTimeout);
        motor.config_kD(pidIdx, 0.0, Const.kCANTimeout);
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

    public boolean onTarget() {
        return Math.abs(getEncoderPosition() - positionState) <= Const.kElevatorPositionTolerance
                && Math.abs(getEncoderVelocity()) <= Const.kElevatorVelocityTolerance;
    }

    public void setPosition(double position) {
        if (position == positionState)
            return;
        positionState = position;
        motor.set(ControlMode.MotionMagic, position * Const.kDeg2Rot * Const.kRot2TalonRaw,
                DemandType.ArbitraryFeedForward, getFeedForward()); // TODO: units!!
        log("setSetpoint: " + position + "degs");
    }

    public double getFeedForward() {
        return elevatorFeedforward.calculate(getEncoderVelocity());// TODO: this needs to be m/s
    }

    public boolean getPiston() {
        return Timer.getFPGATimestamp() >= pistonStateValidAfter ? pistonState : !pistonState;
    }

    public void engageBrake(boolean engage) {
        if (engage == pistonState)// not getPiston() which is the current state
            return; // pistonState is the target state, which is what we want

        if (engage)
            piston.set(Const.kElevatorEngagePistonPos);
        else
            piston.set(Const.kElevatorNOTEngagePistonPos);

        pistonState = engage;
        pistonStateValidAfter = Timer.getFPGATimestamp() + Const.kElevatorPistonMoveDelay;
    }

    public void stopMotor() {
        motor.set(ControlMode.PercentOutput, 0);
    }

    public void debug() {
        SmartDashboard.putNumber("Setpoint", motor.getClosedLoopTarget() * Const.kDeg2Rot * Const.kRot2TalonRaw);
    }
}