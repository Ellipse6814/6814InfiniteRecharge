package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Const;
import frc.robot.subsystems.Table;
import frc.robot.subsystems.TableColorDetector.TableColor;

public class TableRotateToColor extends CommandBase {

    public boolean isFinished;

    private Table tableSpinner = Table.getInstance();

    private int direction;
    private double degToSpinRoller;
    private double prevStableEncoderVal;
    // private TableColorDetector tableColorDetector =
    // TableColorDetector.getInstance();

    public TableRotateToColor() {

    }

    @Override
    public void initialize() {
        System.out.println("Starting CMD: TableRotateToColor");
        isFinished = false;
        prevStableEncoderVal = 0;

        TableColor theirColor = getGameData();
        TableColor wantedColor = getMyColorFromTheirColor(theirColor);

        if (wantedColor == TableColor.Other) {
            isFinished = true;
            System.out.println("TableRotateToColor: ERROR wantedColor = Other");
            return;
        }

        TableColor currentColor = tableSpinner.peekColor();

        if (wantedColor == currentColor) {
            isFinished = true;
            return;
        }

        int displacement = getDisplacementToRotate(currentColor, wantedColor);

        // displacement = 8;
        System.out.println("TableRotateToColor: TheirColor: " + theirColor);
        System.out.println("TableRotateToColor: WantedColor: " + wantedColor);
        System.out.println("TableRotateToColor: CurrentColor: " + currentColor);
        System.out.println("TableRotateToColor: displacement " + displacement + "edges");

        direction = getSign(displacement);
        double degToSpinTable = displacement * 45 + 15 * direction; // spin to middle after arrived at color
        degToSpinRoller = degToSpinTable * Const.kTableRot2RollerRot;

        tableSpinner.initColorSensor(direction);
        tableSpinner.resetEncoder(0);

        System.out.println("TableRotateToColor: rotating " + degToSpinRoller + "deg");

        tableSpinner.stopMotor();
        tableSpinner.setSetpoint(degToSpinRoller);
    }

    @Override
    public void execute() {
        boolean edgeChanged = tableSpinner.getDeltaEdge();
        if (edgeChanged) {
            double deltaDegreesOnRoller = Const.kTableSliceRollerDeg * direction;
            prevStableEncoderVal += deltaDegreesOnRoller;
            System.out.println("wrongEncoderVal: "
                    + tableSpinner.motor.getSelectedSensorPosition() * Const.kTalonRaw2Rot * Const.kRot2Deg);
            System.out.println("prevStableEncoderVal: " + prevStableEncoderVal);
            tableSpinner.resetEncoder(prevStableEncoderVal);
        }

        if (Math.abs(degToSpinRoller - tableSpinner.getEncoderPosition()) < 10
                && Math.abs(tableSpinner.getEncoderVelocity()) < 5) {
            System.out.println("TableRotateToColor: met tolerance "
                    + Math.abs(degToSpinRoller - tableSpinner.getEncoderPosition()) + "deg");
            System.out.println(
                    "TableRotateToColor: met tolerance " + Math.abs(tableSpinner.getEncoderVelocity()) + "deg/s");

            // position error < 90deg
            // velocity < 90deg/s
            // tolerance met -> stop cmd
            isFinished = true;
            return;
        }

        // if (tableSpinner.getEncoderPosition() - prevStableEncoderVal >
        // Const.kTableSliceRollerDeg * 2) {
        // // We should have past two edges by now, but none registered.
        // // Let's restart sequence
        // initialize();
        // return;
        // }
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted)
            System.out.println("TableRotateToColor: WARNING: interrupted");

        System.out.println("TableRotateToColor: INFO: ended");
        tableSpinner.stopMotor();
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
            System.out.println("TableRotateToColor: ERROR game data corrupt:" + gameData);
            return TableColor.Other;
        } else {
            System.out.println("TableRotateToColor: ERROR game data not received");
            return TableColor.Other;
        }
    }

    private int getSign(int a) {
        if (a > 0)
            return 1;
        if (a < 0)
            return -1;
        return 0;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}