package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;

import java.util.ArrayList;

public abstract class MotorOpMode extends OpMode {

	protected DcMotor motorFrontLeft;
	protected DcMotor motorFrontRight;
	protected DcMotor motorBackLeft;
	protected DcMotor motorBackRight;
	private ArrayList<DcMotor> motors;

	private Servo upperLeftServo;
	private Servo lowerLeftServo;
	private Servo upperRightServo;
	private Servo lowerRightServo;

	protected GyroSensor gyroSensor;

	protected int target;

	private int spins = 0;

	public void init() {
		this.init(hardwareMap);
	}

	public void init(HardwareMap hardwareMap) {

		motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
		motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
		motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
		motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
		motors = new ArrayList<>();

		enableBreaking(false);

		upperLeftServo = hardwareMap.servo.get("upperLeftServo");
		lowerLeftServo = hardwareMap.servo.get("lowerLeftServo");
		upperRightServo = hardwareMap.servo.get("upperRightServo");
		lowerRightServo = hardwareMap.servo.get("lowerRightServo");

		gyroSensor = hardwareMap.gyroSensor.get("imu");
		gyroSensor.calibrate();

	}

	public void setServosClosed(boolean b) {

		if (b) {
			upperLeftServo.setPosition(0.65);
			lowerLeftServo.setPosition(0.25);
			upperRightServo.setPosition(0.15);
			lowerRightServo.setPosition(0.8);
		} else {
			upperLeftServo.setPosition(0.2);
			lowerLeftServo.setPosition(0.75);
			upperRightServo.setPosition(0.85);
			lowerRightServo.setPosition(0.05);
		}

	}
	
	public ArrayList<DcMotor> getMotors(HardwareMap hardwareMap) {
		return motors;
	}

	public boolean rotateToPosition(int position) {

		target = position;

		if (gyroSensor.isCalibrating()) {
			time = System.currentTimeMillis() / 1000;
			telemetry.addLine("Calibrating gyro...");
			return false;
		}

		int heading = clamp(gyroSensor.getHeading());
		position = clamp(position + spins * 360, false);

		final int buffer = 10;

		int delta = Math.abs(position - heading);
		if (delta > 180) {
			position = 360 - position;
		}

		int high = position + buffer + spins * 360;
		int low = position - buffer + spins * 360;

		telemetry.addData("heading", heading);
		telemetry.addData("target", position);
		telemetry.addData("clamp" , clamp(position + 180, false));

		if (heading < high && heading > low) {

			motorBackLeft.setPower(0);
			motorBackRight.setPower(0);
			motorFrontLeft.setPower(0);
			motorFrontRight.setPower(0);
			return true;

		}

		else if (heading <= low && heading < position) {

			motorBackLeft.setPower(0.25);
			motorBackRight.setPower(0.25);
			motorFrontLeft.setPower(0.25);
			motorFrontRight.setPower(0.25);

		}

		else if (heading >= high && heading >= position) {

			motorBackLeft.setPower(-0.25);
			motorBackRight.setPower(-0.25);
			motorFrontLeft.setPower(-0.25);
			motorFrontRight.setPower(-0.25);

		}

		return false;

	}

	public boolean rotate(int position) {

		if (gyroSensor.isCalibrating()) {
			time = System.currentTimeMillis() / 1000;
			telemetry.addLine("Calibrating gyro...");
			return false;
		}

		return rotateToPosition(clamp(gyroSensor.getHeading() + position, false));
	}

	public void enableBreaking(boolean enable) {

		DcMotor.ZeroPowerBehavior behavior = DcMotor.ZeroPowerBehavior.FLOAT;

		if (enable) {
			behavior = DcMotor.ZeroPowerBehavior.BRAKE;
		}

		for (DcMotor motor : motors) {
			motor.setZeroPowerBehavior(behavior);
		}

	}

	public void setPower(double power) {

		motorFrontLeft.setPower(-power);
		motorFrontRight.setPower(power);
		motorBackLeft.setPower(-power);
		motorBackRight.setPower(power);

	}

	private int clamp(int i) {

		if (i > 360) {
			spins++;
			return i - 360;
		}

		if (i < 0) {
			spins--;
			return i + 360;
		}

		return i;

	}

	private int clamp(int i, boolean b) {

		if (b) {
			return clamp(i);
		}

		if (i > 360) {
			return i - 360;
		}

		if (i < 0) {
			return i + 360;
		}

		return i;

	}

}
