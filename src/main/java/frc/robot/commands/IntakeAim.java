package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Const;
import frc.robot.subsystems.Logger;

public class IntakeAim extends SequentialCommandGroup {
    public IntakeAim() {
        addCommands(//
                new IntakeSetPiston(true), //
                new IntakeSetAngle(Const.kIntakeShootAngle)//
        );
    }

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("IntakeGround", msg);
    }

    @Override
    public void initialize() {
        log("Starting");
    }
}