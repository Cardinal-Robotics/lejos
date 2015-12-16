package lejos.ev3.menu.model;

import java.nio.file.Path;

import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.viewer.FileViewer;
import lejos.ev3.menu.viewer.Menu;

/** Command to view a file
 * @author Aswin Bouwmeester
 *
 */
public class DetailViewCommand extends DetailFileCommand {

  public DetailViewCommand(Control control,  Path path) {
    super(control, "View", " " , path);
  }
  
  @Override 
  public void select(Menu menu) {
    FileViewer view = new FileViewer(control, path);
    
  }

}
