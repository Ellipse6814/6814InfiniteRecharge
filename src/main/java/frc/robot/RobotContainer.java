package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveAutoTime;
import frc.robot.commands.DriveTeleOp;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.ElevatorBrake;
import frc.robot.subsystems.ElevatorMotor;
import frc.robot.subsystems.IntakeAngleMotor;
import frc.robot.subsystems.IntakePiston;
import frc.robot.subsystems.IntakeRoller;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Logger;
import frc.robot.subsystems.TableMotor;
import frc.robot.subsystems.TablePiston;

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
  // private Table table = Table.getInstance();
  // private Drive drive = Drive.getInstance();
  // private Elevator elevator = Elevator.getInstance();
  // private Intake intake = Intake.getInstance();
  // private LED led = LED.getInstance();

  private Drive drive = Drive.getInstance();
  private IntakeAngleMotor intakeAngleMotor = IntakeAngleMotor.getInstance();
  private IntakePiston intakePiston = IntakePiston.getInstance();
  private IntakeRoller intakeRoller = IntakeRoller.getInstance();
  private TableMotor tableMotor = TableMotor.getInstance();
  private TablePiston tablePiston = TablePiston.getInstance();
  private ElevatorMotor elevatorMotor = ElevatorMotor.getInstance();
  private ElevatorBrake elevatorBrake = ElevatorBrake.getInstance();
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
    JoystickButton button = new JoystickButton(driverJoy, 1);
    button.whenPressed(command)

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
    // intake.setDefaultCommand(new IntakeSetRoller(Const.kIntakeHoldSpd));
    // elevator.setDefaultCommand(new ElevatorSetPercentage(0));
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
    drive.debug();
    intakeAngleMotor.debug();
    intakePiston.debug();
    intakeRoller.debug();
    tableMotor.debug();
    tablePiston.debug();
    elevatorMotor.debug();
    elevatorBrake.debug();
    led.debug();
  }
}
