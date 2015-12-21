package lejos.ev3.menu.presenter;

import java.nio.file.Path;

import lejos.ev3.menu.components.Viewer;
import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.viewer.Menu;

/** Command to view a file
 * @author Aswin Bouwmeester
 *
 */
public class DetailViewCommand extends DetailFileCommand {

  public DetailViewCommand( Path path) {
    super( "View", " " , path);
  }
  
  @Override 
  public void select(Menu menu) {
    Viewer.view(control, path);
  }

}
