package lejos.ev3.menu.model;

import java.nio.file.Path;

import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.viewer.Editor;
import lejos.ev3.menu.viewer.Menu;

public class DetailSetDefault extends DetailStringValue {


  /** Sets the path as the default program
   * @param control
   * @param path
   */
  public DetailSetDefault(Control control, Path path) {
    super(control, "Set as default", "lejos.default_program", "%s");
    this.value = path.toString();
    isSelectable = true;
    isInitialized = true;
  }
  
  @Override
  public void select(Menu menu) {
    menu.notifyOn("Setting default");
    this.setValue(value);
    menu.notifyOff();
    menu.selectParent();
  }


}
