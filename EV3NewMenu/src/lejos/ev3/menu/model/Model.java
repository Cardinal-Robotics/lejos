package lejos.ev3.menu.model;

import java.util.List;


public interface Model {

  List<String> readFile(String path);

  List<String> getEntries(String path, String glob);

  boolean isDirectory(String value);

  public String getSetting(String key, String defaultValue);
  
  public void setSetting(String key, String value);


}
