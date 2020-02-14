package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Logger;

public class IntakeSetPiston extends CommandBase {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("IntakeSetPiston", msg);
    }

    private final Intake intake = Intake.getInstance();
    private boolean engageIntake;

    public IntakeSetPiston(boolean engageIntake) {
        this.engageIntake = engageIntake;
    }

    @Override
    public void initialize() {
        log("Starting");
        intake.engageIntake(engageIntake);
    }

    @Override
    public boolean isFinished() {
        return intake.getPiston() == engageIntake;
    }

    @Override
    public void end(boolean interrupted) {
        log("Ended with interrupted = [" + interrupted + "]");
    }
}
