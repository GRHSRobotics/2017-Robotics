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

		leftServo.setPosition(0.82);
		rightServo.setPosition(0.82);

		telemetry.addData("time", getRuntime());
		telemetry.addData("leftServoPosition", leftServo.getPosition());
		telemetry.addData("rightServoPosition", rightServo.getPosition());
		telemetry.update();

	}
}