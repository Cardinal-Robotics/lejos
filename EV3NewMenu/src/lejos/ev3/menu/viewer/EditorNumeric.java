package lejos.ev3.menu.viewer;

import lejos.ev3.menu.components.TextPanel;
import lejos.ev3.menu.presenter.SettingDetail;
import lejos.hardware.Button;

/**
 * A simple editor for numeric values that uses the up and down keys to change
 * the value
 * 
 * @author Aswin Bouwmeester
 *
 */
public class EditorNumeric implements Editor {
  int increment = 1;
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
    
    TextPanel p = new TextPanel(String.format(format, key, label, value ));

    int old = value;
    while (true) {
      p.clear();
      p.setMessage(String.format(format, key, label, value ));
      p.paint();

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
          v = Integer.toString(value);
          presenter.setValue(v);
        }
        return;
      }
      case (Button.ID_ESCAPE): {
        return;
      }
      }
    }
  }




}
