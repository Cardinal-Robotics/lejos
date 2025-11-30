package robot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class DriveBase {
	// Define fields for the left and right motor with the EV3LargeRegulatedMotor type.
	public EV3LargeRegulatedMotor left;
	public EV3LargeRegulatedMotor right;
	
	public DriveBase() {
		// Create the EV3LargeRegulatedMotors and set them to the variables above.
		// Assumes that the motors are connected to ports B and C.
		left = new EV3LargeRegulatedMotor(MotorPort.B);
		right = new EV3LargeRegulatedMotor(MotorPort.C);
	}
	
	// Tell the motors to go forward and at what speed.
	public void forward(int speed) {
		left.setSpeed(speed);
		right.setSpeed(speed);
		left.forward();
		right.forward();
	}
	
	// Tells the motors to stop moving.
	// The true fixes a bug that temporarily freezes the robot when stopping.
	public void stop() {
		left.stop(true);
		right.stop(true);
	}
}
