package robot;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;

public class BumperDemo {
	private static DriveBase driveBase = new DriveBase();
	private static EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
	
	public BumperDemo() {
		// Loop over our code.
		while(true) {
			// Uses the code inside the getTouch function to see if we are touching something.
			boolean isTouching = SensorData.getTouchSensorData(touchSensor);
			
			// If the current button is anything but 0 (no button), we stop the loop and move to the next part.
            if (Button.readButtons() != 0) break;
            // If the touch sensor detects something, we stop the loop and move to the next part.
            else if(isTouching) break;
            
            // Drive at 300 degrees/second.
            driveBase.forward(300);
			
            // Let the robot take a breather for 20ms.
			Delay.msDelay(20);
		}
		
		
		
		// If we do not stop it, it will still try to go at the set speed.
		driveBase.stop();
		
		// Once the bot hits an obstacle we do an (almost) 90 degree turn to the right.
		driveBase.right.rotate(-400);
		
		
		
		
		// Keep the robot driving until it rams into another wall.
		while(true) {
			boolean isTouching = SensorData.getTouchSensorData(touchSensor);
			
            if (Button.readButtons() != 0) break;
            else if(isTouching) break;
            
            driveBase.forward(300);
            Delay.msDelay(20);
		}
	}
}
