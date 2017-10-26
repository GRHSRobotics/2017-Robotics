package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.cubeRecognition.Color;
import org.firstinspires.ftc.teamcode.cubeRecognition.Screen;
import org.firstinspires.ftc.teamcode.cubeRecognition.ScreenManager;

@Autonomous(name = "cubeRecognition", group = "Autonomous")
public class CubeRecognition extends OpMode {

	private ScreenManager screenManager;
	private Screen screen;

	@Override
	public void init() {
		screenManager = new ScreenManager();
		screen = screenManager.getScreen();
	}

	@Override
	public void loop() {

		Color color = screen.getColor(250, 250);

		telemetry.addData("250, 250", color.getR() + " " + color.getG() + " " + color.getB());
		telemetry.update();

	}

}
