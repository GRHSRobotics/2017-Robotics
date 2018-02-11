package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "moveForward", group = "default")
public class MoveForward extends MotorOpMode {

	private double startTime = 0;

	@Override
	public void init() {

		super.init();

		setServosClosed(true);

	}

	@Override
	public void loop() {

		if (startTime == 0) {
			startTime = getRuntime();
		}

		if (getRuntime() - startTime < 1) {
			setPower(1);
		}

		else if (getRuntime() - startTime < 1.5) {
			setServosClosed(false);
			setPower(-0.3);
			return;
		}

		else {
			stop();
			return;
		}

		telemetry.addData("time", getRuntime());
		telemetry.addData("delta T", getRuntime() - startTime);

	}

	@Override
	public void stop() {
		setPower(0);
		super.stop();
	}

}
