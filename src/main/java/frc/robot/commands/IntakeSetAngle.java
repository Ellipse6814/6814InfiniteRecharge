package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PerpetualCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Logger;

public class IntakeSetAngle extends CommandBase {

  private Logger logger = Logger.getInstance();

  private void log(Object msg) {
    logger.log("IntakeSetAngle", msg);
  }

  private final Intake intake = Intake.getInstance();
  private double angle;

  public IntakeSetAngle(double angle) {
    this.angle = angle;
  }

  @Override
  public void initialize() {
    log("Starting");
    intake.setAngle(angle);
  }

  @Override
  public void execute() {
    intake.setAngle(angle);
  }

  @Override
  public boolean isFinished() {
    return intake.onTarget();
  }

  @Override
  public void end(boolean interrupted) {
    log("Ended with interrupted = [" + interrupted + "]");
  }
}
