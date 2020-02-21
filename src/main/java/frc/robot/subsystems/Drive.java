package frc.robot.subsystems;

import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;
import frc.robot.Util.Debugable;

public class Drive extends SubsystemBase implements Debugable {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("Drive", msg);
    }

    private static Drive instance;

    public static Drive getInstance() {
        if (instance == null)
            instance = new Drive();

        return instance;
    }

    private CANSparkMax leftMaster, leftSlave1, leftSlave2;
    private CANSparkMax rightMaster, rightSlave1, rightSlave2;

    private CANEncoder leftEncoder;
    private CANEncoder rightEncoder;

    private CANPIDController leftPID;
    private CANPIDController rightPID;

    private int gear;

    private Drive() {
        leftMaster = new CANSparkMax(Const.kDriveLeftMotorPort, MotorType.kBrushless);
        leftSlave1 = new CANSparkMax(Const.kDriveLeftMotorPort1, MotorType.kBrushless);
        leftSlave2 = new CANSparkMax(Const.kDriveLeftMotorPort2, MotorType.kBrushless);

        rightMaster = new CANSparkMax(Const.kDriveRightMotorPort, MotorType.kBrushless);
        rightSlave1 = new CANSparkMax(Const.kDriveRightMotorPort1, MotorType.kBrushless);
        rightSlave2 = new CANSparkMax(Const.kDriveRightMotorPort2, MotorType.kBrushless);

        leftMaster.restoreFactoryDefaults();
        leftSlave1.restoreFactoryDefaults();
        leftSlave2.restoreFactoryDefaults();
        rightMaster.restoreFactoryDefaults();
        rightSlave1.restoreFactoryDefaults();
        rightSlave2.restoreFactoryDefaults();

        leftSlave1.follow(leftMaster);
        leftSlave2.follow(leftMaster);
        rightSlave1.follow(rightMaster);
        rightSlave2.follow(rightMaster);

        leftMaster.setInverted(Const.kLeftDriveInverted);
        rightMaster.setInverted(Const.kRightDriveInverted);

        leftEncoder = leftMaster.getAlternateEncoder(AlternateEncoderType.kQuadrature, 4096);
        rightEncoder = rightMaster.getAlternateEncoder(AlternateEncoderType.kQuadrature, 4096);

        leftEncoder.setInverted(Const.kLeftDriveSensorInverted);
        rightEncoder.setInverted(Const.kRightDriveSensorInverted);

        leftPID = leftMaster.getPIDController();
        rightPID = rightMaster.getPIDController();

        leftPID.setFeedbackDevice(leftEncoder);
        rightPID.setFeedbackDevice(rightEncoder);

        log("Inited");
    }

    public void tankDrive(double leftSpd, double rightSpd) {
        leftMaster.set(leftSpd);
        rightMaster.set(rightSpd);
    }

    public void stopMotors() {
        tankDrive(0, 0);
    }

    public void gearUp() {
        gear = Math.min(gear + 1, Const.kDrivePowerGears.length - 1);
        log("Gear up to [" + gear + "]");
    }

    public void gearDown() {
        gear = Math.min(gear - 1, 0);
        log("Gear down to [" + gear + "]");
    }

    public int getGear() {
        return gear;
    }

    public double getLeftPosition() {
        return leftEncoder.getPosition() * Const.kDriveWheelDiameter;
    }

    public double getRightPosition() {
        return rightEncoder.getPosition() * Const.kDriveWheelDiameter;
    }

    public double getLeftVelocity() {
        return leftEncoder.getVelocity() * Const.kDriveWheelDiameter * Const.kMin2Sec;
    }

    public double getRightVelocity() {
        return rightEncoder.getVelocity() * Const.kDriveWheelDiameter * Const.kMin2Sec;
    }

    public void debug() {
        SmartDashboard.putNumber("Drive:gear", gear);
    }
}
