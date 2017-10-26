package org.firstinspires.ftc.teamcode.cubeRecognition;

import android.app.Activity;

public class ScreenManager extends Activity {

	private Screen screen;

	public ScreenManager() {

		this.screen = new Screen(getApplicationContext());

	}

	public Screen getScreen() {
		return screen;
	}

}
