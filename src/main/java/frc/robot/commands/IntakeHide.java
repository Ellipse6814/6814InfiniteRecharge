package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Const;
import frc.robot.subsystems.Logger;

public class IntakeHide extends SequentialCommandGroup {
    // public IntakeHide() {
    // addCommands(//
    // new IntakeSetAngle(Const.kIntakeHideAngle), //
    // new IntakeSetPiston(false) //
    // );
    // }
    public IntakeHide() {
        addCommands(//
                new PartialFinish(new IntakeSetAngle(Const.kIntakeHideAngle)), //
                new IntakeSetPiston(false) //
        );
    }

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("IntakeHide", msg);
    }

    @Override
    public void initialize() {
        log("Starting");
    }
}