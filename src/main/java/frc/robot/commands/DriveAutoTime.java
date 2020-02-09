package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Logger;

public class DriveAutoTime extends CommandBase {
  private Logger logger = Logger.getInstance();

  private void log(Object msg) {
    logger.log("DriveAutoTime", msg);
  }

  private final Drive drive = Drive.getInstance();
  private double startingTime = 0;
  private final double duration, speed;
  private boolean isFinished = false;

  public DriveAutoTime(double duration, double speed) {
    this.duration = duration;
    this.speed = speed;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    log("Starting");
    isFinished = false;
    startingTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drive.tankDrive(speed, speed);

    double currentTime = Timer.getFPGATimestamp();
    double dt = currentTime - startingTime;

    if (dt >= duration)
      isFinished = true;

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.stopMotors();
    log("Ended with interrupted = [" + interrupted + "]");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
