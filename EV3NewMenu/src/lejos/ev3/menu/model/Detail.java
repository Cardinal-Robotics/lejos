package lejos.ev3.menu.model;

import lejos.hardware.lcd.GraphicsLCD;


public interface Detail {
  
  static int NUMERIC = 0;
  static int STRING = 1;
  static int TYPE_INFO = 0;
  static int TYPE_SELECTABLE = 1;
  static int TYPE_EDITABLE = 2;
  static int TYPE_COMMAND = 3;


  public boolean isEditable();

  public boolean isSelectable();
  
  public boolean isCommand();

  public int getType();

  public Detail setLabel(String label);

  public String getLabel();

  public Detail setNValue(int value);

  public int getNValue();

  public Detail setSValue(String value);

  public String getSValue();

  public Detail setFormat(String format);

  public String getFormat();
  
  public String getID();
  
  public boolean edit(GraphicsLCD canvas);
  
  public int getDetailType();

  public boolean canHaveFocus();

}
