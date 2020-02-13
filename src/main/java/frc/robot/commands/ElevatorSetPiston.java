package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Logger;

public class ElevatorSetPiston extends CommandBase {

  private Logger logger = Logger.getInstance();

  private void log(Object msg) {
    logger.log("ElevatorSetPiston", msg);
  }

  private final Elevator elevator = Elevator.getInstance();
  private boolean engageBrake;

  public ElevatorSetPiston(boolean engageBrake) {
    this.engageBrake = engageBrake;
  }

  @Override
  public void initialize() {
    log("Starting");
    elevator.engageBrake(engageBrake);
  }

  @Override
  public boolean isFinished() {
    return elevator.getPiston() == engageBrake;
  }

  @Override
  public void end(boolean interrupted) {
    log("Ended with interrupted = [" + interrupted + "]");
  }
}
