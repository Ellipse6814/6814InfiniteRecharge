package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public abstract class PartialFinishableCommand extends CommandBase {
    public abstract boolean isPartialFinished();
}