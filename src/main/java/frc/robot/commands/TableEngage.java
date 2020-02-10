package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Const;
import frc.robot.subsystems.Logger;
import frc.robot.subsystems.Table;

public class TableEngage extends CommandBase {

  private Logger logger = Logger.getInstance();

  private void log(Object msg) {
    logger.log("TableEngage", msg);
  }

  private final Table tableSpinner = Table.getInstance();
  private boolean engageRoller;

  private boolean isFinished;
  private double startTime;

  public TableEngage(boolean engageRoller) {
    this.engageRoller = engageRoller;
  }

  @Override
  public void initialize() {
    log("Starting");
    isFinished = false;
    startTime = Timer.getFPGATimestamp();
    tableSpinner.engageRoller(engageRoller);
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
