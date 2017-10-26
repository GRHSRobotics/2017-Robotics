package org.firstinspires.ftc.teamcode.cubeRecognition;

import android.content.Context;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

public class Shape {

	private int[][] vertices;
	private Screen screen;

	public Shape(int[][] vertices, Screen screen) {

		this.vertices = vertices;
		this.screen = screen;

	}

	public int[][] getVertices() {
		return vertices;
	}

	public Color getAverageColor() {

		Color[] colors = new Color[vertices.length];

		for (int i = 0; i < vertices.length; i++) {
			colors[i] = screen.getColor(vertices[i][0], vertices[i][0]);
		}

		return Color.average(colors);

	}

}
