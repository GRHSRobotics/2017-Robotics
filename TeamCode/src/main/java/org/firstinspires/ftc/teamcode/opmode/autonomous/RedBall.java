package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.TeamColor;

@Autonomous(name = "RedBall", group = "default")
public class RedBall extends OpMode {

	private BallAutonomous ballAutonomous;

	@Override
	public void init() {
		ballAutonomous = new BallAutonomous(hardwareMap, telemetry, TeamColor.Red);
		ballAutonomous.init();
	}

	@Override
	public void loop() {
		ballAutonomous.loop(getRuntime());
	}

	@Override
	public void stop() {
		ballAutonomous.stop();
	}

}
