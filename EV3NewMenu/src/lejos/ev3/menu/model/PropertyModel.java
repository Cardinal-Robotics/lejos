package lejos.ev3.menu.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;

public class PropertyModel extends AbstractModel{
  private Properties   props      = new Properties();
  private final String PROPS_FILE = "/home/root/lejos/settings.properties";

  protected PropertyModel() {
    try {
      // load a properties file
      props.load(new FileInputStream(PROPS_FILE));
    } catch (FileNotFoundException e) {
      // Ignore
    } catch (IOException e) {
      System.err.println("Failed to load properties file");
    }
    myKeys = Arrays.asList("bluetooth.pin", "audio.volume", "lejos.keyclick_volume", "lejos.keyclick_length", "lejos.keyclick_frequency", "lejos.default_program"  );
  }

  @Override
  public void setSetting(String key, String value) {
    props.setProperty(key, value);
    try {
      props.store(new FileOutputStream(PROPS_FILE), null);
    } catch (IOException e) {
      System.err.println("Failed to store properties");
    }
    broadcast(key, value);
      int nValue = Integer.parseInt(value);
      switch(key) {
      case "volume": {BrickFinder.getLocal().getAudio().setVolume(nValue); break;}
      case "lejos.keyclick_volume": {Button.setKeyClickVolume(nValue);break;}
      case "lejos.keyclick_length": {Button.setKeyClickLength(nValue); break;}
      case "lejos.keyclick_frequency": {Button.setKeyClickTone(Button.ID_ENTER, nValue);break;}
      }
    }
 
  @Override
  public String getSetting(String key, String defaultValue) {
    if (props.containsKey(key))
      return props.getProperty(key);
    return defaultValue;
  }

}
