package lejos.ev3.menu.control;

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
   *          {RUN | DEBUG | DELETE}
   * @param value
   */
  List<String> execute(String command, String value);

  void killProgram();

  void shutdown();

  List<String> runScript(String script);



}
