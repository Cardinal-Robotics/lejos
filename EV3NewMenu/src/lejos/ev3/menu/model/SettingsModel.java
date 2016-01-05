package lejos.ev3.menu.model;

import java.util.List;


public interface SettingsModel {

  public String getSetting(String key, String defaultValue);
  
  public void setSetting(String key, String value);


}
