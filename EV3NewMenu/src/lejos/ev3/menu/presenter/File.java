package lejos.ev3.menu.presenter;

import java.util.ArrayList;
import java.util.List;

import lejos.ev3.menu.components.Icons;

public class File extends BaseDetail {

  private Files parent;

  public File(String file, Files parent) {
    super("FILE", file, "%2$s", null, true);
    this.value = file;
    this.label = shortName();
    this.parent = parent;
    initialized = true;
  }

  @Override
  public void select() {
    parent.removeChildren();
    if (value.endsWith(" ")) {
      // This is a directory
      Files files = new Files(value);
      List<MenuItem> submenu = new ArrayList<MenuItem>();
      submenu.add(files);
      menu.insertAndRun(submenu);
      return;
    }
    MenuItem child = new ItemFile(value,label, Icons.EYE);
    if (isFiletype("^.*\\.jar")) {
      if (isIn(Files.PROGRAMS_DIRECTORY)) {
        child.addDetail(new ControlCommand("RUN_PROGRAM", "Run", value));
        child.addDetail(new ControlCommand("DEBUG_PROGRAM", "Debug", value));
        String d = model.getSetting("lejos.default_program", null);
        if (d == null)
          child.addDetail(new SetDefault("Set as default", value));
        else if (d.equals(value))
          child.addDetail(new SetDefault("Unset as default", ""));
        else
          child.addDetail(new SetDefault("Set as default", value));
      } else if (isIn(Files.TOOLS_DIRECTORY)) {
        child.addDetail(new ControlCommand("RUN_TOOL", "Run", value));
      } else if (isIn(Files.SAMPLES_DIRECTORY)) {
        child.addDetail(new ControlCommand("RUN_SAMPLE", "Run", value));
      }
    } 
    if (isFiletype("^.*\\.(out|txt|err)$")) {
      child.addDetail(new FilesCommand("VIEW", "View", value));
    }
    if (isIn(Files.PROGRAMS_DIRECTORY) || isIn(Files.LIB_DIRECTORY)) {
      child.addDetail(new FilesCommand("DELETE", "Delete", value));
    }
    if (!child.hasDetails())
      return;
    parent.addChild(child);
    menu.selectChild();
  }

  /**
   * @param glob
   *          file extention in glob format without the wildcard
   * @return
   */
  protected boolean isFiletype(String glob) {
    if (value.matches(glob))
      return true;
    return false;
  }

  protected boolean isIn(String dir) {
    if (value.indexOf(dir) > -1)
      return true;
    return false;
  }

  protected String shortName() {
    int i = value.lastIndexOf(java.io.File.separator);
    return value.substring(i + 1);
  }

}
