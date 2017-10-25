package org.firstinspires.ftc.teamcode.cubeRecognition;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class Screen extends SurfaceView implements Runnable, SurfaceHolder.Callback, Camera.PreviewCallback{

	//private Color[][] pixels;
	private int[] pixels;
	private Thread thread;

	//TODO: Update to new camera API
	private Camera camera;
	private Camera.Parameters parameters;
	private Camera.Size cameraSize;
	private SurfaceHolder surfaceHolder;

	public Screen(Context context) {

		super(context);

		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

	}

	public Color getColor(int x, int y) {
		return pixels[x][y];
	}

	private void update() {



	}

	@Override
	public void run() {

		while (true) {
			update();
		}

	}

	public void start() {

		if (thread == null) {
			thread = new Thread(this, "screenUpdatingThread");
			thread.start();
		}

	}

	@Override
	public void onPreviewFrame(byte[] bytes, Camera camera) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {

		camera = Camera.open();

		try {

			camera.setPreviewDisplay(surfaceHolder);
			camera.setPreviewCallback(this);

			parameters = camera.getParameters();
			cameraSize = parameters.getPreviewSize();
			pixels = new int[cameraSize.width * cameraSize.height];

		} catch (IOException e) {
			camera.release();
			camera = null;
			e.printStackTrace();
		}

	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

	}
}
