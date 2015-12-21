package lejos.ev3.menu.presenter;

import java.nio.file.Paths;

import lejos.ev3.menu.control.Control;

/** Detail to show a Run default item in the menu
 * @author Aswin Bouwmeester
 *
 */
public class DetailRunDefault extends DetailManagedCommand {

  public DetailRunDefault() {
    super( "", "", null);
    isSelectable =false;
    isInitialized = false;
  }
  
  @Override
  public void initialize() {
    String def = control.getProperty("lejos.default_program");
    if (def.isEmpty()) {
      isSelectable =false;
      path = null;
      command = "";
      label = "No default set";
    }
    else {
      isSelectable =true;
      path = Paths.get(def);
      command = "RUN_PROGRAM";
      label = "Run " + path.getFileName();
    }
    isInitialized = true;
  }
  
  @Override
  public String toString() {
    if (!isInitialized) initialize();
    return label;
  }

}
