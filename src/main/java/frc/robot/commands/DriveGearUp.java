package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Logger;

public class DriveGearUp extends CommandBase {
    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("DriveGearUp", msg);
    }

    private Drive drive = Drive.getInstance();

    public DriveGearUp() {
    }

    @Override
    public void initialize() {
        log("Starting");
        drive.gearUp();
    }

    @Override
    public void end(boolean interrupted) {
        log("Ended with interrupted = [" + interrupted + "]");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}