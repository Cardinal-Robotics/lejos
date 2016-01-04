package lejos.ev3.menu.presenter;

import lejos.ev3.menu.components.Icons;

public class File extends BaseDetail {

  private MenuItem parent;

  public File(String file, MenuItem parent) {
    super("FILE", file, "%2$s", null, true);
    this.value = file;
    this.label = shortName();
    this.parent = parent;
    initialized = true;
  }

  @Override
  public int select() {
    parent.removeChildren();
    if (model.isDirectory(value)) {
      parent.addChild(new Files(value));
    } else {
      MenuItem child = new ItemBase(label, Icons.EYE);
      if (isFiletype("jar")) {
        if (isIn(Files.PROGRAMS_DIRECTORY)) {
          child.addDetail(new Command("RUN_PROGRAM", "Run", value));
          child.addDetail(new Command("DEBUG_PROGRAM", "Debug", value));
          String d = model.getSetting("lejos.default_program", null);
          if (d == null)
            child.addDetail(new SetDefault("Set as default", value));
          else if (d.equals(value))
            child.addDetail(new SetDefault("Unset as default", ""));
          else
            child.addDetail(new SetDefault("Set as default", value));
        } else if (isIn(Files.TOOLS_DIRECTORY)) {
          child.addDetail(new Command("RUN_TOOL", "Run", value));
        } else if (isIn(Files.SAMPLES_DIRECTORY)) {
          child.addDetail(new Command("RUN_SAMPLE", "Run", value));
        }
      } else if (isFiletype("{out,err,txt}")) {
        child.addDetail(new ViewCommand(value));
      }
      if (isIn(Files.PROGRAMS_DIRECTORY)) {
        child.addDetail(new Command("DELETE", "Delete", value));
      }
      if (!child.hasDetails())
        return 0;
      parent.addChild(child);
    }
    return 1;
  }

  /**
   * @param glob
   *          file extention in glob format without the wildcard
   * @return
   */
  protected boolean isFiletype(String glob) {
    if (value.lastIndexOf(glob) == value.length() - glob.length())
      return true;
    return false;
  }

  protected boolean isIn(String dir) {
    if (value.indexOf(dir) == 0)
      return true;
    return false;
  }

  protected String shortName() {
    int i = value.lastIndexOf(java.io.File.separator);
    return value.substring(i + 1);
  }

}
