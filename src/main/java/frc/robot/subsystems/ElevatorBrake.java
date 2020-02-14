package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.ElevatorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;
import frc.robot.Util.Debugable;

public class ElevatorBrake extends SubsystemBase implements Debugable {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("ElevatorBrake", msg);
    }

    private static ElevatorBrake instance;

    public static ElevatorBrake getInstance() {
        if (instance == null)
            instance = new ElevatorBrake();
        return instance;
    }

    private final DoubleSolenoid piston;

    private boolean pistonState;
    private double pistonStateValidAfter;

    private ElevatorBrake() {
        piston = new DoubleSolenoid(Const.kElevatorSolonoidPort1, Const.kElevatorSolonoidPort2);

        log("Init");
    }

    public boolean getPiston() {
        return Timer.getFPGATimestamp() >= pistonStateValidAfter ? pistonState : !pistonState;
    }

    public void engageBrake(boolean engage) {
        if (engage == pistonState)// not getPiston() which is the current state
            return; // pistonState is the target state, which is what we want

        if (engage)
            piston.set(Const.kElevatorEngagePistonPos);
        else
            piston.set(Const.kElevatorNOTEngagePistonPos);

        pistonState = engage;
        pistonStateValidAfter = Timer.getFPGATimestamp() + Const.kElevatorPistonMoveDelay;
    }

    public void debug() {
    }
}