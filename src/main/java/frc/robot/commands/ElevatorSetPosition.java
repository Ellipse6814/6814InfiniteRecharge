package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Logger;

public class ElevatorSetPosition extends CommandBase {

  private Logger logger = Logger.getInstance();

  private void log(Object msg) {
    logger.log("ElevatorSetPosition", msg);
  }

  private final Elevator elevator = Elevator.getInstance();
  private double position;

  public ElevatorSetPosition(double position) {
    this.position = position;
  }

  @Override
  public void initialize() {
    log("Starting");
    elevator.setPosition(position);;
  }

  @Override
  public void execute() {
    elevator.setPosition(position);
  }

  @Override
  public boolean isFinished() {
    return elevator.onTarget();
  }

  @Override
  public void end(boolean interrupted) {
    log("Ended with interrupted = [" + interrupted + "]");
  }
}
