package lejos.ev3.menu.presenter;

import java.nio.file.Path;
import java.util.List;

import lejos.ev3.menu.components.Icons;
import lejos.ev3.menu.components.Viewer;
import lejos.ev3.menu.viewer.Menu;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;

public class DetailManagedCommand extends DetailFileCommand {
  private GraphicsLCD      canvas          = LocalEV3.get().getGraphicsLCD();


  public DetailManagedCommand( String label, String command, Path path) {
    super( label, command, path);
  }

  @Override
  protected boolean preExecute(Menu menu) {
    // TODO: find out what is the reason behind this command and determine if it belongs here or in the menu
    menu.notifyOn(Icons.HOUR_GLASS, new String[]{"Wait", "a", "second.."});
    menu.suspend();
    return true;
    }

  @Override
  protected void postExecute(List<String> result, Menu menu) {
    menu.resume();
    menu.notifyOff();
    if (result != null) if (result.size()>0) Viewer.view(result);
  }
  
  

}
