package org.firstinspires.ftc.teamcode.cubeRecognition;

import android.hardware.Camera;

public class Screen {

	private Camera camera;
	private CameraP

	private boolean openCamera() {

		boolean opened = false;

		try {
			releaseCameraAndPreview();
			camera = Camera.open();
			opened = (camera != null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return opened;

	}

	private void releaseCameraAndPreview() {

	}

}
