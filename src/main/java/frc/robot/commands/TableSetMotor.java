package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Logger;
import frc.robot.subsystems.TableMotor;

public class TableSetMotor extends CommandBase {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("TableSetMotor", msg);
    }

    private final TableMotor tableMotor = TableMotor.getInstance();
    private double speed;

    public TableSetMotor(double speed) {
        this.speed = speed;
        addRequirements(tableMotor);
    }

    @Override
    public void initialize() {
        log("Starting");
        tableMotor.setSpeed(speed);
    }

    @Override
    public void execute() {
        tableMotor.setSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        log("Ended with interrupted = [" + interrupted + "]");
    }
}
