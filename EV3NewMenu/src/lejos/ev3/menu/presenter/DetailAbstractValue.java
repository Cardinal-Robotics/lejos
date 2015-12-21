package lejos.ev3.menu.presenter;

import lejos.ev3.menu.components.Editor;

/**
 * Abstraction for a property or setting of the leJOS VM
 * 
 * @author Aswin Bouwmeester
 *
 */
public abstract class DetailAbstractValue extends DetailBase {

  protected String                  key;
  protected String                  format;
  private boolean                   isEditable = false;
  protected Class<? extends Editor> editor     = null;
  protected String                  label;

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
  public DetailAbstractValue( String label, String key, String format) {
    super();
    this.label = label;
    this.key = key;
    this.format = format;
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
  public DetailAbstractValue( String label, String key, String format, Class<? extends Editor> editor) {
    this( label, key, format);
    this.key = key;
    this.format = format;
    this.editor = editor;
    isEditable = true;
    isSelectable = true;
  }

  /**
   * Returns the format used to display the label,value pair
   * 
   * @return
   */
  public String getFormat() {
    return format;
  }

  /**
   * Returns the text to identify the property or setting for the user
   * 
   * @return
   */
  public String getLabel() {
    return label;
  }

  /**
   * Returns true if the property or setting can be edited (in this context).
   * 
   * @return
   */
  public boolean isEditable() {
    return isEditable;
  }

}
