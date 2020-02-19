package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;
import frc.robot.Util.Debugable;
import frc.robot.Util.TalonHelper;
import frc.robot.subsystems.TableColorDetector.TableColor;

public class TableMotor extends SubsystemBase implements Debugable {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("TableMotor", msg);
    }

    private static TableMotor instance;

    public static TableMotor getInstance() {
        if (instance == null)
            instance = new TableMotor();
        return instance;
    }

    private final int pidIdx = 0;
    public final TalonSRX motor = new TalonSRX(Const.kTableMotorPort);
    private final TableColorDetector tableColorDetector = TableColorDetector.getInstance();
    private SimpleMotorFeedforward simpleMotorFeedforward = new SimpleMotorFeedforward(Const.kTablekS, Const.kTablekV,
            Const.kTablekA);

    private int direction;
    private double degs;
    private double prevStableEncoderVal;

    private TableMotor() {
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

    public void setSpeed(double speed) {
        motor.set(ControlMode.PercentOutput, speed);
    }

    public boolean getDeltaEdge() {
        return tableColorDetector.hasNewState();
    }

    public TableColor peekColor() {
        return tableColorDetector.getTableColor();
    }

    public void initColorSensor(int direction) {
        tableColorDetector.init(direction);
    }

    public void stableSpinDegs(double degs) {
        this.degs = degs;
        direction = getSign(degs);
        initColorSensor(direction);
        resetEncoder(0);
        setSetpoint(degs);
    }

    public void setSetpoint(double degs) {
        motor.set(ControlMode.MotionMagic, degs * Const.kDeg2Rot * Const.kRot2TalonRaw, DemandType.ArbitraryFeedForward,
                getFeedForward());
        log("setSetpoint: " + degs + "degs");
    }

    public double getFeedForward() {
        return simpleMotorFeedforward.calculate(getEncoderVelocity());// TODO: this needs to be m/s
    }

    public void updateColorSensorComplementaryFilter() {
        boolean edgeChanged = getDeltaEdge();
        if (edgeChanged) {
            prevStableEncoderVal += Const.kDeltaDegreesOnRoller * direction;
            log("wrongEncoderVal: " + motor.getSelectedSensorPosition() * Const.kTalonRaw2Rot * Const.kRot2Deg);
            log("prevStableEncoderVal: " + prevStableEncoderVal);

            double newSetpoint = getEncoderPosition() + degs - prevStableEncoderVal;
            setSetpoint(newSetpoint);
        }
    }

    public void resetEncoder(double resetToDeg) {
        int intVal = (int) (resetToDeg * Const.kDeg2Rot * Const.kRot2TalonRaw);
        TalonHelper.resetEncoder(motor, pidIdx, intVal);
        // log("Should reset encoder to: " + intVal);
        // motor.setSelectedSensorPosition(intVal, pidIdx, 30);
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
        if (Math.abs(degs - getEncoderPosition()) < 10 && Math.abs(getEncoderVelocity()) < 5) {
            log("met tolerance " + Math.abs(degs - getEncoderPosition()) + "deg");
            log("met tolerance " + Math.abs(getEncoderVelocity()) + "deg/s");
            return true;
        }
        return false;
    }

    public void debug() {
        SmartDashboard.putNumber("Setpoint", motor.getClosedLoopTarget() * Const.kDeg2Rot * Const.kRot2TalonRaw);
    }

    private int getSign(double a) {
        if (a > 0)
            return 1;
        if (a < 0)
            return -1;
        return 0;
    }
}