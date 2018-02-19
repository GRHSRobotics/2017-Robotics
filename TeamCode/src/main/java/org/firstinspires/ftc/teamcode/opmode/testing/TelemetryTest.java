package org.firstinspires.ftc.teamcode.opmode.testing;

import android.media.AudioManager;
import android.media.ToneGenerator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "telemetryTest", group = "testing")
public class TelemetryTest extends OpMode {

	private ToneGenerator toneGenerator;
	private int time;

	@Override
	public void init() {

		toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
		toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP,150);

		telemetry.addLine("initialized");

	}

	@Override
	public void loop() {

		int time = (int) getRuntime();

		if (time % 10 == 0 && time > this.time) {
			toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP,150);
			this.time = time;
		}

		telemetry.addData("time", getRuntime());
		telemetry.update();

	}

}
