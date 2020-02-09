package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.DriveCalculator;
import frc.robot.subsystems.Logger;

public class DriveTeleOp extends CommandBase {
  private Logger logger = Logger.getInstance();

  private void log(Object msg) {
    logger.log("DriveTeleOp", msg);
  }

  private final Drive drive = Drive.getInstance();
  private final DoubleSupplier joystickAxis1, joystickAxis4;
  private final DriveCalculator driveCalculator = new DriveCalculator();

  public DriveTeleOp(DoubleSupplier joystickAxis1, DoubleSupplier joystickAxis4) {
    this.joystickAxis1 = joystickAxis1;
    this.joystickAxis4 = joystickAxis4;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    log("Starting");
    driveCalculator.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = -joystickAxis1.getAsDouble();
    double turn = joystickAxis4.getAsDouble();

    double[] outputs = driveCalculator.calculate(speed, turn, drive.getGear());
    drive.tankDrive(outputs[0], outputs[1]);
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
    return false;
  }
}
