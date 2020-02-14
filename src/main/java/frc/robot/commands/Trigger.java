package frc.robot.commands;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Trigger {
    public CommandBase[] commands;

    public Trigger(CommandBase command) {
        commands = new CommandBase[] { command };
    }

    public Trigger(CommandBase[] commands) {
        this.commands = commands;
    }

    public void trigger() {
        for (int i = 0; i < commands.length; i++) {
            commands[i].schedule();
        }
    }
}