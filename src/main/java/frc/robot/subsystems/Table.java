package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;
import frc.robot.subsystems.TableColorDetector.TableColor;

public class Table extends SubsystemBase {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("Table", msg);
    }

    private static Table instance;

    public static Table getInstance() {
        if (instance == null)
            instance = new Table();
        return instance;
    }

    private final DoubleSolenoid piston;

    private final int pidIdx = 0;
    public final TalonSRX motor = new TalonSRX(Const.kTableMotorPort);
    private final TableColorDetector tableColorDetector = TableColorDetector.getInstance();

    private int direction;
    private double degs;
    private double prevStableEncoderVal;

    private boolean pistonState;
    private double pistonStateValidAfter;

    public static void main(String[] args) {
        // System.out.println((int) (0.75 * Const.kTableRot2RollerRot *
        // Const.kSec2Talon100Ms * Const.kRot2TalonRaw));
        // System.out.println(506 * Const.kDeg2Rot * Const.kRot2TalonRaw);
    }

    private Table() {
        // TODO: put consts in @Link{Const.java}
        piston = new DoubleSolenoid(Const.kTableSolonoidPort1, Const.kTableSolonoidPort2);
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

        pistonState = piston.get().equals(Const.kIntakeEngagePistonPos);
        pistonStateValidAfter = Timer.getFPGATimestamp();
        log("Init");
    }

    public boolean getDeltaEdge() {
        return tableColorDetector.hasNewState();
    }

    public TableColor peekColor() {
        return tableColorDetector.getTableColor();
    }

    public void stableSpinDegs(double degs) {
        this.degs = degs;
        direction = getSign(degs);
        initColorSensor(direction);
        resetEncoder(0);
        setSetpoint(degs);
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

    public boolean onTarget() {
        if (Math.abs(degs - getEncoderPosition()) < 10 && Math.abs(getEncoderVelocity()) < 5) {
            log("met tolerance " + Math.abs(degs - getEncoderPosition()) + "deg");
            log("met tolerance " + Math.abs(getEncoderVelocity()) + "deg/s");
            return true;
        }
        return false;
    }

    public void initColorSensor(int direction) {
        tableColorDetector.init(direction);
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

    public boolean getPiston() {
        return Timer.getFPGATimestamp() >= pistonStateValidAfter ? pistonState : !pistonState;
    }

    public void engageTable(boolean engage) {
        if (engage == pistonState)// not getPiston() which is the current state
            return; // pistonState is the target state, which is what we want

        if (engage)
            piston.set(Const.kTableEngagePistonPos);
        else
            piston.set(Const.kTableNOTEngagePistonPos);

        pistonState = engage;
        pistonStateValidAfter = Timer.getFPGATimestamp() + Const.kTablePistonMoveDelay;
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