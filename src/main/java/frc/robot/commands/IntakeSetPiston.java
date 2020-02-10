package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Const;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Logger;

public class IntakeSetPiston extends CommandBase {

  private Logger logger = Logger.getInstance();

  private void log(Object msg) {
    logger.log("IntakeSetPiston", msg);
  }

  private final Intake intake = Intake.getInstance();
  private boolean engageIntake;

  private boolean isFinished;
  private double startTime;

  public IntakeSetPiston(boolean engageIntake) {
    this.engageIntake = engageIntake;
  }

  @Override
  public void initialize() {
    log("Starting");
    if (intake.getEngaged() == engageIntake) {
      isFinished = true;
      log("Engage status [" + engageIntake + "] already satisfied, exiting");
      return;
    }

    isFinished = false;
    startTime = Timer.getFPGATimestamp();
    intake.engageIntake(engageIntake);
  }

  @Override
  public void execute() {
    isFinished = (Timer.getFPGATimestamp() - startTime) >= Const.kTableEngageDelaySec;
  }

  @Override
  public boolean isFinished() {
    return isFinished;
  }

  @Override
  public void end(boolean interrupted) {
    log("Ended with interrupted = [" + interrupted + "]");
  }
}
