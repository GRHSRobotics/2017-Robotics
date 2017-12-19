package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

public abstract class MotorOpMode extends OpMode {

	protected DcMotor motorFrontLeft;
	protected DcMotor motorFrontRight;
	protected DcMotor motorBackLeft;
	protected DcMotor motorBackRight;

	private ArrayList<DcMotor> motors;

	public void init() {

		motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
		motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
		motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
		motorBackRight = hardwareMap.dcMotor.get("motorBackRight");

		motors.add(motorFrontLeft);
		motors.add(motorFrontRight);
		motors.add(motorBackLeft);
		motors.add(motorBackRight);

	}

	protected void turn(Direction direction) {

	}

	protected void enableBreaking(boolean enable) {

		DcMotor.ZeroPowerBehavior behavior;

		if (enable) {
			behavior = DcMotor.ZeroPowerBehavior.BRAKE;
		} else {
			behavior = DcMotor.ZeroPowerBehavior.FLOAT;
		}

		for (DcMotor motor : motors) {
			motor.setZeroPowerBehavior(behavior);
		}

	}

}
