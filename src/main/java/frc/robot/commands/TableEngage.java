package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Logger;
import frc.robot.subsystems.TablePiston;

public class TableEngage extends CommandBase {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("TableEngage", msg);
    }

    private final TablePiston tablePiston = TablePiston.getInstance();
    private boolean engageTable;

    public TableEngage(boolean engageTable) {
        this.engageTable = engageTable;
        addRequirements(tablePiston);
    }

    @Override
    public void initialize() {
        log("Starting");
        tablePiston.engageTable(engageTable);
    }

    @Override
    public boolean isFinished() {
        return tablePiston.getPiston() == engageTable;
    }

    @Override
    public void end(boolean interrupted) {
        log("Ended with interrupted = [" + interrupted + "]");
    }
}
