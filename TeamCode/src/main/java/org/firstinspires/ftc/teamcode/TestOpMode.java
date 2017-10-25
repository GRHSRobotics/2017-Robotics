package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by AllTheMegahertz on 9/10/2017.
 */

@Autonomous(name="Basic Debug", group="debug")
public class TestOpMode extends OpMode {

	private Servo servo;

	@Override
	public void init() {

		servo = hardwareMap.servo.get("linearServo");

	}

	@Override
	public void loop() {

		if (servo.getPosition() > 1) {
			servo.setPosition(servo.getPosition() + 0.01);
		}
		else if (servo.getPosition() > 0) {
			servo.setPosition(servo.getPosition() - 0.01);
		}

		telemetry.addData("time", getRuntime());
		telemetry.addData("servoPosition", servo.getPosition());
		telemetry.update();

	}
}