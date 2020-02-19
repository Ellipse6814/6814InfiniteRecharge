package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Logger;

public class TableHide extends ParallelCommandGroup {

    public TableHide() {
        addCommands(//
                new TableEngage(false), //
                new TableSetMotor(0)//
        );
    }

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("TableHide", msg);
    }

    @Override
    public void initialize() {
        log("Starting");
        super.initialize();
    }
}