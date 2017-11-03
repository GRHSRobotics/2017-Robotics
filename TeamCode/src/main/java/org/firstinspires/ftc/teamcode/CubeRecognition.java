package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcontroller.internal.CameraActivity;
import org.firstinspires.ftc.robotcontroller.internal.CameraView;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

import java.io.InputStream;

@Autonomous(name = "cubeRecognition", group = "Autonomous")
public class CubeRecognition extends OpMode {

	private Camera mCamera = null;
	private CameraView mCameraView = null;
	private CameraActivity cameraActivity;

	@Override
	public void init() {

		Looper.prepare();

		cameraActivity = new CameraActivity();

		Intent intent = new Intent(hardwareMap.appContext, cameraActivity.getClass());
		hardwareMap.appContext.startActivity(intent);

	}

	@Override
	public void loop() {

		Window window = ((Activity) hardwareMap.appContext).getWindow();

		if (window != null) {

			Bitmap bitmap = getPixels(window);
			for (int x = 0; x < 200; x++) {
				for (int y = 0; y < 200; y++) {
					telemetry.addData(x + " " + y, bitmap.getPixel(x, y));
				}
			}

		}

		telemetry.update();

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

	public Bitmap getPixels(Window window) {

		View view = window.getDecorView().getRootView();
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);

		return bitmap;

	}

}
