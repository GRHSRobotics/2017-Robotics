package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class BallAutonomous extends VirtualOpMode {

	private TeamColor teamColor;
	private HardwareMap hardwareMap;

	private DcMotor frontLeft;
	private DcMotor frontRight;
	private DcMotor backLeft;
	private DcMotor backRight;
	private Servo colorServo;
	private ColorSensor leftColorSensor;
	private ColorSensor rightColorSensor;

	private double startTime = 0;

	public BallAutonomous(HardwareMap hardwareMap, TeamColor teamColor) {
		this.hardwareMap = hardwareMap;
		this.teamColor = teamColor;
	}

	@Override
	public void init() {

		frontLeft = hardwareMap.dcMotor.get("frontLeft");
		frontRight = hardwareMap.dcMotor.get("frontRight");
		backLeft = hardwareMap.dcMotor.get("backLeft");
		backRight = hardwareMap.dcMotor.get("backRight");

		colorServo = hardwareMap.servo.get("colorServo");
		leftColorSensor = hardwareMap.colorSensor.get("leftColorSensor");
		rightColorSensor = hardwareMap.colorSensor.get("rightColorSensor");

		colorServo.setPosition(0);

		this.teamColor = teamColor;

	}

	@Override
	public void loop(double runtime) {

		boolean color;
		if (teamColor == TeamColor.Red) {
			color = leftColorSensor.red() + rightColorSensor.blue() > leftColorSensor.blue() + rightColorSensor.red();
		} else {
			color = leftColorSensor.blue() + rightColorSensor.red() > leftColorSensor.red() + rightColorSensor.blue();
		}

		if (startTime == 0) {
			startTime = runtime;
		}

		double deltaT = runtime - startTime;

		if (deltaT <= 1) {
			colorServo.setPosition(0.5);
		}

		else if (deltaT <= 1.5) {

			int power = 1;
			if (!color) {
				power = -1;
			}

			frontLeft.setPower(power);
			frontRight.setPower(power);
			backLeft.setPower(power);
			backRight.setPower(power);

		}

		else {
			colorServo.setPosition(0);
			frontLeft.setPower(0);
			frontRight.setPower(0);
			backLeft.setPower(0);
			backRight.setPower(0);
		}

	}

}
