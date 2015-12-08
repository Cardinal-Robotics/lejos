package lejos.ev3.menu.viewer;

import lejos.ev3.menu.model.Detail;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;

  public class EditorNumeric implements Editor {
  protected int upperLimit=Integer.MAX_VALUE;
  protected int lowerLimit=Integer.MIN_VALUE;
  protected int increment =1;
  private GraphicsLCD  g              = LocalEV3.get().getGraphicsLCD();
  protected String format = "%d";


  public EditorNumeric() {
  }

  
  public EditorNumeric(int  lowerLimit, int upperLimit, int increment, String format) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
    this.increment = increment;
    this.format = format;
  }

  
  public EditorNumeric setLimits (int min, int max) {
    upperLimit = max;
    lowerLimit = min;
    return this;
  }
  
  public EditorNumeric setIncrement(int increment) {
    this.increment = increment;
    return this;
  }
  
  public EditorNumeric setFormat(String format) {
    this.format = format;
    return this;
  }
  
  
  
  @Override
  public boolean edit(Detail control, GraphicsLCD canvas) {
    int value = control.getNValue();
    String format = control.getFormat();
    String label = control.getLabel();
    int old = value;
    Config.SHADE.draw(canvas);
    Config.EDITOR.draw(canvas);
    g.setFont(Config.EDITORLINE.font);

    while (true) {
      Config.EDITORLINE.clear(canvas);
    g.drawString(String.format(label + format, value), Config.EDITORLINE.x,  Config.EDITORLINE.y , 0);
    
      switch(Button.waitForAnyEvent()) {
        case (Button.ID_UP): {
          if ( value + increment <= upperLimit)
            value+=increment;
          break;
        }
        case (Button.ID_DOWN): {
          if ( value - increment >= lowerLimit)
            value-=increment;
          break;
        }
        case (Button.ID_ENTER): {
          if (value != old) {
            control.setNValue(value);
            return true;
          }
          else return false;
        }
        case (Button.ID_ESCAPE): {
          return false;
        }
      }
    }
  }



}
