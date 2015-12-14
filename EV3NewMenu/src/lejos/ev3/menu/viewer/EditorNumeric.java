package lejos.ev3.menu.viewer;

import lejos.ev3.menu.model.DetailNumericValue;
import lejos.ev3.menu.model.DetailStringValue;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;

/**
 * A simple editor for numeric values that uses the up and down keys to change
 * the value
 * 
 * @author Aswin Bouwmeester
 *
 */
public class EditorNumeric implements Editor {

  public EditorNumeric() {
  }

  @Override
  public void edit(DetailNumericValue model) {
    GraphicsLCD canvas = LocalEV3.get().getGraphicsLCD();
    int lowerLimit = model.getMinValue();
    int upperLimit = model.getMaxValue();
    int increment = model.getIncrement();
    String format = model.getFormat();
    String label = model.getLabel();
    int value = model.getValue();

    int old = value;
    Config.SHADE.draw(canvas);
    Config.EDITOR.draw(canvas);
    canvas.setFont(Config.EDITORLINE.font);

    while (true) {
      Config.EDITORLINE.clear(canvas);
      canvas.drawString(String.format(format, label, value), Config.EDITORLINE.x, Config.EDITORLINE.y, 0);

      switch (Button.waitForAnyEvent()) {
      case (Button.ID_UP): {
        if (value + increment <= upperLimit)
          value += increment;
        break;
      }
      case (Button.ID_DOWN): {
        if (value - increment >= lowerLimit)
          value -= increment;
        break;
      }
      case (Button.ID_ENTER): {
        if (value != old) {
          model.setValue(value);
        }
        return;
      }
      case (Button.ID_ESCAPE): {
        return;
      }
      }
    }
  }

  @Override
  public void edit(DetailStringValue model) {
  }

}
