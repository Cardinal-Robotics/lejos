package lejos.ev3.menu.model;

import java.io.File;

import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.viewer.Icons;
import lejos.ev3.menu.viewer.Menu;

/**
 * Detail to display a single file in a Files Menu item
 * 
 * @author Aswin Bouwmeester
 *
 */
public class DetailFile extends DetailBase {

  protected String  label;
  protected String  fullName;
  private ItemFiles parent;

  /**
   * @param control
   *          The control that provides access to the file
   * @param fullName
   *          The Full name of the file
   * @param parent
   *          The Files Menu Item that displays this detail
   */
  public DetailFile(Control control, String fullName, ItemFiles parent) {
    super(control);
    this.label = fullName.substring(fullName.lastIndexOf('/') + 1, fullName.length());
    this.fullName = fullName;
    isInitialized = true;
    this.isSelectable = true;
    this.parent = parent;
  }

  @Override
  protected void initialize() {
  }

  @Override
  public String toString() {
    return label;
  }

  /**
   * Selects this file. A new menu screen will be displayed. If the file is a
   * directory this new menu screen will display all the files within this
   * directory. If the file is a file then the nem menu screen will display a
   * list of commands that can be executed on this file.
   * 
   */
  @Override
  public void select(Menu menu) {
    parent.removeChildren();
    File file = control.getFile(fullName);
    if (file.isDirectory()) {
      parent.addChild(new ItemFiles(control, fullName));
    } else {
      MenuItem child = new ItemBase(control, label, Icons.EYE);
      if (fullName.contains(".jar")) {
        child.addDetail(new DetailFileCommand(control, "Run", "RUN", fullName));
        if (fullName.contains(ItemFiles.PROGRAMS_DIRECTORY)) {
          child.addDetail(new DetailFileCommand(control, "Debug", "DEBUG", fullName));
          child.addDetail(new DetailFileCommand(control, "Set as default", "SET_DEFAULT", fullName));
        }
      }
      if (file.canWrite()) {
        child.addDetail(new DetailFileCommand(control, "Delete", "DELETE", fullName));
      }

      parent.addChild(child);
    }
    menu.selectChild();
  }

}
