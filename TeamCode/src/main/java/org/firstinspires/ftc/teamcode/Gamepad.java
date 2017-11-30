package org.firstinspires.ftc.teamcode;

public class Gamepad {

	private com.qualcomm.robotcore.hardware.Gamepad gamepad;

	public Gamepad() {
		this.gamepad = new com.qualcomm.robotcore.hardware.Gamepad();
	}

	public Gamepad(com.qualcomm.robotcore.hardware.Gamepad gamepad) {
		this.gamepad = gamepad;
	}

	public Gamepad(Gamepad gamepad) {
		this.gamepad = gamepad.getGamepad();
	}

	public com.qualcomm.robotcore.hardware.Gamepad getGamepad() {
		return gamepad;
	}


}
