package org.firstinspires.ftc.robotcontroller.internal;

import java.util.ArrayList;

/**
 * Created by AllTheMegahertz on 2/20/2018.
 */

public class PixelManager {

	private static ArrayList<Integer> pixels = new ArrayList<>();

	public static int nextPixel() {
		return pixels.remove(0);
	}

	public static int getAmount() {
		return pixels.size();
	}

	public static void addPixel(int pixel) {
		pixels.add(pixel);
	}

}
