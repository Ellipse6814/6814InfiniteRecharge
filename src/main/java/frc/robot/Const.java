package frc.robot;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.util.Color;

public class Const {

    // units
    public static final double kINF = 1e8;

    // // rotations
    public static final double kTalonRaw2Rot = 1.0 / 4096;
    public static final double kRot2TalonRaw = 4096.0;
    public static final double kSec2Talon100Ms = 1.0 / 10;
    public static final double kTalon100Ms2sec = 10.0;
    public static final double kDeg2Rot = 1.0 / 360;
    public static final double kRot2Deg = 360.0;
    public static final double kDeg2Rad = 2.0 * Math.PI / 360;
    public static final double kRad2Deg = 1 / kDeg2Rad;

    // // lengths
    public static final double kIn2M = 0.0254;
    public static final double kM2In = 1 / 0.0254;

    // // time
    public static final double kMin2Sec = 60.0;

    // _robot
    public static final int kCANTimeout = 20;// ms
    public static final int kMagEncoderCountsPerRotation = 4096;
    public static final double kMotorDeadband = 0.05;

    // _drive
    public static final int kDriveLeftMotorPort = -1;
    public static final int kDriveLeftMotorPort1 = -2;
    public static final int kDriveLeftMotorPort2 = -3;
    public static final int kDriveRightMotorPort = -4;
    public static final int kDriveRightMotorPort1 = -5;
    public static final int kDriveRightMotorPort2 = -6;

    public static final double kDriveGearRatio = 1;
    public static final double kDriveWheelDiameter = 6 * kIn2M;

    public static final boolean kLeftDriveInverted = false;
    public static final boolean kRightDriveInverted = true;
    public static final boolean kLeftDriveSensorInverted = false;// TODO:
    public static final boolean kRightDriveSensorInverted = true;

    public static final double[] kDrivePowerGears = { 0.3333, 0.6666, 1 };
    public static final double[] kDriveTurnGears = { 0.25, 0.25, 0.25 };

    public static final double kDriveDeltaPercentagePerSec = 4; // 1 == 100%

    // _table
    public static final int kTableSolonoidPort1 = -1;
    public static final int kTableSolonoidPort2 = -1;
    public static final int kTableMotorPort = -1;
    public static final double kTableGearRatio = 1;

    public static final Value kTableEngagePistonPos = Value.kForward; // TODO:
    public static final Value kTableNOTEngagePistonPos = Value.kReverse;

    public static final double kTablePistonMoveDelay = 1;

    public static final Color kBlue = ColorMatch.makeColor(0.143, 0.427, 0.429); // Color(0.12, 0.42, 0.44);
    public static final Color kGreen = ColorMatch.makeColor(0.197, 0.561, 0.240); // Color(0.18, 0.59, 0.23);
    public static final Color kRed = ColorMatch.makeColor(0.561, 0.232, 0.114); // Color(0.5, 0.34, 0.15);
    public static final Color kYellow = ColorMatch.makeColor(0.361, 0.524, 0.113); // Color(0.34, 0.55, 0.11);

    public static final double kEffectiveTableDiameter = 29;
    public static final double kTableRollerDiameter = 3;
    public static final double kTableRot2RollerRot = (Math.PI * kEffectiveTableDiameter)
            / (Math.PI * kTableRollerDiameter);
    public static final double kRollerRot2TableRot = 1 / kTableRot2RollerRot;

    public static final double kTableSliceDeg = 45.0;
    public static final double kTableSliceRollerDeg = kTableSliceDeg * kTableRot2RollerRot;
    public static final double kTableSliceOverSpinPercentage = 0.5;
    public static final int kTableRotationSlices = 10;
    public static final double kDeltaDegreesOnRoller = Const.kTableSliceRollerDeg;

    public static final double kTableMaxVel = 0;
    public static final double kTableMaxAcc = 0;
    public static final double kTablekP = 0;
    public static final double kTablekI = 0;
    public static final double kTablekD = 0;
    public static final double kTablekS = 0;
    public static final double kTablekV = 0;
    public static final double kTablekA = 0;

    // _elevator
    public static final int kElevatorSolonoidPort1 = -2;
    public static final int kElevatorSolonoidPort2 = -3;
    public static final int kElevatorMotorPort = -2;
    public static final int kElevatorMotorSlavePort1 = -2;
    public static final int kElevatorMotorSlavePort2 = -2;
    public static final int kElevatorLimitSwitchPort = -1;
    public static final int kElevatorReleaseButtonPort = -1;

    public static final double kElevatorGearRatio = 1;

    public static final Value kElevatorEngagePistonPos = Value.kForward;// TODO:
    public static final Value kElevatorNOTEngagePistonPos = Value.kReverse;

    public static final boolean kElevatorInverted = false;// TODO:
    public static final boolean kElevatorSensorInverted = false;// TODO:
    public static final int kElevatorCurrentLimit = 40;

    public static final double kElevatorResetSpeed = -0.1;
    public static final double kElevatorLimitSwitchEncoderPosition = 0;

    public static final double kElevatorPistonMoveDelay = 1;

    public static final double kElevatorPositionTolerance = 1;
    public static final double kElevatorVelocityTolerance = 1;

    public static final double kElevatorBottomPos = 0; // TODO:
    public static final double kElevatorTopPos = 1;

    public static final double kElevatorMaxVel = 0;
    public static final double kElevatorMaxAcc = 0;
    public static final double kElevatorkP = 0;
    public static final double kElevatorkI = 0;
    public static final double kElevatorkD = 0;
    public static final double kElevatorkS = 0;
    public static final double kElevatorkV = 0;
    public static final double kElevatorkA = 0;
    public static final double kElevatorkG = 0;

    // _intake
    public static final int kIntakeRollerMotorPort = -1;
    public static final int kIntakeAngleMotorPort = -1;
    public static final int kIntakeLimitSwitchPort = -1;

    public static final boolean kIntakeAngleMotorInverted = false;
    public static final boolean kIntakeAngleMotorSensorInverted = false;
    public static final int kIntakeCurrentLimit = 40;

    public static final double kIntakeGearRatio = 1;

    public static final Value kIntakeEngagePistonPos = Value.kForward;
    public static final Value kIntakeNOTEngagePistonPos = Value.kReverse;
    public static final double kIntakePistonMoveDelay = 1;

    public static final double kIntakeResetSpeed = -0.1;
    public static final double kIntakeLimitSwitchEncoderPosition = 0;

    public static final double kIntakePositionTolerance = 1;
    public static final double kIntakeVelocityTolerance = 1;

    public static final double kIntakeGroundAngle = 1;
    public static final double kIntakeHideAngle = 0;
    public static final double kIntakeShootAngle = 0;

    public static final double kIntakeHoldSpd = 0.2;
    public static final double kIntakeInSpd = -1;
    public static final double kIntakeOutSpd = 1;

    public static final double kIntakeMaxVel = 0;
    public static final double kIntakeMaxAcc = 0;
    public static final double kIntakekP = 0;
    public static final double kIntakekI = 0;
    public static final double kIntakekD = 0;
    public static final double kIntakekS = 0;
    public static final double kIntakekV = 0;
    public static final double kIntakekA = 0;
    public static final double kIntakekCos = 0;

    // public static final double

    // _LED
    public static final int kLEDPort = -1;

}