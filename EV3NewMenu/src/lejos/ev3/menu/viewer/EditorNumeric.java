package lejos.ev3.menu.viewer;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;

  public class EditorNumeric implements Editor {
  protected int upperLimit=Integer.MAX_VALUE;
  protected int lowerLimit=Integer.MIN_VALUE;
  protected int increment =1;
  private GraphicsLCD  g              = LocalEV3.get().getGraphicsLCD();
  protected String format = "%2.0f";
  protected String key;


  public EditorNumeric() {
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
  public int edit(int value, int x, int y) {
    int old = value;
    int top = y - Config.DETAILS.font.getHeight()-2;
    int width = Math.max(Config.DETAILS.font.stringWidth(String.format(format, upperLimit)),Config.DETAILS.font.stringWidth(String.format(format, lowerLimit))) +4;
    int height = Config.DETAILS.font.getHeight() * 3 + 4;

    while (true) {
    g.setColor(GraphicsLCD.WHITE);
    g.fillRect(x-2, top, width, height);
    g.setColor(GraphicsLCD.BLACK);
    g.drawRect(x-2, top, width, height);
    
    g.drawRegion(Icons.ARROW_UP, 0, 0,  Icons.ARROW_UP.getWidth(), Icons.ARROW_UP.getHeight(), 0,  x + width /2 , y -1,GraphicsLCD.HCENTER | GraphicsLCD.BOTTOM);
    g.drawRegion(Icons.ARROW_UP, 0, 0,  Icons.ARROW_UP.getWidth(), Icons.ARROW_UP.getHeight(), 0,  x + width /2, y + Config.DETAILS.font.getHeight() + 1, GraphicsLCD.HCENTER | GraphicsLCD.TOP);
    g.setFont(Config.DETAILS.font);
    g.drawString(String.format(format, value), x, y , 0);
    g.setFont(Font.getDefaultFont());
    
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
          return value;
        }
        case (Button.ID_ESCAPE): {
          return old;
        }
      }
    }
  }

  @Override
  public String edit(String value, int x, int y) {
    throw new RuntimeException("A numeric editor does not edit strings");
  }
  

}
