package lejos.ev3.menu.model;

import java.util.List;

import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.Icons;
import lejos.hardware.lcd.GraphicsLCD;

public class NodeFiles extends BaseNode {
  public static final String PROGRAMS_DIRECTORY = "/home/lejos/programs";
  public static final String LIB_DIRECTORY = "/home/lejos/lib";
  public static final String SAMPLES_DIRECTORY = "/home/root/lejos/samples";
  public static final String TOOLS_DIRECTORY = "/home/root/lejos/tools";


  public NodeFiles(MenuControl control, String path, String filter) {
    super(control);
    alignment = GraphicsLCD.LEFT | GraphicsLCD.TOP;
    setIcon(Icons.FILES);
    setLabel("Files");
    this.selectableDetails = true;
    List<String> list = control.getFiles(path, filter);
    
    for (String fn : list) {
          addDetail(new DetailProgram(control, fn));
        }
    }
  }
