package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakePiston;
import frc.robot.subsystems.Logger;

public class IntakeSetPiston extends CommandBase {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("IntakeSetPiston", msg);
    }

    private final IntakePiston intakePiston = IntakePiston.getInstance();
    private boolean engageIntake;

    public IntakeSetPiston(boolean engageIntake) {
        this.engageIntake = engageIntake;
        addRequirements(intakePiston);
    }

    @Override
    public void initialize() {
        log("Starting");
        intakePiston.engageIntake(engageIntake);
    }

    @Override
    public boolean isFinished() {
        return intakePiston.getPiston() == engageIntake;
    }

    @Override
    public void end(boolean interrupted) {
        log("Ended with interrupted = [" + interrupted + "]");
    }
}
