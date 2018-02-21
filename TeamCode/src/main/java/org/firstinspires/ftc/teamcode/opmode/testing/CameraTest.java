package org.firstinspires.ftc.teamcode.opmode.testing;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Looper;
import android.support.annotation.ColorRes;
import android.widget.FrameLayout;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcontroller.internal.CameraActivity;
import org.firstinspires.ftc.robotcontroller.internal.CameraView;
import org.firstinspires.ftc.robotcontroller.internal.PixelManager;
import org.firstinspires.ftc.teamcode.R;

import java.io.*;

import static android.graphics.Color.WHITE;

/**
 * Created by AllTheMegahertz on 2/19/2018.
 */

@Autonomous(name = "cameraTest", group = "testing")
public class CameraTest extends OpMode {

	private static final int ORANGE = Color.rgb(239, 151, 27);
	private static final int BLACK = Color.BLACK;

	private Bitmap left = null;
	private boolean[][] matches = new boolean[1920][1080];

	private Camera mCamera = null;
	private Intent intent;

	@Override
	public void init() {

		Looper.prepare();

		intent = new Intent(hardwareMap.appContext, CameraActivity.class);
		hardwareMap.appContext.startActivity(intent);

	}

	@Override
	public void loop() {

		int[][] search = new int[200][150];
		int[] offset = new int[2];

		int[][] pixels = PixelManager.getPixels();
		for (int x = 0; x < pixels.length; x++) {
			for (int y = 0; y < pixels[0].length; y++) {

				if (x > offset[0] && x < offset[0] + search.length) {
					if (y > offset[1] && y < offset[1] + search[0].length) {

						search[x - offset[0]][y - offset[1]] = pixels[x][y];

					}
				}

			}
		}

		//Run while box has less than 50% white pixels
		while (search(search, Color.WHITE) || offset[0] == 1920 - search.length && offset[1] == 1080 - search[0].length) {

			if (search(search, Color.WHITE)) {
				telemetry.addLine("Found at (" + offset[0] + ", " + offset[1] + ")");
				break;
			} else {

				if (search[0].length < 1080) {
					search = new int[search.length + 1][search[0].length + 1];
				} else {
					break;
				}

			}

			if (offset[0]++ > 1920 - search.length) {
				offset[0] = 0;
				offset[1]++;
			}

		}

		telemetry.addLine("Not found");
		telemetry.update();

	}

	@Override
	public void stop() {

		super.stop();

		if (intent != null) {
			hardwareMap.appContext.stopService(intent);
		}

	}

	private static boolean compareColor(int color1, int color2, int tolerance) {

		int r1 = Color.red(color1);
		int g1 = Color.green(color1);
		int b1 = Color.blue(color1);

		int r2 = Color.red(color2);
		int g2 = Color.green(color2);
		int b2 = Color.blue(color2);

		return Math.abs(r1 - r2) < tolerance && Math.abs(g1 - g2) < tolerance && Math.abs(b1 - b2) < tolerance;

	}

	private static void copyStream(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

	private boolean search(int[][] region, int color) {

		int count = 0;

		for (int x = 0; x < region.length; x++) {
			for (int y = 0; y < region[0].length; y++) {

				if (compareColor(region[x][y], Color.WHITE, 20)) {
					count++;
				}

			}
		}

		return (float) count / (float) (region.length * region[0].length) < 0.5;

	}

}
