package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class BallAutonomous extends VirtualOpMode {

	private TeamColor teamColor;
	private HardwareMap hardwareMap;
	private Telemetry telemetry;

	private DcMotor motorFrontLeft;
	private DcMotor motorFrontRight;
	private DcMotor motorBackLeft;
	private DcMotor motorBackRight;
	private Servo colorServo;
//	private ColorSensor rightColorSensor;
	private ColorSensor leftColorSensor;

	private double startTime = 0;
	private ArrayList<Boolean> tests = new ArrayList<>();
	private boolean locked = false;

	public BallAutonomous(HardwareMap hardwareMap, Telemetry telemetry, TeamColor teamColor) {
		this.hardwareMap = hardwareMap;
		this.teamColor = teamColor;
		this.telemetry = telemetry;
	}

	@Override
	public void init() {

		motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
		motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
		motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
		motorBackRight = hardwareMap.dcMotor.get("motorBackRight");

		motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		colorServo = hardwareMap.servo.get("colorServo");
//		rightColorSensor = hardwareMap.colorSensor.get("rightColorSensor");
		leftColorSensor = hardwareMap.colorSensor.get("leftColorSensor");
//		rightColorSensor.enableLed(true);
		leftColorSensor.enableLed(true);

		colorServo.setPosition(0);

	}

	@Override
	public void loop(double runtime) {

		telemetry.addData("team", teamColor);
		telemetry.addData("left", leftColorSensor.red() + " " + leftColorSensor.green() + " " + leftColorSensor.blue() + " " + leftColorSensor.toString());
		telemetry.update();

		if (colorServo.getPosition() != 0) {
			if (teamColor == TeamColor.Red) {
				tests.add(leftColorSensor.blue() < leftColorSensor.red());
			} else {
				tests.add(leftColorSensor.red() < leftColorSensor.blue());
			}
		}

		if (startTime == 0) {
			startTime = runtime;
		}

		double deltaT = runtime - startTime;

		if (deltaT <= 1) {
			colorServo.setPosition(0.65);
		}

		else if (deltaT <= 1.5 && !locked) {

			int i = 0;
			for (boolean b : tests) {
				if (b) {
					i++;
				}
			}

			float power = 0.3f;
			if ((float) i / (float) tests.size() < 0.5) {
				power *= -1;
			}

			motorFrontLeft.setPower(power);
			motorFrontRight.setPower(power);
			motorBackLeft.setPower(power);
			motorBackRight.setPower(power);

			locked = true;

		}

		else if (deltaT > 1.5) {
			colorServo.setPosition(0);
			motorFrontLeft.setPower(0);
			motorFrontRight.setPower(0);
			motorBackLeft.setPower(0);
			motorBackRight.setPower(0);
		}

	}

}
