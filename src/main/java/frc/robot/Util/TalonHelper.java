package frc.robot.Util;

import java.util.List;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Const;
import frc.robot.subsystems.Logger;

public class TalonHelper {
    private static Logger logger = Logger.getInstance();

    private static void log(Object msg) {
        logger.log("TalonHelper", msg);
    }

    public static final int kCommTimeout = Const.kCANTimeout; // ms

    public static TalonSRX createTalon(int port, boolean inverted) {
        TalonSRX talon = new TalonSRX(port);
        talon.configFactoryDefault(kCommTimeout);
        talon.setInverted(inverted);
        talon.enableVoltageCompensation(true);
        talon.configForwardSoftLimitEnable(false, 10);
        talon.configReverseSoftLimitEnable(false, 10);
        log("A TalonSRX is created on port [" + port + "], with inverted [" + inverted + "]");
        return talon;
    }

    public static TalonSRX createSlaveTalon(int port, TalonSRX master) {
        TalonSRX talon = new TalonSRX(port);
        talon.configFactoryDefault(kCommTimeout);
        talon.follow(master);
        talon.setInverted(InvertType.FollowMaster);
        log("A SLAVE TalonSRX is created on port [" + port + "], with inverted [" + master.getInverted() + "]");
        return talon;
    }

    public static VictorSPX createSlaveVictor(int port, TalonSRX master) {
        VictorSPX victor = new VictorSPX(port);
        victor.configFactoryDefault(kCommTimeout);
        victor.follow(master);
        victor.setInverted(InvertType.FollowMaster);
        log("A SLAVE VictorSRX is created on port [" + port + "], with inverted [" + master.getInverted() + "]");
        return victor;
    }

    public static void configMagEncoder(TalonSRX talon, boolean phase) {
        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kCommTimeout);
        talon.setSensorPhase(phase);
    }

    public static void configQuadEncoder(TalonSRX talon, boolean phase) {
        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kCommTimeout);
        talon.setSensorPhase(phase);
    }

    public static void configCurrentLimit(TalonSRX talon, int amps) {
        talon.configContinuousCurrentLimit(amps, kCommTimeout);
        talon.configPeakCurrentLimit(0);
    }

    public static void configNeutralMode(TalonSRX talon, NeutralMode mode) {
        talon.setNeutralMode(mode);
    }

    public static void configNeutralMode(VictorSPX victor, NeutralMode mode) {
        victor.setNeutralMode(mode);
    }

    public static void configNeutralMode(List<Object> motors, NeutralMode mode) {
        motors.forEach((motor) -> {
            if (motor instanceof TalonSRX) {
                configNeutralMode((TalonSRX) motor, mode);
            } else if (motor instanceof VictorSPX) {
                configNeutralMode((VictorSPX) motor, mode);
            } else {
                log("ERROR: config neutral mode received non-motor objects");
            }
        });
    }

    public static void configDeadband(TalonSRX talon, double percent) {
        talon.configNeutralDeadband(percent);
    }

    public static void configDeadband(VictorSPX victor, double percent) {
        victor.configNeutralDeadband(percent);
    }

    public static void configUpdateRate(TalonSRX talon, StatusFrame type, int RateMS) {
        talon.setStatusFramePeriod(type, RateMS, 10);
    }

    public static void configUpdateRate(VictorSPX victor, StatusFrame type, int RateMS) {
        victor.setStatusFramePeriod(type, RateMS, 10);
    }

    public static void configDeadband(List<Object> motors, double percent) {
        motors.forEach((motor) -> {
            if (motor instanceof TalonSRX) {
                configDeadband((TalonSRX) motor, percent);
            } else if (motor instanceof VictorSPX) {
                configDeadband((VictorSPX) motor, percent);
            } else {
                log("ERROR: config deadband received non-motor objects");
            }
        });
    }

    public static void configPID(TalonSRX talon, int slot, double p, double i, double d, double f, double iZoneRot) {
        talon.config_kP(slot, p, kCommTimeout);
        talon.config_kI(slot, i, kCommTimeout);
        talon.config_kD(slot, d, kCommTimeout);
        talon.config_kF(slot, f, kCommTimeout);
        talon.config_IntegralZone(slot, (int) (iZoneRot * Const.kRot2TalonRaw), kCommTimeout);
    }

    public static void configUpperSoftLimit(TalonSRX talon, int limit) {
        talon.configForwardSoftLimitThreshold(limit, kCommTimeout);
        talon.configForwardSoftLimitEnable(true, kCommTimeout);
    }

    public static void configLowerSoftLimit(TalonSRX talon, int limit) {
        talon.configReverseSoftLimitThreshold(limit, kCommTimeout);
        talon.configReverseSoftLimitEnable(true, kCommTimeout);
    }

    public static void configMotionMagic(TalonSRX talon, double maxVelRotPerSec, double maxAccRotPerSec2) {
        talon.configMotionCruiseVelocity((int) (maxVelRotPerSec * Const.kRot2TalonRaw * Const.kSec2Talon100Ms),
                kCommTimeout);
        talon.configMotionAcceleration((int) (maxAccRotPerSec2 * Const.kRot2TalonRaw * Const.kSec2Talon100Ms),
                kCommTimeout);
    }
}