package org.firstinspires.ftc.robotcontroller.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by AllTheMegahertz on 2/19/2018.
 */

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder mHolder;
	private Camera mCamera;
	private Overlay overlay;

	public CameraView(Context context, Camera camera) {

		super(context);

		overlay = ((CameraActivity) context).getOverlay();

		mCamera = camera;
		mCamera.setDisplayOrientation(90);

		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

	}

	@Override
	public void surfaceCreated(final SurfaceHolder surfaceHolder) {

		try {

			mCamera.setPreviewDisplay(surfaceHolder);
			mCamera.startPreview();

			final ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

			mCamera.setPreviewCallback(new Camera.PreviewCallback() {
				@Override
				public void onPreviewFrame(byte[] data, Camera camera) {
					int frameHeight = camera.getParameters().getPreviewSize().height;
					int frameWidth = camera.getParameters().getPreviewSize().width;
					// number of pixels//transforms NV21 pixel data into RGB pixels
					int rgb[] = new int[frameWidth * frameHeight];
					decodeYUV420SP(rgb, data, frameWidth, frameHeight);

//					overlay.setDrawingCacheEnabled(true);
//					Bitmap cache = overlay.getDrawingCache();

					for (int x = 0; x < frameWidth; x++) {
						for (int y = 0; y < frameHeight; y++) {

							int index = x * y + x; //Finds 1D index of 2D pixel matrix

							PixelManager.setPixel(rgb[index], x, y);


						}
					}

				}
			});

		} catch (IOException e) {
			Log.d("ERROR", "Camera error on surface created " + e.getMessage());
		}

	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

		if (mHolder.getSurface() == null) {
			return;
		}

		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();
		} catch (IOException e) {
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

		mCamera.setPreviewCallback(null);
		mCamera.stopPreview();
		mCamera.release();

	}

	//Byte decoder
	private void decodeYUV420SP(int[] rgb, byte[] yuv420sp, int width, int height) {
		// Pulled directly from:
		// http://ketai.googlecode.com/svn/trunk/ketai/src/edu/uic/ketai/inputService/KetaiCamera.java
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

				if (r < 0)
					r = 0;
				else if (r > 262143)
					r = 262143;
				if (g < 0)
					g = 0;
				else if (g > 262143)
					g = 262143;
				if (b < 0)
					b = 0;
				else if (b > 262143)
					b = 262143;

				rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
			}
		}
	}

}
