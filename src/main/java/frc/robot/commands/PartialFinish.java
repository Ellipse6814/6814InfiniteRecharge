package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class PartialFinish extends CommandBase {
    private PartialFinishableCommand command;

    public PartialFinish(PartialFinishableCommand command) {
        this.command = command;
    }

    @Override
    public void initialize() {
        command.schedule();
    }

    @Override
    public boolean isFinished() {
        return command.isPartialFinished();
    }
}