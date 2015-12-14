package lejos.ev3.menu.model;

import lejos.ev3.menu.control.Control;

/**
 * Detail representing a file command
 * 
 * @author Aswin Bouwmeester
 *
 */
public class DetailFileCommand extends DetailLabel {

  private String command;
  private String fileName;

  /**
   * @param control
   *          The control that has access to the file and can execute the
   *          command
   * @param label
   *          The text to represent the command to the user
   * @param command
   *          The command as is understoood by the control
   */
  public DetailFileCommand(Control control, String label, String command) {
    super(control, label);
    this.isSelectable = true;
    this.command = command;
  }

  public DetailFileCommand(Control control, String label, String command, String fileName) {
    this(control, label, command);
    this.fileName = fileName;
    isInitialized = true;
  }

  /**
   * Instructs the control to execute the command
   * 
   */
  protected void select() {
    if (isInitialized)
      control.execute(command, fileName);
  }

  @Override
  public String toString() {
    return label;
  }

}
