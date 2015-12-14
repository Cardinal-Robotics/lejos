package lejos.ev3.menu.model;

import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.viewer.Editor;
import lejos.ev3.menu.viewer.Menu;

/**
 * Displays a Numeric property or setting of the leJOS VM and optionally allows
 * the user to modify the value.
 * 
 * @author Aswin Bouwmeester
 *
 */
public class DetailNumericValue extends DetailAbstractValue {
  private int value;
  private int minValue  = Integer.MIN_VALUE;
  private int maxValue  = Integer.MAX_VALUE;
  private int increment = 1;

  /**
   * @param control
   *          The control that provides the value of the property or setting
   * @param label
   *          The text to identify the property or setting for the user
   * @param key
   *          The key to identify the property or setting for the control
   * @param format
   *          The format to display the label value pair on the screen
   */
  public DetailNumericValue(Control control, String label, String key, String format) {
    super(control, label, key, format);
  }

  /**
   * @param control
   *          The control that provides the value of the property or setting
   * @param label
   *          The text to identify the property or setting for the user
   * @param key
   *          The key to identify the property or setting for the control
   * @param format
   *          The format to display the label value pair on the screen
   * @param editor
   *          The class to be used to modify the value of the property or
   *          setting
   */
  public DetailNumericValue(Control control, String label, String key, String format, Class<? extends Editor> editor) {
    super(control, label, key, format, editor);
  }

  /**
   * @param control
   *          The control that provides the value of the property or setting
   * @param label
   *          The text to identify the property or setting for the user
   * @param key
   *          The key to identify the property or setting for the control
   * @param format
   *          The format to display the label value pair on the screen
   * @param editor
   *          The class to be used to modify the value of the property or
   *          setting
   * @param minValue
   *          The lowest valid value for this property or setting
   * @param maxValue
   *          The highest valid value for this property or setting
   * @param increment
   *          The step size to be used by the editor to raise or lower a value
   */
  public DetailNumericValue(Control control, String label, String key, String format, Class<? extends Editor> editor, int minValue,
      int maxValue, int increment) {
    super(control, label, key, format, editor);
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.increment = increment;

  }

  @Override
  protected void initialize() {
    value = control.getNumericProperty(key);
    this.isInitialized = true;
  }

  @Override
  public String toString() {
    super.toString();
    return String.format(format, label, value);
  }

  public void setValue(int value) {
    this.value = value;
    control.setNumericProperty(key, value);
  }

  public int getValue() {
    return value;
  }

  public int getMinValue() {
    return minValue;
  }

  public int getMaxValue() {
    return maxValue;
  }

  public int getIncrement() {
    return increment;
  }

  /**
   * Starts the editor and lets the user modify the value
   * 
   */
  public void select(Menu menu) {
    try {
      Editor edit = (Editor) this.editor.newInstance();
      edit.edit(this);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
