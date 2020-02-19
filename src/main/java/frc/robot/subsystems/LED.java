package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Const;
import frc.robot.Util.Debugable;

public class LED implements Debugable {

    private Logger logger = Logger.getInstance();

    private void log(Object msg) {
        logger.log("LED", msg);
    }

    private static LED instance;

    public static LED getInstance() {
        if (instance == null) {
            instance = new LED();
        }
        return instance;
    }

    private SpeedController led = new Spark(Const.kLEDPort);

    private double phaseShift;

    private int requireWorking, requireIdle;
    private LEDState state;

    private LED() {
        require(LEDState.Yellow);
        log("Init");
    }

    public void periodic() {
        updateLEDs();
    }

    public void require(LEDState state) {
        changeRequire(state, 1);
    }

    public void clearRequire(LEDState state) {
        changeRequire(state, -1);
    }

    private void changeRequire(LEDState state, int change) {
        if (state == LEDState.Red)
            requireWorking += change;
        else if (state == LEDState.Yellow)
            requireIdle += change;

        if (requireWorking > 0)
            state = LEDState.Red;
        else if (requireIdle > 0)
            state = LEDState.Yellow;
        else
            state = LEDState.Green;

        phaseShift = Timer.getFPGATimestamp();
    }

    private void updateLEDs() {
        LEDColor wantedColor = LEDColor.Off;
        if (state == LEDState.Green)
            wantedColor = ((Timer.getFPGATimestamp() - phaseShift) % Const.kLEDPeriod < Const.kLEDHalfPeriod)
                    ? LEDColor.Green
                    : LEDColor.Off;
        else if (state == LEDState.Red)
            wantedColor = ((Timer.getFPGATimestamp() - phaseShift) % Const.kLEDPeriod < Const.kLEDHalfPeriod)
                    ? LEDColor.Red
                    : LEDColor.Off;
        else if (state == LEDState.Yellow)
            wantedColor = ((Timer.getFPGATimestamp() - phaseShift) % Const.kLEDPeriod < Const.kLEDHalfPeriod)
                    ? LEDColor.Yellow
                    : LEDColor.Off;

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

    public void debug() {
    }

    private enum LEDColor {
        Green, Yellow, White, Violet, Off, ColorGradient2, ColorBlend1, Aqua, Red, Pink
    }

    public enum LEDState {
        Off, Green, Red, Yellow
    }
}
