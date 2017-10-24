package org.firstinspires.ftc.teamcode;

public class Color {

	private int r;
	private int g;
	private int	b;

	public Color(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public static Color average(Color[] colors) {

		int r = 0;
		int g = 0;
		int b = 0;

		for (Color color : colors) {
			r += color.getR();
			g += color.getG();
			b += color.getB();
		}

		r /= colors.length;
		g /= colors.length;
		b /= colors.length;

		return new Color(r, g, b);

	}
}
