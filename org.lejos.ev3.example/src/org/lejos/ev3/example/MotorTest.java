package robot;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class MotorTest {
	public EV3LargeRegulatedMotor largeMotor;
	public EV3MediumRegulatedMotor mediumMotor;
	
	public MotorTest() {
		// Create the EV3LargeRegulatedMotors and set them to the variables above.
		// Assumes that the motors are connected to ports B and C.
		largeMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		mediumMotor = new EV3MediumRegulatedMotor(MotorPort.D);
		
		while(true) {
			// Set the speed of the motors in degrees/second.
			largeMotor.setSpeed(300);
			mediumMotor.setSpeed(300);
			
			// Tell the large motor to go forward 
			largeMotor.forward();
			
			// Tell the medium motor to go backwards 
			mediumMotor.backward();
			
			// If the current button is anything but 0 (no button), we stop the loop and move to the next part.
            if (Button.readButtons() != 0) break;
			
            // Let the robot take a breather for 20ms.
			Delay.msDelay(20);
		}
	}
}
