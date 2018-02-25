package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.teamcode.opmode.MotorOpMode;

@TeleOp(name = "motorDebug", group = "debug")
public class MotorDebug extends MotorOpMode {

	private DcMotor motorArm;
	private Servo colorServo;

	private int previousPosition;

	@Override
	public void init() {

		super.init(hardwareMap);

		motorArm = hardwareMap.dcMotor.get("motorArm");
		motorArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		//REMEMBER THE INDICES THING
		colorServo = hardwareMap.servo.get("colorServo");

		previousPosition = motorArm.getCurrentPosition();

	}

	@Override
	public void loop() {

		colorServo.setPosition(0);
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
		
	}

	@Override
	public void stop() {
		setPower(0);
		motorArm.setPower(0);
		super.stop();
	}

	@Override
	public float clamp(float f) {
		return Math.max(-1, Math.min(1, f));
	}

}
