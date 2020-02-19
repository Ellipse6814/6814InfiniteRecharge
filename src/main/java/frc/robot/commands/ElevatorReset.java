package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Const;
import frc.robot.subsystems.ElevatorMotor;
import frc.robot.subsystems.Logger;

public class ElevatorReset extends CommandBase {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("ElevatorReset", msg);
    }

    private final ElevatorMotor elevatorMotor = ElevatorMotor.getInstance();
    private boolean isFinished;

    public ElevatorReset() {
        addRequirements(elevatorMotor);
    }

    @Override
    public void initialize() {
        log("Starting");
        isFinished = false;
        elevatorMotor.setSpeed(Const.kElevatorResetSpeed);

        if (elevatorMotor.getSafe()) {
            isFinished = true;
            log("Already safe and reset, finishing ElevatorReset");
        }
    }

    @Override
    public void execute() {
        if (!elevatorMotor.getResetLimitSwitch()) {
            elevatorMotor.setSpeed(Const.kElevatorResetSpeed);
        } else {
            elevatorMotor.setSpeed(0);
            elevatorMotor.resetEncoder(Const.kElevatorLimitSwitchEncoderPosition);
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
