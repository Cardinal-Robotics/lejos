package lejos.ev3.menu.model;

import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.Icons;
import lejos.hardware.lcd.GraphicsLCD;


  public class NodePrograms extends NodeFiles {



    public NodePrograms(MenuControl control) {
      super(control, PROGRAMS_DIRECTORY, ".jar");
      alignment = GraphicsLCD.LEFT | GraphicsLCD.TOP;
      setIcon(Icons.PROGRAM);
      setLabel("Programs");
    }
  }
