package org.firstinspires.ftc.teamcode.cubeRecognition;

import org.firstinspires.ftc.teamcode.cubeRecognition.Color;

public class Screen {

	private static Color[][] pixels;


	public static Color getColor(int x, int y) {
		return pixels[x][y];
	}

}
