package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Const;

public class DriveCalculator {

    public int gear;
    double prevTimestamp, prevPower;

    public DriveCalculator() {
        reset();
    }

    public double[] calculate(double power, double turn, int gear) {

        // deadband
        power = applyDeadband(power);
        turn = applyDeadband(turn);

        // ramp
        double dt = Timer.getFPGATimestamp() - prevTimestamp;
        prevTimestamp = Timer.getFPGATimestamp();
        power = ramp(power, prevPower, dt);
        prevPower = power;

        // gear
        power = applyPowerGear(power, gear);
        turn = applyTurnGear(turn, gear);

        double right = power + turn;
        double left = power - turn;

        return new double[] { left, right };
    }

    public void reset() {
        prevTimestamp = Timer.getFPGATimestamp();
    }

    private double applyDeadband(double value) {
        if (Math.abs(value) > Const.kDriveJoystickDeadband)
            return value;
        return 0;
    }

    private double ramp(double value, double prevValue, double dt) {
        double dVal = value - prevValue;
        double allowedDVal = dt * Const.kDriveDeltaPercentagePerSec;

        if (dVal > allowedDVal) {
            value = prevValue + allowedDVal;
        } else if (dVal < -allowedDVal) {
            value = prevValue - allowedDVal;
        }
        return value;
    }

    private double applyPowerGear(double speed, int gear) {
        return speed * Const.kDrivePowerGears[gear];
    }

    private double applyTurnGear(double speed, int gear) {
        return speed * Const.kDriveTurnGears[gear - 1];
    }
}