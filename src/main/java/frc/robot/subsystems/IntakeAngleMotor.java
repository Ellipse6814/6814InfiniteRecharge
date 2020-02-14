package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.controller.ArmFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;
import frc.robot.Util.Debugable;

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

    private final int pidIdx = 0;
    public final TalonSRX angleMotor = new TalonSRX(Const.kTableMotorPort);

    private final ArmFeedforward armFeedforward = new ArmFeedforward(Const.kIntakekS, Const.kIntakekCos,
            Const.kIntakekV, Const.kIntakekA);

    private double angleState;

    private IntakeAngleMotor() {
        TableCalculator.getInstance();
        angleState = getEncoderPosition();
        log("Init");
    }

    public void setAngleMotorPercentage(double speed) {
        angleMotor.set(ControlMode.PercentOutput, speed);
    }

    public void setAngle(double degs) {
        if (degs == angleState)
            return;
        angleState = degs;
        angleMotor.set(ControlMode.MotionMagic, degs * Const.kDeg2Rot * Const.kRot2TalonRaw,
                DemandType.ArbitraryFeedForward, getFeedForward());
        log("setSetpoint: " + degs + "degs");
    }

    public double getFeedForward() {
        return armFeedforward.calculate(getEncoderPosition() * Const.kDeg2Rad, getEncoderVelocity() * Const.kDeg2Rad);
    }

    public void resetEncoder(double resetToDeg) {
        int intVal = (int) (resetToDeg * Const.kDeg2Rot * Const.kRot2TalonRaw);
        angleMotor.setSelectedSensorPosition(intVal, 0, 30);
    }

    public double getEncoderPosition() {
        return angleMotor.getSelectedSensorPosition() * Const.kTalonRaw2Rot * Const.kRot2Deg;
    }

    public double getEncoderVelocity() {
        return angleMotor.getSelectedSensorVelocity() * Const.kTalon100Ms2sec * Const.kTalonRaw2Rot * Const.kRot2Deg;
    }

    public boolean onTarget() {
        return Math.abs(getEncoderPosition() - angleState) <= Const.kIntakePositionTolerance
                && Math.abs(getEncoderVelocity()) <= Const.kIntakeVelocityTolerance;
    }

    public void debug() {
        SmartDashboard.putNumber("Setpoint", angleMotor.getClosedLoopTarget() * Const.kDeg2Rot * Const.kRot2TalonRaw);
    }
}