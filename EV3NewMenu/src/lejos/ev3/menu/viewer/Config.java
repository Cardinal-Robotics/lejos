package lejos.ev3.menu.viewer;

import lejos.ev3.menu.components.Fonts;
import lejos.ev3.menu.components.Icons;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.Image;

/**
 * Defines the lay out of the Graphic Menu
 * 
 * @author Aswin Bouwmeester
 *
 */
public class Config {
  // TODO: Get rid of this, replace by something better

  public static Panel STATUS     = new Panel(0, 0, 178, 13, true, 0);
  public static Label STATUSTEXT = new Label(5, 1, 168, 12, true, 0, Fonts.Courier12);
  public static Panel NODE       = new Panel(0, 13, 178, 26, false, 14);
  public static Icon  ICON       = new Icon(2, 14, 24, 24, false, 0);
  public static Label TITLE      = new Label(29, 17, 128, 20, false, 0, Fonts.Courier17);

  public static Panel DETAILS    = new Panel(0, 40, 178, 87, false, 0);
  public static Icon  ICONSELECT = new Icon(0, 42, 8, 10, false, 0, Icons.ARROW_RIGHT);
  public static Label DETAIL     = new Label(8, 42, 160, Fonts.Courier13.height + 1, false, 0, Fonts.Courier13);

  public static Panel EDITOR     = new Panel(5, 47, 170, 24, false, 15);
  public static Panel SHADE      = new Panel(8, 50, 170, 24, true, 0);
  public static Label EDITORLINE = new Label(8, 51, 160, Fonts.Courier13.height + 1, false, 0, Fonts.Courier13);
  
  public static Panel VIEWER     = new Panel(0, 14, 178, 115, false, 0);
  public static Label VIEWLINE   = new Label(8, 51, 160, Fonts.Courier13.height + 1, false, 0, Fonts.Courier12);

  public static Panel NOTIFY     = new Panel(5, 47, 170, 24, false, 15);
  public static Panel NOTIFYSHADE= new Panel(8, 50, 170, 24, true, 0);
  public static Label NOTIFYLINE = new Label(8, 51, 160, Fonts.Courier13.height + 1, false, 0, Fonts.Courier13);

  
  private Config() {
  };

  public static class Panel {
    public final int     x;
    public final int     y;
    public final int     width;
    public final int     height;
    public final int     bottom;
    public final int     right;
    public final boolean reverse;
    public final int     borders;

    private Panel(int x, int y, int width, int height, boolean reverse, int borders) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.bottom = y + height - 1;
      this.right = x + width - 1;
      this.reverse = reverse;
      this.borders = borders;
    }

    public void draw(GraphicsLCD canvas) {
      draw(canvas, 0, 0);
    }

    void draw(GraphicsLCD canvas, int xOffset, int yOffset) {
      if (reverse)
        canvas.setColor(GraphicsLCD.BLACK);
      else
        canvas.setColor(GraphicsLCD.WHITE);
      canvas.fillRect(x + xOffset, y + yOffset, width, height);
      if (reverse)
        canvas.setColor(GraphicsLCD.WHITE);
      else
        canvas.setColor(GraphicsLCD.BLACK);
      if ((borders & 1) != 0)
        canvas.drawLine(x + xOffset, y + yOffset, right + xOffset, y + yOffset);
      if ((borders & 2) != 0)
        canvas.drawLine(right + xOffset, y + yOffset, right + xOffset, bottom + yOffset);
      if ((borders & 4) != 0)
        canvas.drawLine(x + xOffset, bottom + yOffset, right + xOffset, bottom + yOffset);
      if ((borders & 8) != 0)
        canvas.drawLine(x + xOffset, y + yOffset, x + xOffset, bottom + yOffset);
      canvas.setColor(GraphicsLCD.BLACK);
    }

    public void clear(GraphicsLCD canvas) {
      clear(canvas, 0, 0);
    }

    public void clear(GraphicsLCD canvas, int xOffset, int yOffset) {
      canvas.setColor(GraphicsLCD.WHITE);
      canvas.fillRect(x + xOffset, y + yOffset, width, height);
      canvas.setColor(GraphicsLCD.BLACK);
    }

  }

  public static class Label extends Panel {
    public String label;
    private int   anchor = 0;
    public Font          font;

    private Label(int x, int y, int width, int height, boolean reverse, int borders, Font font) {
      super(x, y, width, height, reverse, borders);
      this.font = font;
    }

    @Override
    public void draw(GraphicsLCD canvas, int xOffset, int yOffset) {
      super.draw(canvas, xOffset, yOffset);
      canvas.setFont(font);
      if (reverse)
        canvas.setColor(GraphicsLCD.WHITE);
      if (label != null)
        canvas.drawString(label, x + xOffset, y + yOffset, anchor);
      if (reverse)
        canvas.setColor(GraphicsLCD.BLACK);
    }
  }

  public static class Icon extends Panel {
    public Image icon;

    private Icon(int x, int y, int width, int height, boolean reverse, int borders, Image icon) {
      super(x, y, width, height, reverse, borders);
      this.icon = icon;
    }

    private Icon(int x, int y, int width, int height, boolean reverse, int borders) {
      super(x, y, width, height, reverse, borders);
    }

    @Override
    public void draw(GraphicsLCD canvas, int xOffset, int yOffset) {
      super.draw(canvas, xOffset, yOffset);
      if (reverse)
        canvas.setColor(GraphicsLCD.WHITE);
      if (icon != null)
        canvas.drawImage(icon, x + xOffset, y + yOffset, 0);
      if (reverse)
        canvas.setColor(GraphicsLCD.BLACK);
    }
  }

}
