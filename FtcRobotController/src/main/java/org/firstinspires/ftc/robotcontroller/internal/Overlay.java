package org.firstinspires.ftc.robotcontroller.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;
import com.qualcomm.ftcrobotcontroller.R;

/**
 * Created by AllTheMegahertz on 2/19/2018.
 */

public class Overlay extends View {

	public Bitmap bitmap = null;

	public Overlay(Context context) {
		super(context);
	}

//	@Override
//	protected void onDraw(Canvas canvas) {
//
//		if (bitmap != null) {
//			canvas.drawBitmap(bitmap, 0, 0, null);
//		}
//
//	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public void setPixel(int x, int y, int color) {
		if (bitmap != null) {
			bitmap.setPixel(x, y, color);
		}
	}

}
