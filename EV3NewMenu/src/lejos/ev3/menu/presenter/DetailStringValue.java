package lejos.ev3.menu.presenter;

import lejos.ev3.menu.components.Editor;
import lejos.ev3.menu.viewer.Menu;

/**
 * Displays a String property or setting of the leJOS VM and optionally allows
 * the user to modify the value.
 * 
 * @author Aswin Bouwmeester
 *
 */
public class DetailStringValue extends DetailAbstractValue {
  protected String value;
  private int    maxLength = 255;
  private char[] validChars;

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
  public DetailStringValue( String label, String key, String format) {
    super( label, key, format);
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
  public DetailStringValue( String label, String key, String format, Class<Editor> editor) {
    this( label, key, format);
    this.editor = editor;
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
   * @param maxLength
   *          The maximum valid length of the string
   */
  public DetailStringValue( String label, String key, String format, Class<Editor> editor, int maxLength) {
    this( label, key, format, editor);
    this.maxLength = maxLength;
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
   * @param maxLength
   *          The maximum valid length of the string
   * @param validChars
   *          An array of valid characters to be used in the property or setting
   */
  public DetailStringValue( String label, String key, String format, Class<Editor> editor, int maxLength, char[] validChars) {
    this( label, key, format, editor, maxLength);
    this.validChars = validChars;
  }

  @Override
  protected void initialize() {
    value = control.getProperty(key);
    this.isInitialized = true;
  }

  @Override
  public String toString() {
    return String.format(format, label,  !getValue().isEmpty() ? getValue() : "not set" );
  }

  public void setValue(String value) {
    this.value = value;
    control.setProperty(key, value);
  }

  public String getValue() {
    if (!isInitialized) initialize();
    if (value == null) return "";
    return value;
  }

  public int getMaxLength() {
    return maxLength;
  }

  public char[] getValidChars() {
    return validChars;
  }

  /**
   * Starts the editor and lets the user modify the value
   * 
   */
  public void select(Menu menu) {
    try {
      Editor edit = Editor.class.newInstance();
      edit.edit(this);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

}
