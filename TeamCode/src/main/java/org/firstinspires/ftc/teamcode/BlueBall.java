package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "BlueBall", group = "default")
public class BlueBall extends OpMode {

	private BallAutonomous ballAutonomous;

	@Override
	public void init() {
		ballAutonomous = new BallAutonomous(hardwareMap, TeamColor.Blue);
		ballAutonomous.init();
	}

	@Override
	public void loop() {
		ballAutonomous.loop(getRuntime());
	}

}
