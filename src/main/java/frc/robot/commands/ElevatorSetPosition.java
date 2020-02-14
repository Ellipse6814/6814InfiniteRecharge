package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ElevatorMotor;
import frc.robot.subsystems.Logger;

public class ElevatorSetPosition extends CommandBase {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("ElevatorSetPosition", msg);
    }

    private final ElevatorMotor elevatorMotor = ElevatorMotor.getInstance();
    private double position;

    public ElevatorSetPosition(double position) {
        this.position = position;
        addRequirements(elevatorMotor);
    }

    @Override
    public void initialize() {
        log("Starting");
        elevatorMotor.setPosition(position);
    }

    @Override
    public void execute() {
        elevatorMotor.setPosition(position);
    }

    @Override
    public boolean isFinished() {
        return elevatorMotor.onTarget();
    }

    @Override
    public void end(boolean interrupted) {
        log("Ended with interrupted = [" + interrupted + "]");
    }
}
