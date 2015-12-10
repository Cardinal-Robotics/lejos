package lejos.ev3.menu.viewer;

import lejos.ev3.menu.model.Detail;
import lejos.hardware.lcd.GraphicsLCD;


public interface Editor {
  
  public boolean edit(Detail control, GraphicsLCD canvas);
}
