package lejos.ev3.menu.model;

import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.Icons;

public class NodeSystem extends BaseNode {

  public NodeSystem(MenuControl model) {
    super(model);
    setIcon(Icons.EV3);
    setLabel("System");
    addDetail(new DetailVM(model));
    addDetail(new DetailVersion(model));
  }

}
