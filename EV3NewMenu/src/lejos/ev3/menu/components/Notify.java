package lejos.ev3.menu.components;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.Image;

public class Notify {
  
  protected static Font font = Fonts.Courier13;
  protected static Image icon;
  protected static String[] message;
  protected static GraphicsLCD      canvas          = LocalEV3.get().getGraphicsLCD();
  protected static int offset = 4;
  
  public static void main (String[] args) {
    setMessage(new String[]{"Sid","Bouwmeester"});
    setIcon(Icons.LEJOS);
    
    run();
    Button.waitForAnyPress();
  }

  public static void setIcon(Image i) {
    icon = i;
  }
  
  public static void setMessage(String[] m) {
    message = m;
  }
  
  public static void setMessage(String m) {
    message = new String[]{m};
  }
  
  public static void run(){
    int windowH = getWindowHeight();
    int windowW = getWindowWidth();
    int windowX = (178 - windowW) / 2;
    int windowY = (128 - windowH) /2;
    int textX = windowX + 4  + ((icon == null) ? 0 : icon.getWidth()+2);
    int textY = (128 - getTextHeight()) /2 + 4;
    int iconY = (128 - ((icon == null) ? 0 : icon.getHeight()))/2 + 1;
    maskBackground();
    drawShadow(windowX, windowY, windowW, windowH);
    drawRegion(windowX, windowY, windowW, windowH);
    drawIcon(windowX + 2, iconY);
    drawMessage(textX, textY);
  };
  
  /**
   * Draws the message
   */
  protected static void drawMessage(int x, int y) {
     canvas.setFont(font);
     canvas.setColor(GraphicsLCD.BLACK);
     for (String line : message) {
       canvas.drawString(line,x , y, 0);
        y += font.getHeight()+1;
     }
  }

  /** Draws the icon
   * 
   */
  protected static void drawIcon(int x, int y) {
    if (icon != null)
      canvas.drawImage(icon, x  ,  y  , 0);
    
  }

  /** Draws the region specified by the parameters
   * @param x
   * @param y
   * @param width
   * @param height
   */
  protected static void drawRegion(int x, int y, int width, int height) {
    canvas.setColor(GraphicsLCD.WHITE);
    canvas.fillRect(x , y , width, height);
    canvas.setColor(GraphicsLCD.BLACK);
    canvas.drawRect(x, y, width, height);
  }

  /** Draws a shadow from the window specified by the parameters
   * @param x
   * @param y
   * @param width
   * @param height
   */
  protected static void drawShadow(int x, int y, int width, int height) {
    canvas.setColor(GraphicsLCD.BLACK);
    canvas.fillRect(x + offset , y + offset , width, height);
  }

  /**
   * Masks the area outside the window. to be overriden
   */
  protected static void maskBackground() {
  }

  protected static int getWindowHeight() {
    return Math.max(getTextHeight() , (icon == null) ? 0 : icon.getHeight()+6);
  }

  protected static int getTextHeight() {
    return (message.length ) * (font.height ) + 8 ;
  }

  
  protected static int getWindowWidth() {
     return (getTextWidth() + ((icon == null) ? 0 : icon.getWidth()+2));
  }

  
  protected static int getTextWidth() {
    int width =0;
    for (String line : message) width =Math.max(width, font.stringWidth(line) + 8 );
    return width;
  }


}
