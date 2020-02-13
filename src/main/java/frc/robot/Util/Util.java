package frc.robot.Util;

import frc.robot.Const;

public class Util {

    public static boolean equal(double a, double b) {
        return Math.abs(a - b) < (1 / Const.kINF);
    }
}