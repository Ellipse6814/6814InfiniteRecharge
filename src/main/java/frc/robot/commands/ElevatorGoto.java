package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Logger;

public class ElevatorGoto extends SequentialCommandGroup {

    private boolean brake;
    private double position;

    public ElevatorGoto(double position, boolean brake) {
        this.brake = brake;
        this.position = position;
        addCommands(//
                new ElevatorSetPiston(false), //
                new ElevatorSetPosition(position), //
                new ElevatorSetPiston(brake)//
        );
    }

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("ElevatorGoto", msg);
    }

    @Override
    public void initialize() {
        log("Starting");
        log("Setpoint position = " + position);
        log("Brake at end = " + brake);
    }
}