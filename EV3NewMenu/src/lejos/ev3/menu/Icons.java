package lejos.ev3.menu;

import lejos.ev3.startup.Utils;
import lejos.hardware.lcd.Image;

public class Icons {
  public static final Image ARROW_LEFT = new Image(7, 10, Utils.stringToBytes8( "\u0030\u0048\u0044\u0042\u0041\u0041\u0042\u0044\u0048\u0030" ));
  public static final Image ARROW_RIGHT = new Image(7, 10, Utils.stringToBytes8( "\u0006\u0009\u0011\u0021\u0041\u0041\u0021\u0011\u0009\u0006" ));
  public static final Image ARROW_UP = new Image(10, 7, Utils.stringToBytes8( "\u0030\u0000\u0048\u0000\u0084\u0000\u0002\u0001\u0001\u0002\u0001\u0002\u00fe\u0001" ));

  private Icons() {
  }

}
