package lejos.ev3.menu.model;

import java.nio.file.Path;

import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.viewer.Menu;

/** Unsets the default program
 * @author Aswin Bouwmeester
 *
 */
public class DetailUnsetDefault extends DetailStringValue {


  public DetailUnsetDefault(Control control, Path path) {
    super(control, "Unset as default", "lejos.default_program", "%s");
    this.value = "";
    isSelectable = true;
    isInitialized = true;
  }
  
  @Override
  public void select(Menu menu) {
     menu.notifyOn("Unsetting default");
    this.setValue(value);
    menu.notifyOff();
    menu.selectParent();
  }


}
