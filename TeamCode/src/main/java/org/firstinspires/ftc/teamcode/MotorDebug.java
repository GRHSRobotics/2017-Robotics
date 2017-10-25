package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "motorDebug", group = "debug")
public class MotorDebug extends OpMode {

	private DcMotor motor;

	@Override
	public void init() {
		motor = hardwareMap.dcMotor.get("motor0");
	}

	@Override
	public void loop() {

		motor.setPower(1);
		telemetry.addData("motorPosition", motor.getCurrentPosition());
		telemetry.update();
		
	}
}
