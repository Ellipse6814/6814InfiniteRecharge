package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Const;
import frc.robot.subsystems.Table;
import frc.robot.subsystems.TableColorDetector.TableColor;

public class TableRotateToRotations extends CommandBase {

    public boolean isFinished;

    private Table tableSpinner = Table.getInstance();

    private int direction;
    private double degToSpinRoller;
    private double degToSpinTable;
    private double prevStableEncoderVal;

    public TableRotateToRotations() {

    }

    @Override
    public void initialize() {
        System.out.println("Starting CMD: TableRotateToRotations");

        isFinished = false;
        prevStableEncoderVal = 0;
        degToSpinTable = 0;

        int displacement = 20;

        System.out.println("TableRotateToRotations: displacement " + displacement + " edges");

        direction = getSign(displacement);
        degToSpinTable = displacement * 45 + 15 * direction; // spin to middle after arrived at color
        degToSpinRoller = degToSpinTable * Const.kTableRot2RollerRot;

        tableSpinner.resetInitColorSensor(direction);
        tableSpinner.resetEncoder(0);

        System.out.println("TableRotateToRotations: rotating " + degToSpinRoller + "deg");

        tableSpinner.stopMotor();
        tableSpinner.setSetpoint(degToSpinRoller);
    }

    @Override
    public void execute() {
        boolean edgeChanged = tableSpinner.getDeltaEdge();
        if (edgeChanged) {
            double deltaDegreesOnRoller = Const.kTableSliceRollerDeg * direction;
            prevStableEncoderVal += deltaDegreesOnRoller;
            System.out.println("prevStableEncoderVal: " + prevStableEncoderVal);
            System.out.println("ActualWrongEcoderVal: " + tableSpinner.getEncoderPosition());
            double newSetpoint = degToSpinRoller - prevStableEncoderVal + tableSpinner.getEncoderPosition();
            tableSpinner.setSetpoint(newSetpoint);
        }

        if (Math.abs(degToSpinRoller - tableSpinner.getEncoderPosition()) < 10
                && Math.abs(tableSpinner.getEncoderVelocity()) < 5) {
            System.out.println("TableRotateToRotations: met tolerance "
                    + Math.abs(degToSpinRoller - tableSpinner.getEncoderPosition()) + "deg");
            System.out.println(
                    "TableRotateToRotations: met tolerance " + Math.abs(tableSpinner.getEncoderVelocity()) + "deg/s");

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
            System.out.println("TableRotateToRotations: WARNING: interrupted");

        System.out.println("TableRotateToRotations: INFO: ended");
        tableSpinner.stopMotor();
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