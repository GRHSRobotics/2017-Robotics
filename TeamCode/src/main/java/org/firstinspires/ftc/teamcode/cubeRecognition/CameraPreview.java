package org.firstinspires.ftc.teamcode.cubeRecognition;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

public class CameraPreview extends ViewGroup implements SurfaceHolder.Callback {

	private SurfaceView mSurfaceView;
	private SurfaceHolder mHolder;
	private Camera mCamera;
	private List<Size> mSupportedPreviewSizes;
	private Size mPreviewSize;

	public CameraPreview(Context context) {

		super(context);

		mSurfaceView = new SurfaceView(context);
		addView(mSurfaceView);

		mHolder = mSurfaceView.getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

	}

	public void setCamera(Camera camera) {

		if (mCamera == camera) {
			return;
		}

		if ((mCamera = camera) != null) {

			List<Size> localSizes = mCamera.getParameters().getSupportedPreviewSizes();
			mSupportedPreviewSizes = localSizes;
			requestLayout();

			try {
				mCamera.setPreviewDisplay(mHolder);
			} catch (IOException e) {
				e.printStackTrace();
			}

			mCamera.startPreview();

		}

	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {

	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int w, int h) {

		Camera.Parameters parameters = mCamera.getParameters();
		parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
		requestLayout();
		mCamera.setParameters(parameters);

		mCamera.startPreview();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		if (mCamera != null) {
			mCamera.stopPreview();
		}
	}

	@Override
	protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

	}

	private void stopPreviewAndFreeCamera() {

		if (mCamera != null) {

			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;

		}

	}

}
