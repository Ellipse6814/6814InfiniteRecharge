package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Logger;

public class TableDoTable extends SequentialCommandGroup {
    public TableDoTable(boolean colorTask) {
        addCommands(//
                new TableEngage(true), //
                colorTask ? new TableRotateToColor() : new TableRotateToRotations(), //
                new TableEngage(false)//
        );
    }

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("TableDoTable", msg);
    }

    @Override
    public void initialize() {
        log("Starting");
    }
}