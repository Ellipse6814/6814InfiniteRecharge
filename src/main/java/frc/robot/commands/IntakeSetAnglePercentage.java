package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeAngleMotor;
import frc.robot.subsystems.Logger;

public class IntakeSetAnglePercentage extends CommandBase {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("IntakeSetAnglePercentage", msg);
    }

    private final IntakeAngleMotor intakeAngleMotor = IntakeAngleMotor.getInstance();
    private double speed;

    public IntakeSetAnglePercentage(double speed) {
        this.speed = speed;
        addRequirements(intakeAngleMotor);
    }

    @Override
    public void initialize() {
        log("Starting");
        intakeAngleMotor.setAngleMotorPercentage(speed);
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
