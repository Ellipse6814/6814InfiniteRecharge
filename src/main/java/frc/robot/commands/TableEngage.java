package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Table;

public class TableEngage extends CommandBase {
  private final Table tableSpinner = Table.getInstance();
  private boolean engageRoller;

  public TableEngage(boolean engageRoller) {
    this.engageRoller = engageRoller;
  }

  @Override
  public void initialize() {
    System.out.println("Starting CMD: TableEngage");
    tableSpinner.engageRoller(engageRoller);
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
