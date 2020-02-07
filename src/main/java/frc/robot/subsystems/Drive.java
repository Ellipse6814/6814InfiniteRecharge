package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;

public class Drive extends SubsystemBase {
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

    private CANSparkMax leftMaster = new CANSparkMax(Const.kDriveLeftMotorPort, MotorType.kBrushless);
    private CANSparkMax leftSlave1 = new CANSparkMax(Const.kDriveLeftMotorPort1, MotorType.kBrushless);
    private CANSparkMax leftSlave2 = new CANSparkMax(Const.kDriveLeftMotorPort2, MotorType.kBrushless);

    private CANSparkMax rightMaster = new CANSparkMax(Const.kDriveRightMotorPort, MotorType.kBrushless);
    private CANSparkMax rightSlave1 = new CANSparkMax(Const.kDriveRightMotorPort1, MotorType.kBrushless);
    private CANSparkMax rightSlave2 = new CANSparkMax(Const.kDriveRightMotorPort2, MotorType.kBrushless);

    private Drive() {
        leftSlave1.follow(leftMaster);
        leftSlave2.follow(leftMaster);
        rightSlave1.follow(rightMaster);
        rightSlave2.follow(rightMaster);

        leftMaster.setInverted(Const.kLeftDriveInverted);
        rightMaster.setInverted(Const.kRightDriveInverted);

        log("Inited");
    }

    public void tankDrive(double leftSpd, double rightSpd) {
        leftMaster.set(leftSpd);
        rightMaster.set(rightSpd);
    }

    public void stopMotors() {
        tankDrive(0, 0);
    }

    @Override
    public void periodic() {

    }

    public void debug() {

    }
}
