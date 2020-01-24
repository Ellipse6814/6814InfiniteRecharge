package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class DriveTeleOp extends CommandBase {
  private final Drive drive = Drive.getInstance();
  private final DoubleSupplier joystickAxis1, joystickAxis4;

  public DriveTeleOp(DoubleSupplier joystickAxis1, DoubleSupplier joystickAxis4) {
    this.joystickAxis1 = joystickAxis1;
    this.joystickAxis4 = joystickAxis4;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Starting CMD: DriveTeleOp");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = -joystickAxis1.getAsDouble() * 0.6;
    double turn = joystickAxis4.getAsDouble() * 0.3;

    double left = speed + turn;
    double right = speed - turn;

    drive.tankDrive(left, right);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.stopMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
