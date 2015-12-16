package lejos.ev3.menu.model;

import java.nio.file.Path;
import java.nio.file.Paths;

import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.viewer.Menu;

public class DetailRunDefault extends DetailStringValue {

  private Path path;

  public DetailRunDefault(Control control) {
    super(control, "Run", "lejos.defaults_program", "%s %s");
    initialize();
    if (getValue().isEmpty()) {
      isSelectable =false;
    }
    else {
      this.path = Paths.get(getValue());
      isSelectable =true;
    }
  }
  
  @Override
  public String toString() {
    if (!isInitialized) initialize();
    if (getValue().isEmpty()) return "No default set";
    return String.format(format , label, path.getFileName());
  }
  
  @Override
  public void select(Menu menu) {
    control.execute("RUN",path );
  }


}
