package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.teamcode.opmode.MotorOpMode;

@TeleOp(name = "motorDebug", group = "debug")
public class MotorDebug extends MotorOpMode {

	private DcMotor motorArm;
	private Servo upperLeftServo;
	private Servo lowerLeftServo;
	private Servo upperRightServo;
	private Servo lowerRightServo;
	private Servo colorServo;
	private ColorSensor leftColorSensor;
	private ColorSensor rightColorSensor;

	private org.firstinspires.ftc.teamcode.Gamepad currentGamepad;
	private org.firstinspires.ftc.teamcode.Gamepad previousGamepad;

	private int previousPosition;

	@Override
	public void init() {

		motorArm = hardwareMap.dcMotor.get("motorArm");
		motorArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		//REMEMBER THE INDICES THING
		upperLeftServo = hardwareMap.servo.get("upperLeftServo");
		lowerLeftServo = hardwareMap.servo.get("lowerLeftServo");
		upperRightServo = hardwareMap.servo.get("upperRightServo");
		lowerRightServo = hardwareMap.servo.get("lowerRightServo");
		colorServo = hardwareMap.servo.get("colorServo");

		rightColorSensor = hardwareMap.colorSensor.get("rightColorSensor");
		leftColorSensor = hardwareMap.colorSensor.get("leftColorSensor");
		rightColorSensor.setI2cAddress(I2cAddr.create8bit(0x3c));
		leftColorSensor.setI2cAddress(I2cAddr.create8bit(0x3e));

		rightColorSensor.enableLed(true);
		leftColorSensor.enableLed(true);

		currentGamepad = new org.firstinspires.ftc.teamcode.Gamepad(gamepad1);
		previousPosition = motorArm.getCurrentPosition();

	}

	@Override
	public void loop() {

		super.init(hardwareMap);

		colorServo.setPosition(0);

		com.qualcomm.robotcore.hardware.Gamepad gamepad = currentGamepad.getGamepad();

		motorFrontLeft.setPower(clamp(gamepad1.left_stick_y - gamepad1.left_stick_x));
		motorFrontRight.setPower(clamp(-gamepad1.right_stick_y - gamepad1.right_stick_x));
		motorBackLeft.setPower(clamp(gamepad1.left_stick_y + gamepad1.left_stick_x));
		motorBackRight.setPower(clamp(-gamepad1.right_stick_y + gamepad1.right_stick_x));

		motorArm.setPower(clamp(-(gamepad1.right_trigger + gamepad2.right_trigger - gamepad1.left_trigger - gamepad2.left_trigger)));

//		if (motorArm.getPower() == 0) {
//			motorArm.setTargetPosition(previousPosition);
//
//			if (motorArm.getCurrentPosition() != motorArm.getTargetPosition()) {
//				motorArm.setPower(Math.copySign(0.35, motorArm.getTargetPosition() - motorArm.getCurrentPosition()));
//			}
//
//		} else {
//			previousPosition = motorArm.getCurrentPosition();
//		}

		if (gamepad1.left_bumper || gamepad2.left_bumper) {
			setServosClosed(true);
		} else if (gamepad1.right_bumper || gamepad2.right_bumper) {
			setServosClosed(false);
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

//		telemetry.addData("upperLeftServo", upperLeftServo.getPosition());
//		telemetry.addData("lowerLeftServo", lowerLeftServo.getPosition());
//		telemetry.addData("left", leftColorSensor.red() + " " + leftColorSensor.green() + " " + leftColorSensor.blue() + " " + leftColorSensor.toString());
//		telemetry.addData("right", rightColorSensor.red() + " " + rightColorSensor.green() + " " + rightColorSensor.blue() + " " + rightColorSensor.toString());
//		telemetry.addData("arm", motorArm.getCurrentPosition());
//		telemetry.addData("armTarget", motorArm.getTargetPosition());
//		telemetry.addData("armPower", motorArm.getPower());
//		telemetry.addData("zeroPower", motorArm.getPower() == 0);
//		telemetry.addData("time", getRuntime());
//
//		telemetry.update();

		previousGamepad = new org.firstinspires.ftc.teamcode.Gamepad(gamepad1);
		
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
