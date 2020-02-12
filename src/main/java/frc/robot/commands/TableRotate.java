package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Logger;
import frc.robot.subsystems.Table;
import frc.robot.subsystems.TableCalculator;

public class TableRotate extends CommandBase {
    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("TableRotate", msg);
    }

    private Table table = Table.getInstance();
    private TableCalculator tableCalculator = TableCalculator.getInstance();

    private boolean doColor;

    public TableRotate(boolean doColor) {
        this.doColor = doColor;
    }

    @Override
    public void initialize() {
        log("Starting");
        table.stableSpinDegs(doColor ? tableCalculator.calculateColor() : tableCalculator.calculateRotations());
    }

    @Override
    public void execute() {
        table.updateColorSensorComplementaryFilter();
    }

    @Override
    public void end(boolean interrupted) {
        log("Ended with interrupted = [" + interrupted + "]");
    }

    @Override
    public boolean isFinished() {
        return table.onTarget();
    }
}