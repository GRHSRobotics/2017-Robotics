package org.firstinspires.ftc.teamcode.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ServoTesting", group = "manual")
public class ServoTest extends OpMode {

	private Servo servo;

	@Override
	public void init() {
		servo = hardwareMap.servo.get("servo0");
	}

	@Override
	public void loop() {

		servo.setPosition(servo.getPosition() + (gamepad1.right_trigger - gamepad1.left_trigger) / 100);

		telemetry.addData("servo", servo.getPosition());
		telemetry.update();

	}

}
