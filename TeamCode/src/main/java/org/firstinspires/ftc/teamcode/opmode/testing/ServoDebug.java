package org.firstinspires.ftc.teamcode.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by AllTheMegahertz on 9/10/2017.
 */

@Autonomous(name="Servo Debug", group="debug")
public class ServoDebug extends OpMode {

	private Servo leftServo;
	private Servo rightServo;

	@Override
	public void init() {

		leftServo = hardwareMap.servo.get("leftServo");
		rightServo = hardwareMap.servo.get("rightServo");
		leftServo.setPosition(0.17);
		rightServo.setPosition(0.17);
	}

	@Override
	public void loop() {

		if (leftServo.getPosition() > 0.17 && gamepad1.left_trigger > 0) {
			leftServo.setPosition(leftServo.getPosition() - 0.01);
		}
		else if (leftServo.getPosition() < 0.81 && gamepad1.left_bumper) {
			leftServo.setPosition(leftServo.getPosition() + 0.01);
		}

		if (rightServo.getPosition() > 0.17 && gamepad1.right_trigger > 0) {
			rightServo.setPosition(rightServo.getPosition() - 0.01);
		}
		else if (rightServo.getPosition() < 0.81 && gamepad1.right_bumper) {
			rightServo.setPosition(rightServo.getPosition() + 0.01);
		}

		if (gamepad1.dpad_up) {
			leftServo.setPosition(0.81);
		}
		else if (gamepad1.dpad_down) {
			leftServo.setPosition(0.17);
		}

		if (gamepad1.dpad_right) {
			rightServo.setPosition(0.81);
		}
		else if (gamepad1.dpad_left) {
			rightServo.setPosition(0.17);
		}

		telemetry.addData("time", getRuntime());
		telemetry.addData("leftServoPosition", leftServo.getPosition());
		telemetry.addData("rightServoPosition", rightServo.getPosition());
		telemetry.update();

	}
}