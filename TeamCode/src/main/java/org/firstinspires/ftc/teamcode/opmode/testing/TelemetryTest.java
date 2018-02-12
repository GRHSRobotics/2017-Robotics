package org.firstinspires.ftc.teamcode.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "telemetryTest", group = "testing")
public class TelemetryTest extends OpMode {

	@Override
	public void init() {
		telemetry.addLine("initialized");
	}

	@Override
	public void loop() {
		telemetry.addData("time", getRuntime());
		telemetry.update();
	}

}
