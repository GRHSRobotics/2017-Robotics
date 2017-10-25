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
		servo.setPosition(0.82);

	}

	@Override
	public void loop() {

		if (servo.getPosition() > 0.8) {
			servo.setPosition(0.17);
		}
		else if (servo.getPosition() < 0.2) {
			servo.setPosition(0.82);
		}

		telemetry.addData("time", getRuntime());
		telemetry.addData("servoPosition", servo.getPosition());
		telemetry.update();

	}
}