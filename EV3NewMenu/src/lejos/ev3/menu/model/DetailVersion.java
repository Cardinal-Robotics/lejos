package lejos.ev3.menu.model;

import lejos.ev3.menu.control.MenuControl;

public class DetailVersion extends BaseDetail {

  protected DetailVersion(MenuControl control) {
    super(control);
  }
  
  public String toString() {
    return control.getVersion();
    
  }

}
