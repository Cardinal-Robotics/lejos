package lejos.ev3.menu.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;

/** Model to maintain al settings that must be stored in a property file
 * @author Aswin Bouwmeester
 *
 */
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
      switch(key) {
      case "volume": {BrickFinder.getLocal().getAudio().setVolume(Integer.parseInt(value)); break;}
      case "lejos.keyclick_volume": {Button.setKeyClickVolume(Integer.parseInt(value));break;}
      case "lejos.keyclick_length": {Button.setKeyClickLength(Integer.parseInt(value)); break;}
      case "lejos.keyclick_frequency": {Button.setKeyClickTone(Button.ID_ENTER, Integer.parseInt(value));break;}
      }
    }
 
  @Override
  public String getSetting(String key, String defaultValue) {
    if (props.containsKey(key))
      return props.getProperty(key);
    return defaultValue;
  }

}
