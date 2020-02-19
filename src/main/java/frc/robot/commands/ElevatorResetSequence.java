package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Logger;

public class ElevatorResetSequence extends SequentialCommandGroup {

    public ElevatorResetSequence() {

        addCommands(//
                new ElevatorSetPiston(false), //
                new ElevatorReset(), //
                new ElevatorGoto(0, false), //
                new ElevatorSetPiston(true)//
        );
    }

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("ElevatorResetSequence", msg);
    }

    @Override
    public void initialize() {
        log("Starting");
        super.initialize();
    }

    @Override
    public boolean isFinished() {
        return super.isFinished();
    }
}