package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Const;
import frc.robot.subsystems.TableColorDetector.TableColor;

public class TableCalculator {

    private static TableCalculator instance;

    public static TableCalculator getInstance() {
        if (instance == null) {
            instance = new TableCalculator();
        }
        return instance;
    }

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("TableCalculator", msg);
    }

    private TableMotor table = TableMotor.getInstance();

    public double calculateColor() {
        TableColor theirColor = getGameData();
        TableColor wantedColor = getMyColorFromTheirColor(theirColor);

        if (wantedColor == TableColor.Other) {
            log("ERROR wantedColor = Other");
            return 0;
        }

        TableColor currentColor = table.peekColor();

        if (wantedColor == currentColor) {
            return 0;
        }

        int slices = getDisplacementToRotate(currentColor, wantedColor);
        double degs = edges2Degs(slices + Const.kTableSliceOverSpinPercentage);// spin to middle after arrived at color

        log("TheirColor: " + theirColor);
        log("WantedColor: " + wantedColor);
        log("CurrentColor: " + currentColor);
        log("displacement " + slices + "edges");

        return degs;
    }

    public double calculateRotations() {
        int slices = Const.kTableRotationSlices;

        double degs = edges2Degs(slices + Const.kTableSliceOverSpinPercentage);// spin to middle after arrived at color

        log("displacement " + slices + "edges");

        return degs;
    }

    private double edges2Degs(double slices) {
        double degToSpinTable = slices * Const.kTableSliceDeg;
        double degToSpinRoller = degToSpinTable * Const.kTableRot2RollerRot;
        return degToSpinRoller;
    }

    private int getDisplacementToRotate(TableColor currentColor, TableColor wantedColor) {
        if (currentColor == TableColor.Green) {
            if (wantedColor == TableColor.Red)
                return -1;
            else if (wantedColor == TableColor.Blue)
                return 1;
            else
                return 2;
        } else if (currentColor == TableColor.Red) {
            if (wantedColor == TableColor.Yellow)
                return -1;
            else if (wantedColor == TableColor.Green)
                return 1;
            else
                return 2;
        } else if (currentColor == TableColor.Yellow) {
            if (wantedColor == TableColor.Blue)
                return -1;
            else if (wantedColor == TableColor.Red)
                return 1;
            else
                return 2;
        } else if (currentColor == TableColor.Blue) {
            if (wantedColor == TableColor.Green)
                return -1;
            else if (wantedColor == TableColor.Yellow)
                return 1;
            else
                return 2;
        }
        return 0;
    }

    private TableColor getMyColorFromTheirColor(TableColor theirColor) {
        if (theirColor == TableColor.Green)
            return TableColor.Blue;
        else if (theirColor == TableColor.Blue)
            return TableColor.Yellow;
        else if (theirColor == TableColor.Yellow)
            return TableColor.Red;
        else if (theirColor == TableColor.Red)
            return TableColor.Green;
        else { // theirColor== Other ==null
            return TableColor.Other;
        }
    }

    private TableColor getGameData() {
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.length() > 0) {
            if (gameData.charAt(0) == 'B')
                return TableColor.Blue;
            else if (gameData.charAt(0) == 'Y')
                return TableColor.Yellow;
            else if (gameData.charAt(0) == 'G')
                return TableColor.Green;
            else if (gameData.charAt(0) == 'R')
                return TableColor.Red;
            log("ERROR game data corrupt: " + gameData);
            return TableColor.Other;
        } else {
            log("ERROR game data not received");
            return TableColor.Other;
        }
    }

}