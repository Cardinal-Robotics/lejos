package lejos.ev3.menu.model;

import java.nio.file.Path;

import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.viewer.Menu;

/**
 * Detail representing a file command
 * 
 * @author Aswin Bouwmeester
 *
 */
public class DetailFileCommand extends DetailLabel {

  private String command;
  protected Path path;

  /**
   * @param control
   *          The control that has access to the file and can execute the
   *          command
   * @param label
   *          The text to represent the command to the user
   * @param command
   *          The command as is understood by the control
   */
  public DetailFileCommand(Control control, String label, String command, Path path) {
    super(control, label);
    this.isSelectable = true;
    this.command = command;
    this.path = path;
    isInitialized = true;
  }

  /**
   * Instructs the control to execute the command
   * 
   */
  @Override
  public void select(Menu menu) {
     menu.execute(control, command, path);
  }

  @Override
  public String toString() {
    return label;
  }

}
