package lejos.ev3.menu.model;


import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.Editor;
import lejos.hardware.lcd.GraphicsLCD;

public  class BasePropertyDetail extends BaseDetail {
  
  protected String key;
  
  
  public BasePropertyDetail(MenuControl control, String label, String key, String format,  int defaultValue, Editor editor) {
    super(control, label, control.getNumericProperty(key,defaultValue), format, Detail.TYPE_EDITABLE );
    this.key = key;
    this.editor = editor;
  }

  public BasePropertyDetail(MenuControl control, String label, String key, String format,  String defaultValue, Editor editor) {
    super(control, label, control.getProperty(key,defaultValue), format, Detail.TYPE_EDITABLE );
    this.key = key;
    this.editor = editor;
  }

  @Override
  public boolean edit(GraphicsLCD canvas) {
    return super.edit(canvas);
  }

  @Override
  public Detail setNValue(int value) {
    control.setNumericProperty(key, value);
    return super.setNValue(value);
  }

  @Override
  public Detail setSValue(String value) {
    control.setProperty(key, value);
    return super.setSValue(value);
  }
  
  
  
  
  
  

}
