package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;
import frc.robot.Util.Debugable;
import frc.robot.subsystems.TableColorDetector.TableColor;

public class TablePiston extends SubsystemBase implements Debugable{

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("TablePiston", msg);
    }

    private static TablePiston instance;

    public static TablePiston getInstance() {
        if (instance == null)
            instance = new TablePiston();
        return instance;
    }

    private final DoubleSolenoid piston;

    private boolean pistonState;
    private double pistonStateValidAfter;

    private TablePiston() {
        // TODO: put consts in @Link{Const.java}
        piston = new DoubleSolenoid(Const.kTableSolonoidPort1, Const.kTableSolonoidPort2);

        pistonState = piston.get().equals(Const.kIntakeEngagePistonPos);
        pistonStateValidAfter = Timer.getFPGATimestamp();
        log("Init");
    }

    public boolean getPiston() {
        return Timer.getFPGATimestamp() >= pistonStateValidAfter ? pistonState : !pistonState;
    }

    public void engageTable(boolean engage) {
        if (engage == pistonState)// not getPiston() which is the current state
            return; // pistonState is the target state, which is what we want

        if (engage)
            piston.set(Const.kTableEngagePistonPos);
        else
            piston.set(Const.kTableNOTEngagePistonPos);

        pistonState = engage;
        pistonStateValidAfter = Timer.getFPGATimestamp() + Const.kTablePistonMoveDelay;
    }

    public void debug() {
    }

}