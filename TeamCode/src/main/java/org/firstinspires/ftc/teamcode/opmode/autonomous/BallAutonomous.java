package org.firstinspires.ftc.teamcode.opmode.autonomous;

import android.content.Intent;
import android.graphics.Color;
import android.os.Looper;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcontroller.internal.CameraActivity;
import org.firstinspires.ftc.robotcontroller.internal.PixelManager;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.SignIdentifier;
import org.firstinspires.ftc.teamcode.SignThread;
import org.firstinspires.ftc.teamcode.TeamColor;
import org.firstinspires.ftc.teamcode.opmode.MotorOpMode;
import org.firstinspires.ftc.teamcode.opmode.VirtualOpMode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class BallAutonomous extends MotorOpMode implements VirtualOpMode {

	private int r = 30;

	private TeamColor teamColor;
	private HardwareMap hardwareMap;
	private Telemetry telemetry;

	private Servo colorServo;

	private double startTime = 0;
	private double deltaT = 0;
	private ArrayList<Boolean> tests = new ArrayList<>();
	private boolean locked = false;
	private boolean lock2 = false;

	private SignIdentifier signIdentifier;
	private SignThread signThread;
	private RelicRecoveryVuMark sign;
	private boolean signFound = false;

	private Intent cameraIntent;
	private boolean colorFound = false;

	private int colorMultiplier = 1;

	public BallAutonomous(HardwareMap hardwareMap, Telemetry telemetry, TeamColor teamColor) {
		this.hardwareMap = hardwareMap;
		this.teamColor = teamColor;
		this.telemetry = telemetry;
	}

	@Override
	public void init() {

		super.init(hardwareMap);

		enableBreaking(true);

		colorServo = hardwareMap.servo.get("colorServo");

		colorServo.setPosition(0);

		setServosClosed(true);

		signIdentifier = new SignIdentifier(hardwareMap.appContext);
		signThread = new SignThread(signIdentifier);
		signThread.start();

	}

	public void loop() {

	}

	@Override
	public void loop(double runtime) {

		if (startTime == 0) {
			startTime = runtime;
		}

		deltaT = runtime - startTime;

		if (!signFound) {

			signFound = (sign = signIdentifier.getSign()) != RelicRecoveryVuMark.UNKNOWN;
			telemetry.addLine("Searching for sign...");

			if (sign == RelicRecoveryVuMark.UNKNOWN && deltaT > 3) {
				sign = RelicRecoveryVuMark.CENTER;
				signFound = true;
				startTime = runtime;
			}

			if (signFound) {

				signThread.stop();

				Looper.prepare();

				cameraIntent = new Intent(hardwareMap.appContext, CameraActivity.class);
				hardwareMap.appContext.startActivity(cameraIntent);

			}

			return;

		} else if (!colorFound) {

			int[][] pixels = PixelManager.getPixels();

			int[] dimensions = new int[] {200, 150};
			int[] offset = new int[] {pixels.length - dimensions[0], pixels[0].length - dimensions[1]};
			int[][] search = getSearchArea(dimensions[0], dimensions[1], offset, pixels);

			int[] colors = new int[] {Color.RED, Color.BLUE};
			if (teamColor == TeamColor.Blue) {
				colors = new int[] {Color.BLUE, Color.RED};
			}

			int ballColor = search(search, colors[0], colors[1], 40);

			if (ballColor == 1) {
				colorMultiplier = 0;
				return;
			} else if (ballColor == colors[1]) {
				colorMultiplier = -1;
				colorFound = true;
			}

		}

		telemetry.addData("Sign", sign.toString());

		if (!imu.isGyroCalibrated()) {
			time = runtime;
			telemetry.addLine("Calibrating gyro...");
			return;
		}

		telemetry.addData("team", teamColor);
		telemetry.addData("target", target);
		telemetry.update();

		if (deltaT <= 3 && !locked) {
			colorServo.setPosition(0.6);
		}

		else if (!locked) {

			r *= colorMultiplier;

			locked = true;

		}

		else if (!lock2) {

			signIdentifier.stop();

			if (rotateToPosition(r)) {
				colorServo.setPosition(0);
				lock2 = true;
			}

			startTime = runtime;
		}

		else if (deltaT < 3) {

			if (rotateToPosition(90)) {

				if (sign == RelicRecoveryVuMark.LEFT) {
					setPower(0.7);
				} else if (sign == RelicRecoveryVuMark.CENTER) {
					setPower(0.5);
				} else if (sign == RelicRecoveryVuMark.RIGHT) {
					setPower(0.3);
				}

			}

		}

		else if (deltaT < 6) {

			if (rotateToPosition(180)) {
				setServosClosed(false);
				setPower(0.3);
			}

		}

		else if (deltaT < 6.25){
			setPower(-0.3);
		}

		else {
			setPower(0);
		}

	}

	public void stop() {
		signThread.stop();
		hardwareMap.appContext.stopService(cameraIntent);

		setPower(0);
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

	private int search(int[][] region, int color1, int color2, int tolerance) {

		int count1 = 0;

		//Only tests every fourth pixel to decrease runtime
		for (int x = 0; x < region.length / 4; x++) {
			for (int y = 0; y < region[0].length / 4; y++) {

				if (compareColor(region[x * 4][y * 4], color1, tolerance)) {
					count1++;
				}

			}
		}

		int count2 = 0;

		//Only tests every fourth pixel to decrease runtime
		for (int x = 0; x < region.length / 4; x++) {
			for (int y = 0; y < region[0].length / 4; y++) {

				if (compareColor(region[x * 4][y * 4], color2, tolerance)) {
					count2++;
				}

			}
		}

		//Numbers too close to be confident
		if (count1 > count2 && count1 * 0.75 <= count2) {
			return 1;
		} else if (count1 > count2) {
			return color1;
		} else {
			return color2;
		}

	}

	private int[][] getSearchArea(int width, int height, int[] offset, int[][] data) {

		int[][] search = new int[width][height];

		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[0].length; y++) {
				if (x > offset[0] && x < offset[0] + search.length) {
					if (y > offset[1] && y < offset[1] + search[0].length) {
						search[x - offset[0]][y - offset[1]] = data[x][y];
					}
				}
			}
		}

		return search;

	}

}
