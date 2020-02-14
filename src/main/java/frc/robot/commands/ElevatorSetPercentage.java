package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Logger;

public class ElevatorSetPercentage extends CommandBase {

  private Logger logger = Logger.getInstance();

  private void log(Object msg) {
    logger.log("ElevatorSetPercentage", msg);
  }

  private final Elevator elevator = Elevator.getInstance();
  private double Speed;

  public ElevatorSetPercentage(double Speed) {
    this.Speed = Speed;
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    log("Starting");
    elevator.setSpeed(Speed);
    ;
  }

  @Override
  public void execute() {
    elevator.setSpeed(Speed);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    log("Ended with interrupted = [" + interrupted + "]");
  }
}
