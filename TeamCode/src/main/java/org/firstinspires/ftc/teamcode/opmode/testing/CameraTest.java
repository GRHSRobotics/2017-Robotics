package org.firstinspires.ftc.teamcode.opmode.testing;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Looper;
import android.widget.FrameLayout;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcontroller.internal.CameraActivity;
import org.firstinspires.ftc.robotcontroller.internal.CameraView;

/**
 * Created by AllTheMegahertz on 2/19/2018.
 */

@Autonomous(name = "cameraTest", group = "testing")
public class CameraTest extends OpMode {

	private Camera mCamera = null;

	@Override
	public void init() {

		Looper.prepare();

		Intent intent = new Intent(hardwareMap.appContext, CameraActivity.class);
		hardwareMap.appContext.startActivity(intent);

	}

	@Override
	public void loop() {

	}

}
