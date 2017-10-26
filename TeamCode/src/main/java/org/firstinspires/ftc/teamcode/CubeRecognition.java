package org.firstinspires.ftc.teamcode;

import android.graphics.SurfaceTexture;
import android.os.Looper;
import android.view.Surface;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.cubeRecognition.Color;
import org.firstinspires.ftc.teamcode.cubeRecognition.Screen;

@Autonomous(name = "cubeRecognition", group = "Autonomous")
public class CubeRecognition extends OpMode {

	private Screen screen;

	@Override
	public void init() {
		Looper.prepare();
		screen = new Screen(hardwareMap.appContext);
	}

	@Override
	public void loop() {



//		Color color = screen.getColor(250, 250);
//
//		telemetry.addData("250, 250", color.getR() + " " + color.getG() + " " + color.getB());
		telemetry.update();

	}

}
