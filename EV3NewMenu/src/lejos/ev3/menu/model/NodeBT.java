package lejos.ev3.menu.model;

import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.Icons;

public class NodeBT extends BaseNode {

  public NodeBT(MenuControl model) {
    super(model);
    setIcon(Icons.BLUETOOTH);
    setLabel("BlueTooth");
    addDetail(new DetailConstruction(model));
  }

}
