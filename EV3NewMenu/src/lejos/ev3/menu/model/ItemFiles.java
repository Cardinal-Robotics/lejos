package lejos.ev3.menu.model;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.viewer.Icons;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.Image;

/**
 * An implementation of the Menu Item to display the contents of a directory
 * 
 * @author Aswin Bouwmeester
 *
 */
public class ItemFiles extends ItemBase {
  public static final String PROGRAMS_DIRECTORY = "/home/lejos/programs";
  public static final String LIB_DIRECTORY      = "/home/lejos/lib";
  public static final String SAMPLES_DIRECTORY  = "/home/root/lejos/samples";
  public static final String TOOLS_DIRECTORY    = "/home/root/lejos/tools";
  protected boolean          initialized        = false;
  protected String           path;
  protected String           filter;

  public ItemFiles(Control control, String path) {
    this(control, path, "/");
  }

  /**
   * @param control
   *          The control that provides access to the directory
   * @param path
   *          The name of the directory
   * @param filter
   *          A filter string to filter files from the directory (use ".jar"
   *          format).
   */
  public ItemFiles(Control control, String path, String filter) {
    super(control, path.substring(path.lastIndexOf('/') + 1, path.length()), Icons.FILES);
    this.path = path;
    this.filter = filter;
  }

  @Override
  public List<MenuDetail> getDetails() {
    if (!initialized)
      initialize();
    return super.getDetails();
  }

  @Override
  public MenuDetail getDetail(int index) {
    if (!initialized)
      initialize();
    return super.getDetail(index);
  }

  protected void initialize() {
    String[] members;
    File file = control.getFile(path);
    if (filter.isEmpty()) {
      members = file.list();
    } else {
      FilenameFilter f = new FilenameFilter() {

        @Override
        public boolean accept(File dir, String name) {
          if (name.lastIndexOf('.') > 0) {
            // get last index for '.' char
            int lastIndex = name.lastIndexOf('.');

            // get extension
            String str = name.substring(lastIndex);

            // match path name extension
            if (str.equals(filter)) {
              return true;
            }
          }
          return false;
        }
      };
      members = file.list(f);
    }
    for (String fn : members) {
      addDetail(new DetailFile(control, path + "/" + fn, this));
    }
    initialized = true;
  }

  protected void removeChildren() {
    this.children = null;

  }

}
