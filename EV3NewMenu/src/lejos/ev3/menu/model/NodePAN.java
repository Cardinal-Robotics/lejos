package lejos.ev3.menu.model;

import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.Icons;

public class NodePAN extends BaseNode {

  public NodePAN(MenuControl model) {
    super(model);
    setIcon(Icons.PAN);
    setLabel("PAN");
    addDetail(new DetailConstruction(model));
  }

}
