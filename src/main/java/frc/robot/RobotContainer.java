package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private Logger logger = Logger.getInstance();

  private void log(Object msg) {
    logger.log("RobotContainer", msg);
  }

  // Subsystems
  private Compressor compressor = new Compressor();
  private Table table = Table.getInstance();
  private Drive drive = Drive.getInstance();
  private Elevator elevator = Elevator.getInstance();
  private Intake intake = Intake.getInstance();
  private LED led = LED.getInstance();

  // Operator Interfaces
  private Joystick driverJoy = new Joystick(0);
  private Joystick operatorJoy = new Joystick(1);

  // Auto Commands
  private Command autoCommand = null;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    log("Inited");
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    drive.setDefaultCommand(new DriveTeleOp(() -> driverJoy.getRawAxis(1), () -> driverJoy.getRawAxis(4)));
    intake.setDefaultCommand(new IntakeSetRoller(Const.kIntakeHoldSpd));
    elevator.setDefaultCommand(new ElevatorSetPercentage(0));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    autoCommand = new DriveAutoTime(3, 0.5);
    return autoCommand;
  }

  public void debug() {
    // TODO: add subsystems
  }
}
