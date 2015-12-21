package lejos.ev3.menu.presenter;

import java.nio.file.Path;
import java.util.List;

import lejos.ev3.menu.viewer.Menu;

/**
 * Detail representing a file command
 * 
 * @author Aswin Bouwmeester
 *
 */
public class DetailFileCommand extends DetailLabel {

  protected String command;
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
  public DetailFileCommand( String label, String command, Path path) {
    super(label);
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
     if (preExecute(menu)) postExecute(execute(), menu);
  }
  
  /** This method is called prior to calling the execute method. Only if this method returns true execute is called. <br>
   * This method is here to be overriden, for example to display a "are you sure?" dialog.
   * @return
   */
  protected boolean preExecute(Menu menu) {
    return true;
  }
  
  /** Instructs the control to execute a file command.
   * @return A list of strings containing the text of the exception resulting from executing the command  
   */
  protected List<String> execute() {
    // TODO: get the returning list from the control
    control.execute(command, path);
    return null;
  }
  
  /** This method is called immediately after the execution of the command. It receives the message from the control.
   * @param result
   */
  protected void postExecute(List<String> result, Menu menu) {
  }
  
  @Override
  public String toString() {
    return label;
  }

}
