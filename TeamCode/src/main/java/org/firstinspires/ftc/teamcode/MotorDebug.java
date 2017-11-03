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

	@Override
	public void init() {
		motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
		motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
		motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
		motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
	}

	@Override
	public void loop() {

		motorFrontLeft.setPower(-(gamepad1.left_stick_y - gamepad1.left_stick_x) / 2);
		motorFrontRight.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) / 2);
		motorBackLeft.setPower(-(gamepad1.left_stick_y + gamepad1.left_stick_x) / 2);
		motorBackRight.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x) / 2);

		telemetry.update();
		
	}
}
