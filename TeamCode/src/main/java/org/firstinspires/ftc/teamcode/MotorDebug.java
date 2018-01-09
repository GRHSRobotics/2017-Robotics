package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "motorDebug", group = "debug")
public class MotorDebug extends OpMode {

	private DcMotor motorFrontLeft;
	private DcMotor motorFrontRight;
	private DcMotor motorBackLeft;
	private DcMotor motorBackRight;
	private DcMotor motorArm;
	private Servo upperLeftServo;
	private Servo lowerLeftServo;
	private Servo upperRightServo;
	private Servo lowerRightServo;
	private Servo colorServo;
	private ColorSensor leftColorSensor;
	private ColorSensor rightColorSensor;

	private Gamepad currentGamepad;
	private Gamepad previousGamepad;

	private int previousPosition;

	@Override
	public void init() {


		motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
		motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
		motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
		motorBackRight = hardwareMap.dcMotor.get("motorBackRight");

		motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
		motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
		motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
		motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

		motorArm = hardwareMap.dcMotor.get("motorArm");
		motorArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		//REMEMBER THE INDICES THING
		upperLeftServo = hardwareMap.servo.get("upperLeftServo");
		lowerLeftServo = hardwareMap.servo.get("lowerLeftServo");
		upperRightServo = hardwareMap.servo.get("upperRightServo");
		lowerRightServo = hardwareMap.servo.get("lowerRightServo");
		colorServo = hardwareMap.servo.get("colorServo");

		currentGamepad = new Gamepad(gamepad1);
		previousPosition = motorArm.getCurrentPosition();

	}

	@Override
	public void loop() {

		colorServo.setPosition(0);

		com.qualcomm.robotcore.hardware.Gamepad gamepad = currentGamepad.getGamepad();

		motorFrontLeft.setPower(clamp(gamepad1.left_stick_y - gamepad1.left_stick_x));
		motorFrontRight.setPower(clamp(-gamepad1.right_stick_y - gamepad1.right_stick_x));
		motorBackLeft.setPower(clamp(gamepad1.left_stick_y + gamepad1.left_stick_x));
		motorBackRight.setPower(clamp(-gamepad1.right_stick_y + gamepad1.right_stick_x));

		motorArm.setPower(clamp(-(gamepad1.right_trigger + gamepad2.right_trigger - gamepad1.left_trigger - gamepad2.left_trigger)));

		if (motorArm.getPower() == 0) {
			motorArm.setTargetPosition(previousPosition);

			if (motorArm.getCurrentPosition() != motorArm.getTargetPosition()) {
				motorArm.setPower(Math.copySign(0.35, motorArm.getTargetPosition() - motorArm.getCurrentPosition()));
			}

		} else {
			previousPosition = motorArm.getCurrentPosition();
		}

		if (gamepad1.left_bumper || gamepad2.left_bumper) {
			upperLeftServo.setPosition(0.65);
			lowerLeftServo.setPosition(0.25);
			upperRightServo.setPosition(0.85);
			lowerRightServo.setPosition(0.05);
		} else if (gamepad1.right_bumper || gamepad2.right_bumper) {
			upperLeftServo.setPosition(0.2);
			lowerLeftServo.setPosition(0.75);
			upperRightServo.setPosition(0.15);
			lowerRightServo.setPosition(0.8);
		}

		//Incrementation of arm position for debugging
		int incrementationSpeed = 10;

		if (gamepad1.back) {
			incrementationSpeed = 50;
		}

		if (gamepad1.a) {
			motorArm.setTargetPosition(motorArm.getCurrentPosition() + incrementationSpeed);
		}
		else if (gamepad1.b) {
			motorArm.setTargetPosition(motorArm.getCurrentPosition() - incrementationSpeed);
		}

		telemetry.addData("upperLeftServo", upperLeftServo.getPosition());
		telemetry.addData("lowerLeftServo", lowerLeftServo.getPosition());
		telemetry.addData("arm", motorArm.getCurrentPosition());
		telemetry.addData("armTarget", motorArm.getTargetPosition());
		telemetry.addData("armPower", motorArm.getPower());
		telemetry.addData("zeroPower", motorArm.getPower() == 0);
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
