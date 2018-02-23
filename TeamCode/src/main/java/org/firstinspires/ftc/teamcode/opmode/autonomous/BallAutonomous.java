package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.SignIdentifier;
import org.firstinspires.ftc.teamcode.SignThread;
import org.firstinspires.ftc.teamcode.TeamColor;
import org.firstinspires.ftc.teamcode.opmode.MotorOpMode;
import org.firstinspires.ftc.teamcode.opmode.VirtualOpMode;

import java.util.ArrayList;

public class BallAutonomous extends MotorOpMode implements VirtualOpMode {

	private int r = 30;

	private TeamColor teamColor;
	private HardwareMap hardwareMap;
	private Telemetry telemetry;

	private Servo colorServo;
	private ColorSensor rightColorSensor;
	private ColorSensor leftColorSensor;

	private double startTime = 0;
	private double deltaT = 0;
	private ArrayList<Boolean> tests = new ArrayList<>();
	private boolean locked = false;
	private boolean lock2 = false;

	private SignIdentifier signIdentifier;
	private SignThread signThread;
	private RelicRecoveryVuMark sign;
	private boolean signFound = false;

	public BallAutonomous(HardwareMap hardwareMap, Telemetry telemetry, TeamColor teamColor) {
		this.hardwareMap = hardwareMap;
		this.teamColor = teamColor;
		this.telemetry = telemetry;
	}

	@Override
	public void init() {

		super.init(hardwareMap);

		enableBreaking(true);

		colorServo = hardwareMap.servo.get("colorServo");
		rightColorSensor = hardwareMap.colorSensor.get("rightColorSensor");
		leftColorSensor = hardwareMap.colorSensor.get("leftColorSensor");
		rightColorSensor.setI2cAddress(I2cAddr.create8bit(0x3c));
		leftColorSensor.setI2cAddress(I2cAddr.create8bit(0x3c));

		rightColorSensor.enableLed(true);
		leftColorSensor.enableLed(true);

		colorServo.setPosition(0);

		setServosClosed(true);

		signIdentifier = new SignIdentifier(hardwareMap.appContext);
		signThread = new SignThread(signIdentifier);
		signThread.start();

	}

	public void loop() {

	}

	@Override
	public void loop(double runtime) {

		deltaT = runtime - startTime;

		if (!signFound) {

			signFound = (sign = signIdentifier.getSign()) != RelicRecoveryVuMark.UNKNOWN;
			telemetry.addLine("Searching for sign...");
			
			if (deltaT > 3) {
				sign = RelicRecoveryVuMark.CENTER;
				signFound = true;
				startTime = getRuntime();
			}

			return;

		}

		telemetry.addData("Sign", sign.toString());

		if (gyroSensor.isCalibrating()) {
			time = System.currentTimeMillis() / 1000;
			telemetry.addLine("Calibrating gyro...");
			return;
		}

		if (startTime == 0) {
			startTime = runtime;
		}

		telemetry.addData("team", teamColor);
		telemetry.addData("left", leftColorSensor.red() + " " + leftColorSensor.green() + " " + leftColorSensor.blue() + " " + leftColorSensor.toString());
		telemetry.addData("right", rightColorSensor.red() + " " + rightColorSensor.green() + " " + rightColorSensor.blue() + " " + rightColorSensor.toString());
		telemetry.addData("target", target);
		telemetry.addData("test", leftColorSensor.blue() + rightColorSensor.red() < leftColorSensor.red() + rightColorSensor.blue());
		telemetry.update();

		if (colorServo.getPosition() != 0 && !(leftColorSensor.blue() == leftColorSensor.red() && rightColorSensor.blue() == rightColorSensor.red()) && deltaT > 2) {

			if (teamColor == TeamColor.Red) {
				tests.add(leftColorSensor.blue() + rightColorSensor.red() < leftColorSensor.red() + rightColorSensor.blue());
			} else {
				tests.add(leftColorSensor.red() + rightColorSensor.blue() < leftColorSensor.blue() + rightColorSensor.red());
			}

		}

		if (deltaT <= 3 && !locked) {
			colorServo.setPosition(0.6);
		}

		else if (!locked) {

			if (tests.size() == 0) {
				r = 0;
				colorServo.setPosition(0);
				return;
			}

			int i = 0;
			for (boolean b : tests) {
				if (b) {
					i++;
				}
			}

			if ((float) i / (float) tests.size() < 0.5) {
				r *= -1;
			}

			locked = true;

		}

		else if (!lock2) {

			signIdentifier.stop();

			if (rotateToPosition(r)) {
				colorServo.setPosition(0);
				lock2 = true;
			}

			startTime = runtime;
		}

		else if (deltaT < 3) {

			if (rotateToPosition(90)) {

				if (sign == RelicRecoveryVuMark.LEFT) {
					setPower(0.7);
				} else if (sign == RelicRecoveryVuMark.CENTER) {
					setPower(0.5);
				} else if (sign == RelicRecoveryVuMark.RIGHT) {
					setPower(0.3);
				}

			}

		}

		else if (deltaT < 6) {

			if (rotateToPosition(180)) {
				setServosClosed(false);
				setPower(0.3);
			}

		}

		else if (deltaT < 6.25){
			setPower(-0.3);
		}

		else {
			setPower(0);
		}

	}

	public void stop() {
		signThread.stop();
		setPower(0);
	}

}
