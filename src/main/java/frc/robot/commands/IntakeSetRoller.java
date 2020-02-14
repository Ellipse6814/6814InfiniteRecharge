package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Logger;

public class IntakeSetRoller extends CommandBase {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("IntakeSetRoller", msg);
    }

    private final Intake intake = Intake.getInstance();
    private double speed;

    public IntakeSetRoller(double speed) {
        this.speed = speed;
    }

    @Override
    public void initialize() {
        log("Starting");
        intake.setRoller(speed);
    }

    @Override
    public void execute() {
        intake.setRoller(speed);
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
