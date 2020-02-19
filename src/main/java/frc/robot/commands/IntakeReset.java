package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Const;
import frc.robot.subsystems.IntakeAngleMotor;
import frc.robot.subsystems.Logger;

public class IntakeReset extends CommandBase {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("IntakeReset", msg);
    }

    private final IntakeAngleMotor intakeMotor = IntakeAngleMotor.getInstance();
    private boolean isFinished;

    public IntakeReset() {
        addRequirements(intakeMotor);
    }

    @Override
    public void initialize() {
        log("Starting");
        isFinished = false;
        intakeMotor.setSpeed(Const.kIntakeResetSpeed);
    }

    @Override
    public void execute() {
        if (!intakeMotor.getResetLimitSwitch()) {
            intakeMotor.setSpeed(Const.kIntakeResetSpeed);
        } else {
            intakeMotor.setSpeed(0);
            intakeMotor.resetEncoder(Const.kIntakeLimitSwitchEncoderPosition);
            isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void end(boolean interrupted) {
        log("Ended with interrupted = [" + interrupted + "]");
    }
}
