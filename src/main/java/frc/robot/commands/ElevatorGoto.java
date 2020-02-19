package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ElevatorMotor;
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

    private ElevatorMotor elevatorMotor = ElevatorMotor.getInstance();
    private boolean skipCommand;

    @Override
    public void initialize() {
        log("Starting");
        log("Setpoint position = " + position);
        log("Brake at end = " + brake);

        if (elevatorMotor.onTarget()) {
            skipCommand = true;
            log("already on target, not even going to move the motors nor release the brakes");
            return;
        }

        skipCommand = false;
        super.initialize();
    }

    @Override
    public boolean isFinished() {
        return skipCommand || super.isFinished();
    }
}