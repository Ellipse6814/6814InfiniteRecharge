package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Logger;

public class TableDoTable extends SequentialCommandGroup {

    private boolean doColor;

    public TableDoTable(boolean doColor) {
        this.doColor = doColor;
        addCommands(//
                new TableEngage(true), //
                new TableRotate(doColor), //
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
        log("doColor = " + doColor);
        super.initialize();
    }
}