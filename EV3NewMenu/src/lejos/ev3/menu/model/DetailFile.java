package lejos.ev3.menu.model;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

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
  private static int maxLength = 17; 

  protected String  label;
  protected Path  path;
  private ItemFiles parent;

  /**
   * @param control
   *          The control that provides access to the file
   * @param fullName
   *          The Full name of the file
   * @param parent
   *          The Files Menu Item that displays this detail
   */
  public DetailFile(Control control, Path path, ItemFiles parent) {
    super(control);
    this.label = truncate(path.getFileName().toString());
    this.path = path;
    isInitialized = true;
    this.isSelectable = true;
    this.parent = parent;
  }
  
  public static void setMaxLength(int length) {
    maxLength = length;
  }
  
  protected String truncate(String in) {
    if (in.length()<maxLength) return in;
    return in.substring(0, maxLength-5)+"."+in.substring(in.length()-4);
    
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
   * directory. If the file is a file then the menu screen will display a
   * list of commands that can be executed on this file.
   * In case no commands are available for the file type the method does nothing.
   * 
   */
  @Override
  public void select(Menu menu) {
    parent.removeChildren();
    if (control.isDirectory(path)) {
      parent.addChild(new ItemFiles(control, path));
    }
    else {
      MenuItem child = new ItemBase(control, label, Icons.EYE);
      if (isFiletype("jar") && path.getNameCount()>0) {
        if (path.getParent().equals(ItemFiles.PROGRAMS_DIRECTORY)) {
          child.addDetail(new DetailFileCommand(control, "Run", "RUN_SEPERATE", path));
          child.addDetail(new DetailFileCommand(control, "Debug", "DEBUG", path));
          Path d = Paths.get(control.getProperty("lejos.default_program"),"");
          if (d.equals(path))
            child.addDetail(new DetailUnsetDefault(control, path));
          else
            child.addDetail(new DetailSetDefault(control, path));
        }
        else if (path.getParent().equals(ItemFiles.TOOLS_DIRECTORY) | path.getParent().equals(ItemFiles.SAMPLES_DIRECTORY)) {
          child.addDetail(new DetailFileCommand(control, "Run", "RUN", path));
        }
      }
      else if (isFiletype("{out,err,txt}") ) {
        child.addDetail(new DetailViewCommand(control, path));
      }
      if (path.getParent().equals(ItemFiles.PROGRAMS_DIRECTORY)) {
        child.addDetail(new DetailFileCommand(control, "Delete", "DELETE", path));
      }
      if (!child.hasDetails()) return;
      parent.addChild(child);
    }
    menu.selectChild();
  }
  
  /**
   * @param glob file extention in glob format without the dot or wildcard
   * @return
   */
  protected boolean isFiletype( String glob) {
    return FileSystems.getDefault().getPathMatcher("glob:**."+ glob).matches(path);
  }
  


}
