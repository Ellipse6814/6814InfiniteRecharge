package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.controller.ElevatorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;
import frc.robot.Util.Debugable;

public class ElevatorMotor extends SubsystemBase implements Debugable{

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("ElevatorMotor", msg);
    }

    private static ElevatorMotor instance;

    public static ElevatorMotor getInstance() {
        if (instance == null)
            instance = new ElevatorMotor();
        return instance;
    }

    private final int pidIdx = 0;
    public final TalonSRX motor = new TalonSRX(Const.kElevatorMotorPort);

    private ElevatorFeedforward elevatorFeedforward = new ElevatorFeedforward(Const.kElevatorkS, Const.kElevatorkG,
            Const.kElevatorkV, Const.kElevatorkA);

    private double positionState;

    private ElevatorMotor() {
        log("Init");
    }

    public void setPosition(double position) {
        if (position == positionState)
            return;
        positionState = position;
        motor.set(ControlMode.MotionMagic, position * Const.kDeg2Rot * Const.kRot2TalonRaw,
                DemandType.ArbitraryFeedForward, getFeedForward()); // TODO: units!!
        log("setSetpoint: " + position + "degs");
    }

    public void setSpeed(double speed) {
        motor.set(ControlMode.PercentOutput, speed);
    }

    public double getFeedForward() {
        return elevatorFeedforward.calculate(getEncoderVelocity());// TODO: this needs to be m/s
    }

    public void resetEncoder(double resetToDeg) {
        int intVal = (int) (resetToDeg * Const.kDeg2Rot * Const.kRot2TalonRaw);
        motor.setSelectedSensorPosition(intVal, pidIdx, 30);// TODO: put this in TalonHelper
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

    public void debug() {
        SmartDashboard.putNumber("Setpoint", motor.getClosedLoopTarget() * Const.kDeg2Rot * Const.kRot2TalonRaw);
    }
}