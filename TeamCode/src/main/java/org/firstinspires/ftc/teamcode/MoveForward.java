package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "moveForward", group = "default")
public class MoveForward extends OpMode {

	private DcMotor motorFrontLeft;
	private DcMotor motorFrontRight;
	private DcMotor motorBackLeft;
	private DcMotor motorBackRight;
	private double starttime = 0;

	@Override
	public void init() {

		motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
		motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
		motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
		motorBackRight = hardwareMap.dcMotor.get("motorBackRight");

	}

	@Override
	public void loop() {

		if (starttime == 0) {
			starttime = getRuntime();
		}

		if (getRuntime() - starttime >= 1) {
			stop();
			return;
		}

		telemetry.addData("time", getRuntime());
		telemetry.addData("delta T", getRuntime() - starttime);

		motorFrontLeft.setPower(1);
		motorFrontRight.setPower(-1);
		motorBackLeft.setPower(1);
		motorBackRight.setPower(-1);

	}

	@Override
	public void stop() {
		motorBackLeft.setPower(0);
		motorBackRight.setPower(0);
		motorFrontLeft.setPower(0);
		motorFrontRight.setPower(0);
		super.stop();
	}

}
