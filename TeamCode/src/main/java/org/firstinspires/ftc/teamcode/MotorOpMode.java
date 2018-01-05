package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;

public class MotorOpMode {

	DcMotor motorFrontLeft;
	DcMotor motorFrontRight;
	DcMotor motorBackLeft;
	DcMotor motorBackRight;

	ArrayList<DcMotor> motors;

	public void init() {
		DcMotor motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
		DcMotor motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
		DcMotor motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
		DcMotor motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
	}
	
	public ArrayList<DcMotor> getMotors(HardwareMap hardwareMap) {
		return motors;
	}

	//TODO: Implement method
	public void turn() {

	}

	public void enableBreaking(boolean enable, ArrayList<DcMotor> motors) {

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
