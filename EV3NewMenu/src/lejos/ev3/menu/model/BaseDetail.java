package lejos.ev3.menu.model;


import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.Editor;
import lejos.hardware.lcd.GraphicsLCD;

public class BaseDetail implements Detail {
 
  
  protected boolean editable = false;
  protected MenuControl control;
  String label ;
  private final int type;
  private final int detailType;
  protected int nValue;
  protected String sValue;
  String ID;
  protected Editor editor;
  private String format;
  

  public BaseDetail(MenuControl control, String label, String value, String format, int detailType ) {
    this.control = control;
    this.type = STRING;
    this.sValue = value;
    this.detailType = detailType;
    this.label = label;
    this.format = format;
  }
  
  public BaseDetail(MenuControl control, String label, int value, String format, int detailType ) {
    this.control = control;
    this.type = NUMERIC;
    this.nValue = value;
    this.detailType = detailType;
    this.label = label;
    this.format = format;
  }
  


  @Override
  public Detail setLabel(String label) {
    this.label = label;
    return this;
  }

  @Override
  public String getLabel() {
    return label;
  }


  @Override
  public boolean isEditable() {
    return detailType == TYPE_EDITABLE ? true : false;
  }

  @Override
  public String toString() {
    if (type == NUMERIC) 
      return String.format(label + format,getNValue());
    else 
      return String.format(label + format,getSValue());
  }

  @Override
  public boolean isSelectable() {
    return detailType == TYPE_SELECTABLE ? true : false;
  }

  @Override
  public boolean isCommand() {
    return detailType == TYPE_COMMAND ? true : false;
  }
  
  @Override
  public boolean canHaveFocus() {
    return detailType > 0 ? true : false;
  }

  
  

  
  @Override
  public Detail setNValue(int value) {
    nValue = value;
    return this;
  }

  @Override
  public int getNValue() {
    return nValue;
  }

  @Override
  public Detail setSValue(String value) {
    sValue = value;
    return this;
    
  }

  @Override
  public String getSValue() {
    return sValue;
  }

  @Override
  public Detail setFormat(String format) {
    this.format = format;
    return this;
  }

  @Override
  public String getFormat() {
    return format;
  }


  @Override
  public int getType() {
    return type;
  }

  @Override
  public int getDetailType() {
    return detailType;
  }


  @Override
  public String getID() {
    // TODO Auto-generated method stub
    return ID;
  }

  @Override
  public boolean edit(GraphicsLCD canvas) {
    if (editor == null) throw new RuntimeException("No editor defined");
    return editor.edit(this, canvas);
  }
}
