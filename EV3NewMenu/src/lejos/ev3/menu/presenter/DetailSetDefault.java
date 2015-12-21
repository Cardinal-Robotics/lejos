package lejos.ev3.menu.presenter;

import java.nio.file.Path;

import lejos.ev3.menu.components.Editor;
import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.viewer.Menu;

public class DetailSetDefault extends DetailStringValue {


  /** Sets the path as the default program
   * @param control
   * @param path
   */
  public DetailSetDefault( Path path) {
    super( "Set as default", "lejos.default_program", "%s");
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
