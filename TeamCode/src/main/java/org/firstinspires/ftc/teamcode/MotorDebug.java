package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

@Autonomous(name = "motorDebug", group = "debug")
public class MotorDebug extends OpMode {

	private DcMotor motorFrontLeft;
	private DcMotor motorFrontRight;
	private DcMotor motorBackLeft;
	private DcMotor motorBackRight;
	private DcMotor motorArm;

	@Override
	public void init() {
		motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
		motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
		motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
		motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
		motorArm = hardwareMap.dcMotor.get("motorArm");
	}

	@Override
	public void loop() {

		motorFrontLeft.setPower(clamp(gamepad1.left_stick_y + gamepad1.left_stick_x));
		motorFrontRight.setPower(clamp(-gamepad1.right_stick_y + gamepad1.right_stick_x));
		motorBackLeft.setPower(clamp(gamepad1.left_stick_y - gamepad1.left_stick_x));
		motorBackRight.setPower(clamp(-gamepad1.right_stick_y - gamepad1.right_stick_x));

		motorArm.setPower(clamp(gamepad1.right_trigger - gamepad1.left_trigger));

		telemetry.update();
		
	}

	private float clamp(float f) {
		return Math.max(-1, Math.min(1, f));
	}

}