package lejos.ev3.menu.viewer;

import lejos.hardware.lcd.CustomFont;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.Image;

public class Config {
  
  public static Panel STATUS = new Panel(0,0, 178, 12, CustomFont.getMediumFont(),GraphicsLCD.LEFT | GraphicsLCD.VCENTER);

  public static Icon ICONLEFT = new Icon(0,31,Icons.ARROW_LEFT, GraphicsLCD.LEFT | GraphicsLCD.VCENTER);
  public static Icon ICONRIGHT = new Icon(10,31,Icons.ARROW_RIGHT, GraphicsLCD.LEFT | GraphicsLCD.VCENTER);
  public static Icon ICONNODE = new Icon(20,31,Icons.EV3, GraphicsLCD.LEFT | GraphicsLCD.VCENTER);
  public static Panel TITLE = new Panel(54,24, 126, 32, Font.getDefaultFont(), GraphicsLCD.LEFT | GraphicsLCD.TOP);

  public static Icon ICONUP = new Icon(0,50,Icons.ARROW_UP, GraphicsLCD.LEFT | GraphicsLCD.TOP);
  public static Icon ICONDOWN = new Icon(0,120,Icons.ARROW_LEFT, GraphicsLCD.LEFT | GraphicsLCD.BOTTOM);
  public static Icon ICONSELECT = new Icon(10,0,Icons.ARROW_RIGHT, GraphicsLCD.LEFT | GraphicsLCD.TOP);
  public static Panel DETAILS = new Panel(20,50, 158, 12, CustomFont.getMediumFont(),GraphicsLCD.LEFT | GraphicsLCD.TOP);
  
  private Config() {};
  
  
  
  public static class Icon {
    public final int x;
    public final int y;
    public final int width;
    public final int height;
    public final Image icon;
    public final int alignment;
    
    private Icon(int x, int y, Image icon, int alignment) {
      this.x=x;
      this.y = y;
      this.icon = icon;
      this.alignment = alignment;
      this.width = icon.getWidth();
      this.height = icon.getHeight();
    }
  }

  public static class Panel {
    public final int x;
    public final int y;
    public final int width;
    public final int height;
    public final Font font;
    public final int alignment;
    
    private Panel(int x, int y, int width , int height, Font font, int alignment) {
      this.x=x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.font = font;
      this.alignment = alignment;
    }
  }


}
