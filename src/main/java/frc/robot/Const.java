package frc.robot;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.util.Color;

public class Const {

    // units
    public static final double kTalonRaw2Rot = 1.0 / 4096;
    public static final double kRot2TalonRaw = 4096.0;
    public static final double kSec2Talon100Ms = 1.0 / 10;
    public static final double kTalon100Ms2sec = 10.0;
    public static final double kDeg2Rot = 1.0 / 360;
    public static final double kRot2Deg = 360.0;

    public static final double kIn2M = 0.0254;
    public static final double kM2In = 1 / 0.0254;

    // _robot
    public static final Color kBlue = ColorMatch.makeColor(0.143, 0.427, 0.429); // Color(0.12, 0.42, 0.44);
    public static final Color kGreen = ColorMatch.makeColor(0.197, 0.561, 0.240); // Color(0.18, 0.59, 0.23);
    public static final Color kRed = ColorMatch.makeColor(0.561, 0.232, 0.114); // Color(0.5, 0.34, 0.15);
    public static final Color kYellow = ColorMatch.makeColor(0.361, 0.524, 0.113); // Color(0.34, 0.55, 0.11);

    public static final double kEffectiveTableDiameter = 29;
    public static final double kTableRollerDiameter = 3;
    public static final double kTableRot2RollerRot = (Math.PI * kEffectiveTableDiameter)
            / (Math.PI * kTableRollerDiameter);
    public static final double kRollerRot2TableRot = 1 / kTableRot2RollerRot;

    public static final int kCANTimeout = 30;

    public static final double kTableSliceDeg = 45.0;
    public static final double kTableSliceRollerDeg = kTableSliceDeg * kTableRot2RollerRot;

    // _drive
    public static final int kDriveLeftMotorPort = 1;
    public static final int kDriveLeftMotorPort1 = 2;
    public static final int kDriveLeftMotorPort2 = 3;
    public static final int kDriveRightMotorPort = 4;
    public static final int kDriveRightMotorPort1 = 5;
    public static final int kDriveRightMotorPort2 = 6;

    public static final boolean kLeftDriveInverted = false;
    public static final boolean kRightDriveInverted = true;

    public static final double kDriveJoystickDeadband = 0.05;

    public static final double[] kDrivePowerGears = { 0.3333, 0.6666, 1 };
    public static final double[] kDriveTurnGears = { 0.25, 0.25, 0.25 };

    public static final double kDriveDeltaPercentagePerSec = 4; // 1 == 100%

    // _table
    public static final int kTableSolonoidPort1 = -0;
    public static final int kTableSolonoidPort2 = -1;
    public static final int kTableMotorPort = -1;

    // _elevator
    public static final int kElevatorSolonoidPort1 = -2;
    public static final int kElevatorSolonoidPort2 = -3;
    public static final int kElevatorMotorPort = -2;

    // _intake
    public static final int kIntakeRollerMotorPort = -1;
    public static final int kIntakeAngleMotorPort = -1;

    // _LED
    public static final int kLEDPort = -1;

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        // System.out.println(kRollerRot2TableRot);
    }
}