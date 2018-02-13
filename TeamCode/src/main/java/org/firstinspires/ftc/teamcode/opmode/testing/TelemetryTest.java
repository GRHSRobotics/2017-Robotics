package org.firstinspires.ftc.teamcode.opmode.testing;

import android.media.AudioManager;
import android.media.ToneGenerator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "telemetryTest", group = "testing")
public class TelemetryTest extends OpMode {

	private ToneGenerator toneGenerator;

	@Override
	public void init() {

		toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
		toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP,150);

		telemetry.addLine("initialized");

	}

	@Override
	public void loop() {

		if (getRuntime() % 10 == 0) {
			toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP,150);
		}

		telemetry.addData("time", getRuntime());
		telemetry.update();

	}

}
