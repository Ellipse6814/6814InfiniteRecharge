package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ElevatorMotor;
import frc.robot.subsystems.Logger;

public class ElevatorResetSequence extends SequentialCommandGroup {

    public ElevatorResetSequence() {

        addCommands(//
                new ElevatorSetPiston(false), //
                new ElevatorReset(), //
                new ElevatorSetPiston(true)//
        );
    }

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("ElevatorResetSequence", msg);
    }

    private ElevatorMotor elevatorMotor = ElevatorMotor.getInstance();
    private boolean skipCommand;

    @Override
    public void initialize() {
        log("Starting");

        if (elevatorMotor.getSafe()) {
            skipCommand = true;
            log("Already safe and reset, exiting ElevatorResetSequence");
            return;
        }

        skipCommand = false;
        super.initialize();
    }

    @Override
    public boolean isFinished() {
        return skipCommand || super.isFinished();
    }
}