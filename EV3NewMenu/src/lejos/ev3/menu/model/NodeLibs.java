package lejos.ev3.menu.model;

import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.Icons;
import lejos.hardware.lcd.GraphicsLCD;


  public class NodeLibs extends NodeFiles {



    public NodeLibs(MenuControl control) {
      super(control, LIB_DIRECTORY, ".jar");
      alignment = GraphicsLCD.LEFT | GraphicsLCD.TOP;
      setIcon(Icons.PROGRAM);
      setLabel("Programs");
    }
  }
