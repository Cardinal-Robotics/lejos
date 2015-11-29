package lejos.ev3.menu.model;


import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.Config;

public abstract class BasePropertyDetail extends BaseDetail {
  
  protected String key;
  
  private boolean numeric =false;
  protected int  nValue;
  protected String sValue;
  protected String format ;
  protected int nDefault;
  protected String sDefault;
  
  
  protected BasePropertyDetail(MenuControl control, String label, String key, String format,  int defaultValue) {
    super(control);
    editable = true;
    this.label = label;
    this.format = format;
    this.key = key;
    this.numeric = true;
    nDefault = defaultValue;
    nValue = control.getNumericProperty(key,nDefault);
  }

  protected BasePropertyDetail(MenuControl control, String label, String key, String format,  String defaultValue) {
    super(control);
    editable = true;
    this.label = label;
    this.format = format;
    this.key = key;
    this.numeric = false;
    sDefault = defaultValue;
    sValue = control.getProperty(key, sDefault);
  }


  
  public boolean isNumeric() {
    return numeric;
  }

  
  


  @Override
  public void edit( int x, int y) {
    if (editable) {
      if (editor == null) throw new RuntimeException("No editor defined");
      if (numeric) {
       nValue = editor.edit(nValue, x + Config.DETAILS.font.stringWidth(label), y);
       control.setNumericProperty(key, nValue);
      }
      else {
        sValue = editor.edit(sValue, x + Config.DETAILS.font.stringWidth(label), y);
        control.setProperty(key, sValue);
      }
    }
  }


  public String toString() {
    if (numeric) 
      return label + String.format(format, nValue);
    return label + String.format(format, sValue);
  }

}
