package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ElevatorMotor;
import frc.robot.subsystems.Logger;

public class ElevatorSetPercentage extends CommandBase {

  private Logger logger = Logger.getInstance();

  private void log(Object msg) {
    logger.log("ElevatorSetPercentage", msg);
  }

  private final ElevatorMotor elevatorMotor = ElevatorMotor.getInstance();
  private double Speed;

  public ElevatorSetPercentage(double Speed) {
    this.Speed = Speed;
    addRequirements(elevatorMotor);
  }

  @Override
  public void initialize() {
    log("Starting");
    elevatorMotor.setSpeed(Speed);
  }

  @Override
  public void execute() {
    elevatorMotor.setSpeed(Speed);
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
