package org.firstinspires.ftc.robotcontroller.internal;


/**
 * Created by AllTheMegahertz on 2/20/2018.
 */

public class PixelManager {

	//Screen dimensions
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;

	private static int[][] pixels = new int[WIDTH][HEIGHT];

	public static void setPixel(int pixel, int x, int y) {
		pixels[x][y] = pixel;
	}

	public static int getPixel(int x, int y) {
		return pixels[x][y];
	}

	public static int[][] getPixels() {
		return pixels;
	}

	public static void clear() {
		pixels = new int[WIDTH][HEIGHT];
	}

}
