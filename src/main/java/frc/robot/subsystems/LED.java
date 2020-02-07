package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Const;

public class LED extends SubsystemBase {

  private static LED instance;

  public static LED getInstance() {
    if (instance == null) {
      instance = new LED();
    }
    return instance;
  }

  private SpeedController led = new Spark(Const.kLEDPort);
  public LEDState state;

  private double phaseShift;

  private LED() {
    phaseShift = Timer.getFPGATimestamp();
    set(LEDState.Disconnected);
  }

  @Override
  public void periodic() {
    updateLEDs();
  }

  @Deprecated
  public void set(LEDState state) {
    this.state = state;
  }

  private void updateLEDs() {
    LEDColor wantedColor = LEDColor.Off;

    // if (state == LEDState.Disconnected)
    // wantedColor = ((Timer.getFPGATimestamp()-phaseShift) % 1 > 0.5) ?
    // LEDColor.Off :
    // LEDColor.Yellow;
    // else if (state == LEDState.Normal)
    // wantedColor = ((Timer.getFPGATimestamp()-phaseShift) % 1 > 0.5) ?
    // LEDColor.Off :
    // LEDColor.Green;
    // else if (state == LEDState.PrepOut)
    // wantedColor = LEDColor.Yellow;
    // else if (state == LEDState.DoneOut)
    // wantedColor = LEDColor.Aqua;
    // else if (state == LEDState.Danger)
    // wantedColor = LEDColor.Red;
    // else if (state == LEDState.WaitingReset)
    // wantedColor = ((Timer.getFPGATimestamp()-phaseShift) % 1 > 0.5) ?
    // LEDColor.Off :
    // LEDColor.Red;

    led.set(calcLEDSpd(wantedColor));
  }

  private double calcLEDSpd(LEDColor state) {

    double spd;
    if (state == LEDColor.Green)
      spd = 0.75;
    else if (state == LEDColor.White)
      spd = 0.93;
    else if (state == LEDColor.Yellow)
      spd = 0.69;
    else if (state == LEDColor.Violet)
      spd = 0.91;
    else if (state == LEDColor.Off)
      spd = 0.99;
    else if (state == LEDColor.ColorGradient2)
      spd = 0.41;
    else if (state == LEDColor.ColorBlend1)
      spd = -0.03;
    else if (state == LEDColor.Aqua)
      spd = 0.81;
    else if (state == LEDColor.Red)
      spd = 0.61;
    else if (state == LEDColor.Pink)
      spd = 0.59;
    else
      spd = 0.93;

    return spd;
  }
}

enum LEDColor {
  Green, Yellow, White, Violet, Off, ColorGradient2, ColorBlend1, Aqua, Red, Pink
}

enum LEDState {
  Off, Normal, Disconnected, DisconnectedNotReady
}