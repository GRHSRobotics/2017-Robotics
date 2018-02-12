package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.TeamColor;

@Autonomous(name = "BlueBall", group = "default")
public class BlueBall extends OpMode {

	private BallAutonomous ballAutonomous;

	@Override
	public void init() {
		ballAutonomous = new BallAutonomous(hardwareMap, telemetry, TeamColor.Blue);
		ballAutonomous.init();
	}

	@Override
	public void loop() {
		ballAutonomous.loop(getRuntime());
	}

}
