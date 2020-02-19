package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Logger;

public class Seq extends SequentialCommandGroup {

    public Seq(Command[] commands) {
        for (int i = 0; i < commands.length; i++) {
            addCommands(commands[i]);
        }
    }

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("Seq", msg);
    }

    @Override
    public void initialize() {
        log("Starting");
        super.initialize();
    }
}