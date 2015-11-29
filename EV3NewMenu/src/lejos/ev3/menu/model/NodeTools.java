package lejos.ev3.menu.model;


import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.Icons;
import lejos.hardware.lcd.GraphicsLCD;

public class NodeTools extends NodeFiles {



  public NodeTools(MenuControl control) {
    super(control, TOOLS_DIRECTORY, ".jar");
    alignment = GraphicsLCD.LEFT | GraphicsLCD.TOP;
    setIcon(Icons.TOOLS);
    setLabel("Tools");
  }
}
