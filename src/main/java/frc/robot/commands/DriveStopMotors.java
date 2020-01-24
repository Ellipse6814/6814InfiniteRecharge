package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class DriveStopMotors extends CommandBase {

    private Drive drive = Drive.getInstance();

    public DriveStopMotors() {
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        System.out.println("Starting CMD: DriveStopMotors");
        drive.stopMotors();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}