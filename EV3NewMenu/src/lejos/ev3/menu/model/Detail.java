package lejos.ev3.menu.model;

import java.util.List;
import java.util.Properties;

public interface Detail {
  
  static int NUMERIC = 0;
  static int STRING = 1;
  static int TYPE_INFO = 0;
  static int TYPE_SELECTABLE = 1;
  static int TYPE_EDITABLE = 2;


  public boolean isEditable();

  public boolean isSelectable();

  public boolean hasCommands();

  public int getType();

  public Detail setLabel(String label);

  public String getLabel();

  public Detail setNValue(int value);

  public int getNValue();

  public Detail setSValue(String value);

  public String getSValue();

  public Detail setFormat(String format);

  public String getFormat();
  
  public Detail setProperty(String key, String value);

  public Properties getProperties();

  public List<String> getCommands();

  public Detail addCommand(String label);

  public void executeCommand(int index);

}
