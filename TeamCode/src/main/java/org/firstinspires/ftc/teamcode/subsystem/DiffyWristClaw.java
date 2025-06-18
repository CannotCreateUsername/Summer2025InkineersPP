package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class DiffyWristClaw {

    private final Servo leftServo;
    private final Servo rightServo;

    // Adjust these names to match your robot's configuration
    private static final String SERVO_LEFT = "wristServoLeft";
    private static final String SERVO_RIGHT = "wristServoRight";

    /**
     * Initializes the differential wrist.
     *
     * @param hardwareMap The hardware map from the OpMode.
     */
    public DiffyWristClaw(HardwareMap hardwareMap) {
        leftServo = hardwareMap.get(Servo.class, SERVO_LEFT);
        rightServo = hardwareMap.get(Servo.class, SERVO_RIGHT);

        // Optional: Set initial servo directions if needed.
        // By default, they are Servo.Direction.FORWARD
        // servo1.setDirection(Servo.Direction.REVERSE);
        // servo2.setDirection(Servo.Direction.FORWARD);
    }

    // TODO: Working around servo limits are weird. In certain positions, servos cannot rotate any further with the given input.
    //  Perhaps try continuous rotation configuration.

    /**
     * Sets the desired angle and tilt for the wrist.
     *
     * @param angle The desired angle of the wrist (0.0 to 1.0).
     *              0.0 typically represents one extreme (e.g., full left or full down)
     *              1.0 typically represents the other extreme (e.g., full right or full up)
     * @param tilt  The desired tilt of the wrist (0.0 to 1.0).
     *              0.0 typically represents one extreme tilt (e.g., claw open or flat)
     *              1.0 typically represents the other extreme tilt (e.g., claw closed or angled)
     *
     * Note: The exact interpretation of "angle" and "tilt" depends on how your
     * servos are mechanically linked. You may need to experiment to find the
     * desired behavior.
     */
    public void setWristPosition(double angle, double tilt) {
        // Ensure inputs are within the valid range [0.0, 1.0]
        // Same as doing if (angle < 0.0) { angle = 0.0; } and (angle > 1.0) { angle = 1.0; }
        angle = Math.max(0.0, Math.min(1.0, angle));
        tilt = Math.max(0.0, Math.min(1.0, tilt));

        // Calculate servo positions for differential movement
        // These formulas are common for differential mechanisms.
        // You might need to adjust or swap them based on your specific setup.
        double servo1Position = angle + tilt;
        double servo2Position = angle - tilt;

        // Normalize the positions to be within [0.0, 1.0]
        // This scaling ensures that the combined movements don't exceed servo limits.
        // It effectively halves the range of each individual input (angle/tilt)
        // when combined.
        servo1Position = (servo1Position * 0.75);
        servo2Position = (servo2Position * 0.75);

        // It's also common to see another approach where one servo might be reversed
        // and the tilt is added/subtracted differently. For example:
        // double servo1Position = angle + tilt;
        // double servo2Position = (1.0 - angle) + tilt; // Assuming servo2 needs to move oppositely for "angle"
        // And then normalize these.
        // The key is experimentation with your physical setup.

        // Ensure the calculated positions are still within the servo limits
        servo1Position = Math.max(0.0, Math.min(1.0, servo1Position));
        servo2Position = Math.max(0.0, Math.min(1.0, servo2Position));


        leftServo.setPosition(servo1Position);
        rightServo.setPosition(servo2Position);
    }

    /**
     * Example: Sets the wrist to a "neutral" or "centered" angle and a specific tilt.
     * @param tilt The desired tilt (0.0 to 1.0).
     */
    public void setNeutralAngleWithTilt(double tilt) {
        setWristPosition(0.5, tilt); // 0.5 for angle is typically center
    }

    /**
     * Example: Sets the wrist to a specific angle with a "neutral" or "flat" tilt.
     * @param angle The desired angle (0.0 to 1.0).
     */
    public void setNeutralTiltWithAngle(double angle) {
        setWristPosition(angle, 0.5); // 0.5 for tilt might be a neutral or flat position
    }

    /**
     * Gets the current position of servo 1.
     * @return The position of servo 1 (0.0 to 1.0).
     */
    public double getLeftServoPosition() {
        return leftServo.getPosition();
    }

    /**
     * Gets the current position of servo 2.
     * @return The position of servo 2 (0.0 to 1.0).
     */
    public double getRightServoPosition() {
        return rightServo.getPosition();
    }
}