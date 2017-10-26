package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by AllTheMegahertz on 9/10/2017.
 */

@Autonomous(name="Basic Debug", group="debug")
public class TestOpMode extends OpMode {

	@Override
	public void init() {

	}

	@Override
	public void loop() {

		telemetry.addData("time", getRuntime());
		telemetry.update();

	}
}
