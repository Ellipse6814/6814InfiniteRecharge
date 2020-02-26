package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;
import frc.robot.Util.Debugable;

public class IntakePiston extends SubsystemBase implements Debugable {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("IntakePiston", msg);
    }

    private static IntakePiston instance;

    public static IntakePiston getInstance() {
        if (instance == null)
            instance = new IntakePiston();
        return instance;
    }

    private final DoubleSolenoid piston;

    private boolean pistonState;
    private double pistonStateValidAfter;

    private IntakePiston() {
        TableCalculator.getInstance();
        piston = new DoubleSolenoid(Const.kIntakeSolonoidPort1, Const.kIntakeSolonoidPort2);

        pistonState = piston.get().equals(Const.kIntakeEngagePistonPos);
        pistonStateValidAfter = Timer.getFPGATimestamp();

        log("Init");
    }

    public boolean getPiston() {
        return Timer.getFPGATimestamp() >= pistonStateValidAfter ? pistonState : !pistonState;
    }

    public void engageIntake(boolean engage) {
        if (engage == pistonState)// not getPiston() which is the current state
            return; // pistonState is the target state, which is what we want

        if (engage)
            piston.set(Const.kIntakeEngagePistonPos);
        else
            piston.set(Const.kIntakeNOTEngagePistonPos);

        pistonState = engage;
        pistonStateValidAfter = Timer.getFPGATimestamp() + Const.kIntakePistonMoveDelay;
    }

    public void debug() {
        SmartDashboard.putBoolean("IntakePiston:getPiston()", getPiston());
        SmartDashboard.putBoolean("IntakePiston:pistonState", pistonState);
        SmartDashboard.putNumber("IntakePiston:pistonStateValidAfter", pistonStateValidAfter);
    }
}