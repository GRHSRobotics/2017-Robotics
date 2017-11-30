package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "motorDebug", group = "debug")
public class MotorDebug extends OpMode {

	private DcMotor motorFrontLeft;
	private DcMotor motorFrontRight;
	private DcMotor motorBackLeft;
	private DcMotor motorBackRight;
	private DcMotor motorArm;
	private Servo servo0;
	private Servo servo1;

	private Gamepad currentGamepad;
	private Gamepad previousGamepad;

	@Override
	public void init() {

		motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
		motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
		motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
		motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
		motorArm = hardwareMap.dcMotor.get("motorArm");

		servo0 = hardwareMap.servo.get("servo0");
		servo1 = hardwareMap.servo.get("servo1");

		currentGamepad = new org.firstinspires.ftc.teamcode.Gamepad(gamepad1);

	}

	@Override
	public void loop() {

		com.qualcomm.robotcore.hardware.Gamepad gamepad = currentGamepad.getGamepad();

		motorFrontLeft.setPower(clamp(gamepad1.left_stick_y + gamepad1.left_stick_x));
		motorFrontRight.setPower(clamp(-gamepad1.right_stick_y + gamepad1.right_stick_x));
		motorBackLeft.setPower(clamp(gamepad1.left_stick_y - gamepad1.left_stick_x));
		motorBackRight.setPower(clamp(-gamepad1.right_stick_y - gamepad1.right_stick_x));

		motorArm.setPower(clamp(gamepad1.right_trigger + gamepad2.right_trigger - gamepad1.left_trigger - gamepad2.left_trigger));

		if (gamepad1.left_bumper || gamepad2.left_bumper) {
			servo0.setPosition(0.3);
			servo1.setPosition(0.3);
		} else if (gamepad1.right_bumper || gamepad2.right_bumper) {
			servo0.setPosition(0.725);
			servo1.setPosition(0.725);
		}

		//Incrementation of arm position for debugging
		if (gamepad1.a && !previousGamepad.getGamepad().a) {
			motorArm.setTargetPosition(motorArm.getCurrentPosition() + 10);
		}
		else if (gamepad1.b && !previousGamepad.getGamepad().b) {
			motorArm.setTargetPosition(motorArm.getCurrentPosition() - 10);
		}

		telemetry.addData("servo0", servo0.getPosition());
		telemetry.addData("servo1", servo1.getPosition());
		telemetry.addData("arm", motorArm.getCurrentPosition());
		telemetry.addData("armTarget", motorArm.getTargetPosition());
		telemetry.addData("time", getRuntime());

		telemetry.update();

		previousGamepad = new Gamepad(gamepad1);
		
	}

	@Override
	public void stop() {
		motorBackLeft.setPower(0);
		motorBackRight.setPower(0);
		motorFrontLeft.setPower(0);
		motorFrontRight.setPower(0);
		motorArm.setPower(0);
		super.stop();
	}

	private float clamp(float f) {
		return Math.max(-1, Math.min(1, f));
	}

}
