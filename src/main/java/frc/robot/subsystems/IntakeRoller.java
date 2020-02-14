package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.ArmFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;
import frc.robot.Util.Debugable;

public class IntakeRoller extends SubsystemBase implements Debugable {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("IntakeRoller", msg);
    }

    private static IntakeRoller instance;

    public static IntakeRoller getInstance() {
        if (instance == null)
            instance = new IntakeRoller();
        return instance;
    }

    public final TalonSRX rollerMotor = new TalonSRX(Const.kTableMotorPort);// TODO: move these to the constructor

    private double rollerState;

    private IntakeRoller() {
        TableCalculator.getInstance();
        rollerState = 0;
        log("Init");
    }

    public void setRoller(double speed) {
        if (speed == rollerState)
            return;
        rollerState = speed;
        rollerMotor.set(ControlMode.PercentOutput, speed);
    }

    public void debug() {
    }
}