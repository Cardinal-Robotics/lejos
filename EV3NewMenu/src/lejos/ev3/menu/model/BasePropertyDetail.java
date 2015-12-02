package lejos.ev3.menu.model;


import lejos.ev3.menu.control.MenuControl;

public  class BasePropertyDetail extends BaseDetail {
  
  protected String key;
  
  public BasePropertyDetail(MenuControl control, String label, String key, String format,  int defaultValue) {
    super(control, label, control.getNumericProperty(key,defaultValue), format, Detail.TYPE_EDITABLE );
    this.key = key;
  }

  public BasePropertyDetail(MenuControl control, String label, String key, String format,  String defaultValue) {
    super(control, label, control.getProperty(key,defaultValue), format, Detail.TYPE_EDITABLE );
    this.key = key;
  }

  @Override
  public Detail setNValue(int value) {
    control.setNumericProperty(key, value);
    return super.setNValue(value);
  }

  @Override
  public int getNValue() {
    return control.getNumericProperty(key);
  }

  @Override
  public Detail setSValue(String value) {
    control.setProperty(key, value);
    return super.setSValue(value);
  }

  @Override
  public String getSValue() {
    return super.control.getProperty(key);
  }
  
  

}
