package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.controller.ArmFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;
import frc.robot.Util.Debugable;
import frc.robot.Util.TalonHelper;
import frc.robot.subsystems.LED.LEDState;

public class IntakeAngleMotor extends SubsystemBase implements Debugable {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("IntakeAngleMotor", msg);
    }

    private static IntakeAngleMotor instance;

    public static IntakeAngleMotor getInstance() {
        if (instance == null)
            instance = new IntakeAngleMotor();
        return instance;
    }

    public final TalonSRX motor;
    public final DigitalInput resetLimitSwitch = new DigitalInput(Const.kIntakeLimitSwitchPort);

    private final ArmFeedforward armFeedforward = new ArmFeedforward(Const.kIntakekS, Const.kIntakekCos,
            Const.kIntakekV, Const.kIntakekA);
    private final LED led = LED.getInstance();

    private double angleState;
    private boolean safe;

    private IntakeAngleMotor() {
        safe = false;
        led.require(LEDState.Red);

        motor = TalonHelper.createTalon(Const.kIntakeAngleMotorPort, Const.kIntakeAngleMotorInverted);

        TalonHelper.configMagEncoder(motor, Const.kIntakeAngleMotorSensorInverted);

        TalonHelper.configPID(motor, Const.kIntakePIDIdx, Const.kIntakekP, Const.kIntakekI, Const.kIntakekD, 0, 0);
        TalonHelper.configMotionMagic(motor, Const.kIntakeMaxVel, Const.kIntakeMaxAcc);

        TalonHelper.configNeutralMode(motor, NeutralMode.Brake);

        TalonHelper.configDeadband(motor, Const.kMotorDeadband);

        TalonHelper.configCurrentLimit(motor, Const.kIntakeCurrentLimit);

        TableCalculator.getInstance();
        angleState = getEncoderPosition();
        log("Init");
    }

    public void setSpeed(double speed) {
        motor.set(ControlMode.PercentOutput, speed);
    }

    public void setAngle(double degs) {
        if (!safe) {
            log("NOT SAFE, NOT SETTING POSITION");
            return;
        }

        if (degs == angleState)
            return;
        angleState = degs;
        motor.set(ControlMode.MotionMagic, degs * Const.kDeg2Rot * Const.kRot2TalonRaw, DemandType.ArbitraryFeedForward,
                getFeedForward());
        log("setSetpoint: " + degs + "degs");
    }

    public double getFeedForward() {
        return armFeedforward.calculate(getEncoderPosition() * Const.kDeg2Rad, getEncoderVelocity() * Const.kDeg2Rad);
    }

    public void resetEncoder(double resetToDeg) {
        int intVal = (int) (resetToDeg * Const.kDeg2Rot * Const.kRot2TalonRaw);
        TalonHelper.resetEncoder(motor, Const.kIntakePIDIdx, intVal);
    }

    private void updateSafety() {
        if (safe == false) {
            led.clearRequire(LEDState.Red);
            safe = true;
        }
    }

    public boolean getSafe() {
        return safe;
    }

    public double getEncoderPosition() {
        return motor.getSelectedSensorPosition() * Const.kTalonRaw2Rot * Const.kIntakeGearRatio * Const.kRot2Deg;
    }

    public double getEncoderVelocity() {
        return motor.getSelectedSensorVelocity() * Const.kTalon100Ms2sec * Const.kTalonRaw2Rot * Const.kIntakeGearRatio
                * Const.kRot2Deg;
    }

    public boolean onTarget() {
        return Math.abs(getEncoderPosition() - angleState) <= Const.kIntakePositionTolerance
                && Math.abs(getEncoderVelocity()) <= Const.kIntakeVelocityTolerance;
    }

    public boolean getResetLimitSwitch() {
        return resetLimitSwitch.get();
    }

    public boolean tryToLimitSwitchReset() {
        if (getResetLimitSwitch()) {
            resetEncoder(Const.kIntakeLimitSwitchEncoderPosition);
            updateSafety();
            return true;
        }
        return false;
    }

    public void debug() {
        SmartDashboard.putNumber("IntakeAngleMotor:angleState", angleState);
        SmartDashboard.putBoolean("IntakeAngleMotor:safe", safe);
        SmartDashboard.putNumber("IntakeAngleMotor:getEncoderPosition()", getEncoderPosition());
        SmartDashboard.putNumber("IntakeAngleMotor:getEncoderVelocity()", getEncoderVelocity());
        SmartDashboard.putBoolean("IntakeAngleMotor:onTarget()", onTarget());
        SmartDashboard.putBoolean("IntakeAngleMotor:getResetLimitSwitch()", getResetLimitSwitch());
    }
}