package org.firstinspires.ftc.teamcode.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import org.firstinspires.ftc.teamcode.opmode.MotorOpMode;

@Autonomous(name = "GyroTest", group = "default")
public class GyroTest extends MotorOpMode {

	private DcMotor motorFrontLeft;
	private DcMotor motorFrontRight;
	private DcMotor motorBackLeft;
	private DcMotor motorBackRight;

	private GyroSensor gyroSensor;
	private double time;
	private int spins;

	@Override
	public void loop() {
    
		if (gyroSensor.isCalibrating()) {
			time = System.currentTimeMillis() / 1000;
			telemetry.addLine("Calibrating gyro...");
			return;
		}

		int heading = gyroSensor.getHeading();
		double delta = System.currentTimeMillis() / 1000 - time;
		telemetry.addData("delta", delta);

		if (delta < 5) {
			rotateToPosition(0);
		} else if (delta < 10) {
			rotateToPosition(90);
		} else if (delta < 15) {
			rotateToPosition(180);
		} else if (delta < 20) {
			rotateToPosition(270);
		}  else {
			rotateToPosition(0);
		}

	}

	public boolean rotateToPosition(int position) {

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
