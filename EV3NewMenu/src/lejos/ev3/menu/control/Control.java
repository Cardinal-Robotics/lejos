package lejos.ev3.menu.control;

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
   * Executes a command on a file
   * 
   * @param command
   *          {RUN | DEBUG | SET_DEFAULT | DELETE}
   * @param value
   */
  List<String> execute(String command, String value);



}
