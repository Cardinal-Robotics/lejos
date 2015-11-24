package lejos.ev3.menu.model;


import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.Icons;
import lejos.hardware.lcd.GraphicsLCD;

public class NodeSamples extends NodeFiles {



  public NodeSamples(MenuControl control) {
    super(control, SAMPLES_DIRECTORY, ".jar");
    alignment = GraphicsLCD.LEFT | GraphicsLCD.TOP;
    setIcon(Icons.SAMPLES);
    setLabel("Samples");
  }
}
