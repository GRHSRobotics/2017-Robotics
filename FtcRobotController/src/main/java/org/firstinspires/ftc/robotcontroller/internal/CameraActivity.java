package org.firstinspires.ftc.robotcontroller.internal;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.qualcomm.ftcrobotcontroller.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by AllTheMegahertz on 2/19/2018.
 */

public class CameraActivity extends Activity {

	private Camera mCamera = null;
	private CameraView mCameraView = null;
	private Overlay overlay = null;

	private ToneGenerator toneGenerator;

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

		mCameraView.setDrawingCacheEnabled(true);

		overlay = new Overlay(this);
		addContentView(overlay, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

//		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//		scheduler.scheduleAtFixedRate (new Runnable() {
//
//			int tone = ToneGenerator.TONE_CDMA_PIP;
//
//			public void run() {
//
//				try {
//
//					Bitmap bitmap = mCameraView.getDrawingCache();
//
//					for (int i = 0; i < bitmap.getWidth(); i++) {
//						for (int j = 0; j < bitmap.getHeight(); j++) {
//
//							if (bitmap.getPixel(i, j) == Color.BLACK) {
//								toneGenerator.startTone(tone, 50);
//								bitmap.setPixel(i, j, Color.GREEN);
//							}
//
//						}
//					}
//
//				} catch (NullPointerException e) {
//					tone = ToneGenerator.TONE_DTMF_0;
//					e.printStackTrace();
//				}
//
//				toneGenerator.startTone(tone, 250);
//
//			}
//
//		}, 0, 500, TimeUnit.MILLISECONDS);

	}

	public Overlay getOverlay() {
		return overlay;
	}
}
