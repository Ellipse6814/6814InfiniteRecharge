package frc.robot.subsystems;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.wpi.first.wpilibj.Timer;

public class Logger {
    private static Logger instance;

    public static Logger getInstance() {
        if (instance == null)
            instance = new Logger();
        return instance;
    }

    private PrintWriter writer = null;
    public boolean debug = true;

    public static void main(String[] args) {
        Logger l = new Logger();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            // ls.log("title", "message");
        }
        System.out.println((System.currentTimeMillis() - startTime) / 1000.0);
        l.flush();
        System.out.println((System.currentTimeMillis() - startTime) / 1000.0);
    }

    public Logger() {
        try {
            String time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
            writer = new PrintWriter("/home/lvuser/Log " + time + ".csv");
            // writer = new PrintWriter("./Log" + time + ".csv");
            writer.println("Timestamp, Title, Message");
            System.out.println("Log started");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void log(Object message) {
        writer.print(message + "\n");
        if (debug)
            System.out.println(message);
    }

    public void log(String title, Object msg) {
        log(Timer.getFPGATimestamp() + "," + title + "," + msg);
    }
    // public void log(String title, Object message) {
    // writer.println(Timer.getFPGATimestamp() + "," + title + "," + message);
    // }

    public void flush() {
        writer.flush();
        System.out.println("Log flushed");
    }
}

// package frc.robot;

// import java.io.FileNotFoundException;
// import java.io.PrintWriter;
// import java.text.SimpleDateFormat;
// import java.util.Date;

// import edu.wpi.first.wpilibj.Timer;

// public class Logger {
// private PrintWriter writer = null;

// public static void main(String[] args) {
// Logger l = new Logger();

// long startTime = System.currentTimeMillis();

// for (int i = 0; i < 1000; i++) {
// l.log("title", "message");
// }
// System.out.println((System.currentTimeMillis() - startTime) / 1000.0);
// l.flush();
// System.out.println((System.currentTimeMillis() - startTime) / 1000.0);
// }

// public Logger() {
// try {
// String time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
// writer = new PrintWriter("/home/lvuser/Log " + time + ".csv");
// // writer = new PrintWriter("./Log" + time + ".csv");
// writer.println("Timestamp, Title, Message");
// System.out.println("Log started");
// } catch (FileNotFoundException e) {
// e.printStackTrace();
// }
// }

// public void log(Object message) {
// log("Default", message);
// }

// public void log(String title, Object message) {
// writer.println(Timer.getFPGATimestamp() + "," + title + "," + message);
// }

// public void flush() {
// writer.flush();
// System.out.println("Log flushed");
// }
// }