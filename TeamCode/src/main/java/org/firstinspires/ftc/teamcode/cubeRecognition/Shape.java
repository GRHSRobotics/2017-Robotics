package org.firstinspires.ftc.teamcode.cubeRecognition;

public class Shape {

	private int[][] vertices;

	public Shape(int[][] vertices) {

		this.vertices = vertices;

	}

	public int[][] getVertices() {
		return vertices;
	}

	public Color getAverageColor() {

		Color[] colors = new Color[vertices.length];

		for (int i = 0; i < vertices.length; i++) {
			colors[i] = Screen.getColor(vertices[i][0], vertices[i][0]);
		}

		return Color.average(colors);

	}

}
