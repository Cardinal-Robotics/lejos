package robot;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class SensorData {
	public static float[] getSensorData(SensorMode sensorMode) { return getSensorData((SampleProvider)sensorMode); }
	public static float[] getSensorData(SampleProvider sensorMode) {
		float[] data = new float[sensorMode.sampleSize()];
		sensorMode.fetchSample(data, 0);
		return data;
	}
	
	/**
	 * Intensity level (between 0 and 1) of ambient light.   
	 */
	public static float getColorAmbientData(EV3ColorSensor colorSensor) {
		SampleProvider sensorMode = colorSensor.getAmbientMode();
		return getSensorData(sensorMode)[0];
	}
	
	/**
	 * Three intensity levels (between 0 and 1) of red, green and blue light respectively.   
	 */
	public static float[] getColorRGBData(EV3ColorSensor colorSensor) {
		SampleProvider sensorMode = colorSensor.getRGBMode();
		return getSensorData(sensorMode);
	}
	
	/**
	 * Intensity level (between 0 and 1) of reflected light with red LED on.   
	 */
	public static float getColorRedData(EV3ColorSensor colorSensor) {
		SampleProvider sensorMode = colorSensor.getRedMode();
		return getSensorData(sensorMode)[0];
	}
	
	/**
	 * The sample contains one element containing the ID (0-7) of the detected color.   
	 */
	public static int getColorIdData(EV3ColorSensor colorSensor) {
		SampleProvider sensorMode = colorSensor.getColorIDMode();
		float data = getSensorData(sensorMode)[0];
		return (int)data;
	}
	
	  /**
	   * The sample contains one elements representing the angular velocity (in Degrees / second) of the sensor.   
	   */
	public static float getGyroRateData(EV3GyroSensor gyroSensor) {
		SampleProvider sensorMode = gyroSensor.getRateMode();
		return getSensorData(sensorMode)[0];
	}

	  /**
	   * The sample contains one elements representing the orientation (in Degrees) of the sensor in respect to its start position.   
	   */
	public static float getGyroAngleData(EV3GyroSensor gyroSensor) {
		SampleProvider sensorMode = gyroSensor.getAngleMode();
		return getSensorData(sensorMode)[0];
	}
	
	  /**
	   * The sample contains two elements. The first element contains angular velocity (in degrees / second). The second element contain angle (in degrees).  
	   */
	public static float[] getGyroAngleAndRateData(EV3GyroSensor gyroSensor) {
		SampleProvider sensorMode = gyroSensor.getAngleAndRateMode();
		return getSensorData(sensorMode);
	}
	
	
	/**
	 * The sample contains one element giving the distance to an object in front of the sensor. The distance provided is very roughly equivalent to meters
	 * but needs conversion to give better distance. See product page for details. <br>
	 * The effective range of the sensor in Distance mode  is about 5 to 50 centimeters. Outside this range a zero is returned
	 * for low values and positive infinity for high values.
    */
	public static float getIRDistanceData(EV3IRSensor irSensor) {
		SampleProvider sensorMode = irSensor.getDistanceMode();
		return getSensorData(sensorMode)[0];
	}
	
	/** 
	 * Returns true if the sensor is touching something.
	 */
	public static boolean getTouchSensorData(EV3TouchSensor touchSensor) {
		SampleProvider sensorMode = touchSensor.getTouchMode();
		float data = getSensorData(sensorMode)[0];
		return data == 1;
	}
	
	/** 
	 * Returns true if the sensor detects another ultrasonic sensor.
	 */
	public static boolean getUltrasonicListenData(EV3UltrasonicSensor ultrasonicSensor) {
		SampleProvider sensorMode = ultrasonicSensor.getListenMode();
		float data = getSensorData(sensorMode)[0];
		return data == 1;
	}
	
	/** 
	 * Returns the distance to an object (in meters).
	 */
	public static float getUltrasonicDistanceData(EV3UltrasonicSensor ultrasonicSensor) {
		SampleProvider sensorMode = ultrasonicSensor.getDistanceMode();
		return getSensorData(sensorMode)[0];
	}
}
