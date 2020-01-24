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
        drive.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}