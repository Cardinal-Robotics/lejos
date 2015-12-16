package lejos.ev3.menu.control;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * Controls the EV3 VM
 * 
 * @author Aswin Bouwmeester
 *
 */
public interface Control {

  /**
   * Returns a property or setting of the leJOS VM
   * 
   * @param key
   * @return
   */
  String getProperty(String key);

  /**
   * Returns a property or setting of the leJOS VM
   * 
   * @param key
   * @param defaultValue
   * @return
   */
  String getProperty(String key, String defaultValue);

  /**
   * Sets a character property or setting of the leJOS VM
   * 
   * @param key
   * @param value
   */
  void setProperty(String key, String value);

  /**
   * Returns a numeric property or setting of the leJOS VM
   * 
   * @param key
   * @return
   */
  int getNumericProperty(String key);

  /**
   * returns a numeric property or setting of the leJOS VM
   * 
   * @param key
   * @param defaultValue
   * @return
   */
  int getNumericProperty(String key, int defaultValue);

  /**
   * Sets a numeric property or setting of the leJOS VM
   * 
   * @param key
   * @param value
   */
  void setNumericProperty(String key, int value);

  /**
   * Executes a command on a file
   * 
   * @param command
   *          {RUN | DEBUG | SET_DEFAULT | DELETE}
   * @param path
   */
  void execute(String command, Path path);


  List<String> readFile(Path path);

  List<Path> getEntries(Path path, String Glob);

  
  boolean isDirectory(Path path);

}
