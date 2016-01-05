package lejos.ev3.menu.viewer;

import lejos.ev3.menu.components.TextPanel;
import lejos.ev3.menu.presenter.SettingDetail;
import lejos.hardware.Button;
import lejos.utility.Delay;

/**
 * A simple editor for numeric values that uses the up and down keys to change
 * the value
 * 
 * @author Aswin Bouwmeester
 *
 */
public class EditorNumeric implements Editor {
  int increment  = 1;
  int upperLimit = Integer.MAX_VALUE;
  int lowerLimit = Integer.MIN_VALUE;

  public EditorNumeric() {
  }

  @Override
  public void edit(SettingDetail presenter) {
    String key = presenter.getKey();
    String format = presenter.getFormat();
    String label = presenter.getLabel();
    String v = presenter.getValue();
    int value = Integer.parseInt(v);

    TextPanel p = new TextPanel(String.format(format, key, label, value));
    p.setBorders(15);
    p.setShadow(true);
    p.setMargin(5);

    int old = value;
    int delay = 500;
    p.setMessage(String.format(format, key, label, value));
    p.paint();

    
    p.setMessage(String.format(format, key, label, value));
    p.paint();
    
    while (true) {

      switch (Button.getButtons()) {
      case (0): {
        delay = 500;
        break;
      }
      case (Button.ID_UP): {
        if (value + increment <= upperLimit)
          value += increment;
        p.setMessage(String.format(format, key, label, value));
        p.paint();
        Delay.msDelay(delay);
        delay = 200;
        break;
      }
      case (Button.ID_DOWN): {
        if (value - increment >= lowerLimit)
          value -= increment;
        p.setMessage(String.format(format, key, label, value));
        p.paint();
        Delay.msDelay(delay);
        delay = 200;
        break;
      }
      case (Button.ID_ENTER): {
        if (value != old) {
          v = Integer.toString(value);
          presenter.setValue(v);
          while (Button.getButtons() != 0)
            Delay.msDelay(20);
        }
        return;
      }
      case (Button.ID_ESCAPE): {
        while (Button.getButtons() != 0)
          Delay.msDelay(20);
        return;
      }
      }
    }
  }

}
