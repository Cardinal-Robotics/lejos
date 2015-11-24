package lejos.ev3.menu.model;

import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.Icons;

public class NodeWifi extends BaseNode {

  public NodeWifi(MenuControl model) {
    super(model);
    setIcon(Icons.WIFI);
    setLabel("Wifi");
    addDetail(new DetailConstruction(model));
  }

}
