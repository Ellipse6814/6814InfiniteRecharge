package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TableDoTable extends SequentialCommandGroup {
    public TableDoTable(boolean colorTask) {
        addCommands(//
                new TableEngage(true), //
                colorTask ? new TableRotateToColor() : new TableRotateToRotations(), //
                new TableEngage(false)//
        );
    }

    @Override
    public void initialize() {
        System.out.println("Starting CMD: TableDoTable");
    }
}