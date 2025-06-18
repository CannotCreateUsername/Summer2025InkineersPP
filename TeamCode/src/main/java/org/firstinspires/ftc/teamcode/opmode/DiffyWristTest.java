package org.firstinspires.ftc.teamcode.opmode;// In your OpMode (e.g., TeleOp or Autonomous)

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystem.DiffyWristClaw; // Make sure this path is correct

@TeleOp(name = "Differential Wrist Test", group = "Test")
public class DiffyWristTest extends LinearOpMode {

    private DiffyWristClaw wrist;

    @Override
    public void runOpMode() {
        wrist = new DiffyWristClaw(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        double targetAngle = 0.5; // Start at center
        double targetTilt = 0.5;  // Start at neutral tilt

        while (opModeIsActive()) {
            // Example control using gamepad1 sticks
            // Adjust sensitivity as needed
            targetAngle += gamepad1.left_stick_x * 0.02;
            targetTilt += gamepad1.right_stick_y * 0.02;

            // Clamp values again just in case of rapid stick movements
            targetAngle = Math.max(0.0, Math.min(1.0, targetAngle));
            targetTilt = Math.max(0.0, Math.min(1.0, targetTilt));

            wrist.setWristPosition(targetAngle, targetTilt);

            telemetry.addData("Target Angle", "%.2f", targetAngle);
            telemetry.addData("Target Tilt", "%.2f", targetTilt);
            telemetry.addData("Left Servo Pos", "%.2f", wrist.getLeftServoPosition());
            telemetry.addData("Right Servo Pos", "%.2f", wrist.getRightServoPosition());
            telemetry.update();

            // Add a small sleep to prevent busy-waiting and allow other processes
            sleep(20);
        }
    }
}