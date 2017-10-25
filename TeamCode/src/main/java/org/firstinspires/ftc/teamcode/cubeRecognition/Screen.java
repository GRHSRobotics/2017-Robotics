package org.firstinspires.ftc.teamcode.cubeRecognition;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class Screen extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback{

	//private Color[][] pixels;
	private int[] pixels;

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

		for (int i )

	}

	@Override
	public void onPreviewFrame(byte[] bytes, Camera camera) {
		decode(pixels, bytes, cameraSize.width,  cameraSize.height);
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
	public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int w, int h) {
		parameters.setPreviewSize(w, h);
		camera.setParameters(parameters);
		camera.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		camera.stopPreview();
		camera.release();
		camera = null;
	}

	//TODO: Replace with own method
	//From the Ketai project
	private void decode(int[] rgb, byte[] yuv420sp, int width, int height) {

		final int frameSize = width * height;

		for (int j = 0, yp = 0; j < height; j++) {       int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
			for (int i = 0; i < width; i++, yp++) {
				int y = (0xff & ((int) yuv420sp[yp])) - 16;
				if (y < 0)
					y = 0;
				if ((i & 1) == 0) {
					v = (0xff & yuv420sp[uvp++]) - 128;
					u = (0xff & yuv420sp[uvp++]) - 128;
				}

				int y1192 = 1192 * y;
				int r = (y1192 + 1634 * v);
				int g = (y1192 - 833 * v - 400 * u);
				int b = (y1192 + 2066 * u);

				if (r < 0)                  r = 0;               else if (r > 262143)
					r = 262143;
				if (g < 0)                  g = 0;               else if (g > 262143)
					g = 262143;
				if (b < 0)                  b = 0;               else if (b > 262143)
					b = 262143;

				rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
			}
		}
	}

}
