package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Logger;

public class IntakeSetPercentage extends CommandBase {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("IntakeSetPercentage", msg);
    }

    private final Intake intake = Intake.getInstance();
    private double speed;

    public IntakeSetPercentage(double speed) {
        this.speed = speed;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        log("Starting");
        intake.setAngleMotorPercentage(speed);
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
