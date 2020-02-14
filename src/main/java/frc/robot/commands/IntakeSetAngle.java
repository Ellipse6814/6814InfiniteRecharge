package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeAngleMotor;
import frc.robot.subsystems.Logger;

public class IntakeSetAngle extends PartialFinishableCommand {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("IntakeSetAngle", msg);
    }

    private final IntakeAngleMotor intakeAngleMotor = IntakeAngleMotor.getInstance();
    private double angle;

    public IntakeSetAngle(double angle) {
        this.angle = angle;
        addRequirements(intakeAngleMotor);
    }

    @Override
    public void initialize() {
        log("Starting");
        intakeAngleMotor.setAngle(angle);
    }

    @Override
    public void execute() {
        intakeAngleMotor.setAngle(angle);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isPartialFinished() {
        return intakeAngleMotor.onTarget();
    }

    @Override
    public void end(boolean interrupted) {
        log("Ended with interrupted = [" + interrupted + "]");
    }

}
