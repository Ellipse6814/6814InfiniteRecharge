package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {

    private static Drive instance;

    public static Drive getInstance() {
        if (instance == null)
            instance = new Drive();

        return instance;
    }

    // TODO: change IDs
    private CANSparkMax leftMaster = new CANSparkMax(1, MotorType.kBrushless);
    private CANSparkMax leftSlave1 = new CANSparkMax(2, MotorType.kBrushless);
    private CANSparkMax leftSlave2 = new CANSparkMax(3, MotorType.kBrushless);
    private CANSparkMax rightMaster = new CANSparkMax(4, MotorType.kBrushless);
    private CANSparkMax rightSlave1 = new CANSparkMax(5, MotorType.kBrushless);
    private CANSparkMax rightSlave2 = new CANSparkMax(6, MotorType.kBrushless);

    private Drive() {
        leftSlave1.follow(leftMaster);
        leftSlave2.follow(leftMaster);
        rightSlave1.follow(rightMaster);
        rightSlave2.follow(rightMaster);

        // TODO: Check if inverted settings are correct
        leftMaster.setInverted(false);
        rightMaster.setInverted(true);

        System.out.println("Drive: Inited");
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
}
