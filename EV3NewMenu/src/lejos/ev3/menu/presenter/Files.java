package lejos.ev3.menu.presenter;

import java.util.List;

import lejos.ev3.menu.components.Icons;

public class Files extends ItemBase {
 
  public static final String PROGRAMS_DIRECTORY = "/home/lejos/programs";
  public static final String LIB_DIRECTORY      = "/home/lejos/lib";
  public static final String SAMPLES_DIRECTORY  = "/home/root/lejos/samples";
  public static final String TOOLS_DIRECTORY    = "/home/root/lejos/tools";
  public static final String NOT_SET            = null;
  protected String           path;
  protected String key;

  public Files( String path) {
    super( path , Icons.FILES);
    this.path = path;
    this.key = "GET_FILES";
    model.attach(key, this);
  }
  
  @Override
  protected void populate() {
    List<String> entries = model.getList(key, path);
    clearDetails();
    for (String entry : entries) {
      addDetail(new File( entry, this));
    }
    if (entries.isEmpty()) addDetail(new BaseDetail("", "<Empty>", "%2$s", "", false));
    populated = true;
  }
  
  @Override 
  public String getLabel() {
    int i = super.getLabel().lastIndexOf(java.io.File.separator);
    return super.getLabel().substring(i+1);
  }

  @Override
  public void listChanged(String list, String parameter) {
    if (list.equals(key) && parameter.equals(path)) 
      populated = false;
  }

  

}
