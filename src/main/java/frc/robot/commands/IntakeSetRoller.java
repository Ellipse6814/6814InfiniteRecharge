package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeRoller;
import frc.robot.subsystems.Logger;

public class IntakeSetRoller extends CommandBase {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("IntakeSetRoller", msg);
    }

    private final IntakeRoller intakeRoller = IntakeRoller.getInstance();
    private double speed;

    public IntakeSetRoller(double speed) {
        this.speed = speed;
        addRequirements(intakeRoller);
    }

    @Override
    public void initialize() {
        log("Starting");
        intakeRoller.setRoller(speed);
    }

    @Override
    public void execute() {
        intakeRoller.setRoller(speed);
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
