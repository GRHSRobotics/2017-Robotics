package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

@Autonomous(name = "GyroTest", group = "default")
public class GyroTest extends OpMode {

	private DcMotor motorFrontLeft;
	private DcMotor motorFrontRight;
	private DcMotor motorBackLeft;
	private DcMotor motorBackRight;

	private GyroSensor gyroSensor;
	private long time;
	private int spins;

	@Override
	public void init() {

		motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
		motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
		motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
		motorBackRight = hardwareMap.dcMotor.get("motorBackRight");

		gyroSensor = hardwareMap.gyroSensor.get("gyro");

		motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
		motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
		motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
		motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

		gyroSensor.calibrate();

		time = System.currentTimeMillis();

	}

	@Override
	public void loop() {

		int heading = gyroSensor.getHeading();
		if (!rotateToPosition(0) || System.currentTimeMillis() > time + 10000);
		else if (!rotateToPosition(90) || System.currentTimeMillis() > time + 20000);
		else if (!rotateToPosition(180) || System.currentTimeMillis() > time + 30000);
		else if (!rotateToPosition(270) || System.currentTimeMillis() > time + 40000);
		else if (!rotateToPosition(360) || System.currentTimeMillis() > time + 50000);

	}

	public boolean rotateToPosition(int position) {

		final int buffer = 10;
		int high = position + buffer + spins * 360;
		int low = position - buffer + spins * 360;

		int heading = gyroSensor.getHeading();

		telemetry.addData("heading", heading);
		telemetry.addData("target", position);
		telemetry.addData("high", high);
		telemetry.addData("low", low);

		if (heading < high && heading > low) {

			motorBackLeft.setPower(0);
			motorBackRight.setPower(0);
			motorFrontLeft.setPower(0);
			motorFrontRight.setPower(0);
			return true;

		}

		else if (heading <= low && heading < clamp(position + 180)) {

			motorBackLeft.setPower(0.25);
			motorBackRight.setPower(0.25);
			motorFrontLeft.setPower(0.25);
			motorFrontRight.setPower(0.25);

		}

		else if (heading >= high && heading <= clamp(position + 180)) {

			motorBackLeft.setPower(-0.25);
			motorBackRight.setPower(-0.25);
			motorFrontLeft.setPower(-0.25);
			motorFrontRight.setPower(-0.25);

		}

		return false;

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

}
