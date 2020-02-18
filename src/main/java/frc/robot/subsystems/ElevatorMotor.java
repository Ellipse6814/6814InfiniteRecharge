package frc.robot.subsystems;

import java.util.Arrays;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.controller.ElevatorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;
import frc.robot.Util.Debugable;
import frc.robot.Util.TalonHelper;

public class ElevatorMotor extends SubsystemBase implements Debugable {

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
    public final TalonSRX masterMotor, slaveMotor1, slaveMotor2;

    private ElevatorFeedforward elevatorFeedforward = new ElevatorFeedforward(Const.kElevatorkS, Const.kElevatorkG,
            Const.kElevatorkV, Const.kElevatorkA);

    private double positionState;

    private ElevatorMotor() {
        masterMotor = TalonHelper.createTalon(Const.kElevatorMotorPort, Const.kElevatorInverted);
        slaveMotor1 = TalonHelper.createSlaveTalon(Const.kElevatorMotorSlavePort1, masterMotor);
        slaveMotor2 = TalonHelper.createSlaveTalon(Const.kElevatorMotorSlavePort2, masterMotor);

        TalonHelper.configMagEncoder(masterMotor, Const.kElevatorSensorInverted);

        TalonHelper.configPID(masterMotor, pidIdx, Const.kElevatorkP, Const.kElevatorkI, Const.kElevatorkD, 0, 0);
        TalonHelper.configMotionMagic(masterMotor, Const.kElevatorMaxVel, Const.kElevatorMaxAcc);

        TalonHelper.configNeutralMode(Arrays.asList(masterMotor, slaveMotor1, slaveMotor2), NeutralMode.Brake);

        TalonHelper.configDeadband(masterMotor, Const.kMotorDeadband);
        TalonHelper.configDeadband(slaveMotor1, Const.kMotorDeadband);
        TalonHelper.configDeadband(slaveMotor2, Const.kMotorDeadband);

        TalonHelper.configCurrentLimit(masterMotor, Const.kElevatorCurrentLimit);
        TalonHelper.configCurrentLimit(slaveMotor1, Const.kElevatorCurrentLimit);
        TalonHelper.configCurrentLimit(slaveMotor2, Const.kElevatorCurrentLimit);

        log("Init");
    }

    public void setPosition(double position) {
        if (position == positionState)
            return;
        positionState = position;
        masterMotor.set(ControlMode.MotionMagic, position * Const.kDeg2Rot * Const.kRot2TalonRaw,
                DemandType.ArbitraryFeedForward, getFeedForward()); // TODO: units!!
        log("setSetpoint: " + position + "degs");
    }

    public void setSpeed(double speed) {
        masterMotor.set(ControlMode.PercentOutput, speed);
    }

    public double getFeedForward() {
        return elevatorFeedforward.calculate(getEncoderVelocity());// TODO: this needs to be m/s
    }

    public void resetEncoder(double resetToDeg) {
        int intVal = (int) (resetToDeg * Const.kDeg2Rot * Const.kRot2TalonRaw);
        masterMotor.setSelectedSensorPosition(intVal, pidIdx, 30);// TODO: put this in TalonHelper
    }

    public double getEncoderPosition() {
        return masterMotor.getSelectedSensorPosition() * Const.kTalonRaw2Rot * Const.kElevatorGearRatio
                * Const.kRot2Deg;
    }

    public double getEncoderVelocity() {
        return masterMotor.getSelectedSensorVelocity() * Const.kTalon100Ms2sec * Const.kTalonRaw2Rot
                * Const.kElevatorGearRatio * Const.kRot2Deg;
    }

    public boolean onTarget() {
        return Math.abs(getEncoderPosition() - positionState) <= Const.kElevatorPositionTolerance
                && Math.abs(getEncoderVelocity()) <= Const.kElevatorVelocityTolerance;
    }

    public void debug() {
        SmartDashboard.putNumber("Setpoint",
                masterMotor.getClosedLoopTarget() * Const.kDeg2Rot * Const.kElevatorGearRatio * Const.kRot2TalonRaw);
    }
}