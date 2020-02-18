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
    public Compressor compressor = new Compressor();

    public Drive drive = Drive.getInstance();
    public IntakeAngleMotor intakeAngleMotor = IntakeAngleMotor.getInstance();
    public IntakePiston intakePiston = IntakePiston.getInstance();
    public IntakeRoller intakeRoller = IntakeRoller.getInstance();
    public TableMotor tableMotor = TableMotor.getInstance();
    public TablePiston tablePiston = TablePiston.getInstance();
    public ElevatorMotor elevatorMotor = ElevatorMotor.getInstance();
    public ElevatorBrake elevatorBrake = ElevatorBrake.getInstance();
    public LED led = LED.getInstance();

    // Operator Interfaces
    public Joystick driverJoy = new Joystick(0);
    public Joystick operatorJoy = new Joystick(1);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
        // JoystickButton button = new JoystickButton(driverJoy, 1);
        // button.whenPressed(command)
        robotStartInitSequence();
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

        intakeAngleMotor.setDefaultCommand(new DoNothing(intakeAngleMotor));
        intakePiston.setDefaultCommand(new DoNothing(intakePiston));
        intakeRoller.setDefaultCommand(new IntakeSetRoller(Const.kIntakeHoldSpd));

        elevatorMotor.setDefaultCommand(new ElevatorSetPercentage(0));
        elevatorBrake.setDefaultCommand(new DoNothing(elevatorBrake));

        tableMotor.setDefaultCommand(new TableSetMotor(0));
        tablePiston.setDefaultCommand(new DoNothing(tablePiston));
    }

    private void robotStartInitSequence() {
        new ElevatorGoto(0, true).schedule();
        new IntakeHide().schedule();
        new TableEngage(false).schedule();
        new TableSetMotor(0).schedule();

        led.set(LED.LEDState.Idle);

    }

    public Command getAutonomousCommand() {
        Command autoCommand = new DriveAutoTime(3, 0.5);
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
