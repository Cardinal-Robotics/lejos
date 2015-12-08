package lejos.ev3.menu.viewer;

import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.Image;

public class Config {

  public static Panel STATUS     = new Panel(0, 0, 178, 12, true, 0);
  public static Panel NODE       = new Panel(0, 12, 178, 26, false, 14);
  public static Icon  ICON       = new Icon(1, 13, 24, 24, false, 0);
  public static Label TITLE      = new Label(25, 13, 128, 24, false, 0, Font.getDefaultFont());

  public static Panel DETAILS    = new Panel(0, 40, 178, 85, false, 0);
  public static Icon  ICONSELECT = new Icon(0, 44, 10, 10, false, 0, Icons.ARROW_RIGHT);
  public static Label DETAIL     = new Label(10, 41, 157, Font.getDefaultFont().height + 1, false, 0, Font.getDefaultFont());
  
  public static Panel EDITOR     = new Panel(5,47,170, 24, false, 15 );
  public static Panel SHADE     = new Panel(8,50,170, 24, true, 0 );
  public static Label EDITORLINE = new Label(8,51,160, Font.getDefaultFont().height + 1, false, 0, Font.getDefaultFont());

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

    void draw(GraphicsLCD canvas) {
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

    void clear(GraphicsLCD canvas) {
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
    Font          font;

    private Label(int x, int y, int width, int height, boolean reverse, int borders, Font font) {
      super(x, y, width, height, reverse, borders);
      this.font = font;
    }

    @Override
    public void draw(GraphicsLCD canvas, int xOffset, int yOffset) {
      super.draw(canvas, xOffset, yOffset);
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
