package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ElevatorBrake;
import frc.robot.subsystems.Logger;

public class ElevatorSetPiston extends CommandBase {

  private Logger logger = Logger.getInstance();

  private void log(Object msg) {
    logger.log("ElevatorSetPiston", msg);
  }

  private final ElevatorBrake elevatorBrake = ElevatorBrake.getInstance();
  private boolean engageBrake;

  public ElevatorSetPiston(boolean engageBrake) {
    this.engageBrake = engageBrake;
    addRequirements(elevatorBrake);
  }

  @Override
  public void initialize() {
    log("Starting");
    elevatorBrake.engageBrake(engageBrake);
  }

  @Override
  public boolean isFinished() {
    return elevatorBrake.getPiston() == engageBrake;
  }

  @Override
  public void end(boolean interrupted) {
    log("Ended with interrupted = [" + interrupted + "]");
  }
}
