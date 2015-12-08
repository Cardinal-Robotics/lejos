package lejos.ev3.menu.control;

import java.util.List;

public interface MenuControl {

  String getVersion();

  String getProperty(String key);

  String getProperty(String key, String defaultValue);

  void setProperty(String key, String value);
  
  int getNumericProperty(String key);

  int getNumericProperty(String key, int defaultValue);

  void setNumericProperty(String key, int value);
  
  public List<String> getFiles(String path, String filter);

  void execute(String command, String id);
  

  
}
