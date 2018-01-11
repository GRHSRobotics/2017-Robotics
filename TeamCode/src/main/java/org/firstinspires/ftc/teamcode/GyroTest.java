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
	}

	@Override
	public void loop() {

		int heading = gyroSensor.getHeading();

		telemetry.addData("heading", heading);
		telemetry.addData("status", gyroSensor.status());
		telemetry.addLine(Boolean.toString(rotateToPosition(70)));
		telemetry.update();

	}

	public boolean rotateToPosition(int position) {

		final int buffer = 10;
		int high = clamp(position - buffer);
		int low = clamp(position + buffer);

		int heading = gyroSensor.getHeading();

		if (heading > high || heading < low) {

			motorBackLeft.setPower(0);
			motorBackRight.setPower(0);
			motorFrontLeft.setPower(0);
			motorFrontRight.setPower(0);

			return true;

		}

		else if (heading <= low && heading > clamp(position - 180)) {

			motorBackLeft.setPower(0.25);
			motorBackRight.setPower(0.25);
			motorFrontLeft.setPower(0.25);
			motorFrontRight.setPower(0.25);

		}

		else if (heading >= 10 && heading <= clamp(position + 180)) {

			motorBackLeft.setPower(-0.25);
			motorBackRight.setPower(-0.25);
			motorFrontLeft.setPower(-0.25);
			motorFrontRight.setPower(-0.25);

		}

		return false;

	}

	private static int clamp(int i) {

		if (i > 360) {
			return i - 360;
		}

		if (i < 0) {
			return i + 360;
		}

		return i;

	}

}
