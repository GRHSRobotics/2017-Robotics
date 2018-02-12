package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.opmode.MotorOpMode;

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
			setPower(0);
			return;
		}

		else if (getRuntime() - startTime < 4) {
			setPower(0.3);
		}

		else if (getRuntime() - startTime < 4.75) {
			setPower(-0.3);
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
