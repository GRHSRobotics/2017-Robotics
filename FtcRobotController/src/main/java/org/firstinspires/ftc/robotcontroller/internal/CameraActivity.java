package org.firstinspires.ftc.robotcontroller.internal;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import com.qualcomm.ftcrobotcontroller.R;

public class CameraActivity extends Activity {

	private Camera mCamera = null;
	private CameraView mCameraView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_view);

		try {
			mCamera = Camera.open(0);
		}
		catch (Exception e) {
			Log.d("ERROR", "Failed to get camera: " + e.getMessage());
		}

		if (mCamera != null) {
			mCameraView = new CameraView(this, mCamera);
			FrameLayout cameraView = (FrameLayout) findViewById(R.id.camera_view);
			cameraView.addView(mCameraView);
		}

		ImageButton imgClose = (ImageButton) findViewById(R.id.imgClose);
		imgClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				System.exit(0);
			}
		});

	}

}
