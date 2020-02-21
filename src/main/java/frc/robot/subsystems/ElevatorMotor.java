package frc.robot.subsystems;

import java.util.Arrays;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.controller.ElevatorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;
import frc.robot.Util.Debugable;
import frc.robot.Util.TalonHelper;
import frc.robot.commands.ElevatorResetSequence;

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

    public final TalonSRX masterMotor, slaveMotor1, slaveMotor2;
    public final DigitalInput resetLimitSwitch = new DigitalInput(Const.kIntakeLimitSwitchPort);

    private ElevatorFeedforward elevatorFeedforward = new ElevatorFeedforward(Const.kElevatorkS, Const.kElevatorkG,
            Const.kElevatorkV, Const.kElevatorkA);

    private double positionState;
    private boolean safe;

    private ElevatorMotor() {
        safe = false;

        masterMotor = TalonHelper.createTalon(Const.kElevatorMotorPort, Const.kElevatorInverted);
        slaveMotor1 = TalonHelper.createSlaveTalon(Const.kElevatorMotorSlavePort1, masterMotor,
                Const.kElevatorSlave1Inverted);
        slaveMotor2 = TalonHelper.createSlaveTalon(Const.kElevatorMotorSlavePort2, masterMotor,
                Const.kElevatorSlave2Inverted);

        TalonHelper.configMagEncoder(masterMotor, Const.kElevatorSensorInverted);

        TalonHelper.configPID(masterMotor, Const.kElevatorPIDIdx, Const.kElevatorkP, Const.kElevatorkI,
                Const.kElevatorkD, 0, 0);
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
        if (!safe) {
            new ElevatorResetSequence().schedule();
            log("NOT SAFE, not setting setpoint, trying to start the elevator reset command");
            return;
        }

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
        return elevatorFeedforward.calculate(getEncoderVelocity());
    }

    public void resetEncoder(double resetToDeg) {
        int intVal = (int) (resetToDeg * Const.kDeg2Rot * Const.kRot2TalonRaw);
        TalonHelper.resetEncoder(masterMotor, Const.kElevatorPIDIdx, intVal);
        updateSafety();
    }

    private void updateSafety() {
        if (safe == false) {
            safe = true;
        }
    }

    public boolean getSafe() {
        return safe;
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

    public boolean getResetLimitSwitch() {
        return resetLimitSwitch.get();
    }

    public void debug() {
        SmartDashboard.putNumber("ElevatorMotor:positionState", positionState);
        SmartDashboard.putBoolean("ElevatorMotor:safe", safe);
        SmartDashboard.putNumber("ElevatorMotor:getEncoderPosition()", getEncoderPosition());
        SmartDashboard.putNumber("ElevatorMotor:getEncoderVelocity()", getEncoderVelocity());
        SmartDashboard.putBoolean("ElevatorMotor:onTarget()", onTarget());
    }
}