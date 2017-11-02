package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcontroller.internal.CameraActivity;
import org.firstinspires.ftc.robotcontroller.internal.CameraView;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

@Autonomous(name = "cubeRecognition", group = "Autonomous")
public class CubeRecognition extends OpMode {

	private Camera mCamera = null;
	private CameraView mCameraView = null;

	@Override
	public void init() {

		Intent intent = new Intent(hardwareMap.appContext, CameraActivity.class);
		hardwareMap.appContext.startActivity(intent);

	}

	@Override
	public void loop() {

//		Color color = screen.getColor(250, 250);
//
//		telemetry.addData("250, 250", color.getR() + " " + color.getG() + " " + color.getB());
//		telemetry.update();

	}

	@Override
	public void stop() {

		System.exit(0);
		super.stop();

	}

}
