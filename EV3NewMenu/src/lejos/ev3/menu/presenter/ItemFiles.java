package lejos.ev3.menu.presenter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import lejos.ev3.menu.components.Icons;

/**
 * An implementation of the Menu Item to display the contents of a directory
 * 
 * @author Aswin Bouwmeester
 *
 */
public class ItemFiles extends ItemBase {
  public static final Path PROGRAMS_DIRECTORY = Paths.get("/home/lejos/programs");
  public static final Path LIB_DIRECTORY      = Paths.get("/home/lejos/lib");
  public static final Path SAMPLES_DIRECTORY  = Paths.get("/home/root/lejos/samples");
  public static final Path TOOLS_DIRECTORY    = Paths.get("/home/root/lejos/tools");
  protected boolean          initialized        = false;
  protected Path           path;
  protected String           glob = "*";

  public ItemFiles(Path path) {
    this( path, "*");
  }

  /**
   * @param control
   *          The control that provides access to the directory
   * @param path
   *          The name of the directory
   * @param glob
   *          A filter string to filter files from the directory (use ".jar"
   *          format).
   */
  public ItemFiles( Path path, String glob) {
    super( path.getFileName().toString() , Icons.FILES);
    this.path = path;
    this.glob = glob;
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
    List<Path> entries = control.getEntries(path, glob);
    this.removeChildren();
    for (Path entry : entries) {
      addDetail(new DetailFile( entry, this));
    }
    initialized = true;
  }
 


}
