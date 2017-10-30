package org.firstinspires.ftc.teamcode.cubeRecognition;

import android.content.Context;
import android.hardware.Camera;

public class Screen {

	private Camera mCamera;
	private CameraPreview mPreview;
	private boolean open;
	private Context context;

	public Screen(Context context) {
		this.context = context;
	}

	public boolean isOpen() {
		return open;
	}

	public boolean openCamera() {

		boolean opened = false;

		try {

			mPreview = new CameraPreview(context);

			releaseCameraAndPreview();
			mCamera = Camera.open();
			mPreview.setCamera(mCamera);
			opened = (mCamera != null);

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return open = opened;

	}

	private void releaseCameraAndPreview() {

		mPreview.setCamera(null);

		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}

	}

}
