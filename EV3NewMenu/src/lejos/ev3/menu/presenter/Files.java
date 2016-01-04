package lejos.ev3.menu.presenter;

import java.util.List;

import lejos.ev3.menu.components.Icons;

public class Files extends ItemBase {
 
  public static final String PROGRAMS_DIRECTORY = "/home/lejos/programs";
  public static final String LIB_DIRECTORY      = "/home/lejos/lib";
  public static final String SAMPLES_DIRECTORY  = "/home/root/lejos/samples";
  public static final String TOOLS_DIRECTORY    = "/home/root/lejos/tools";
  public static final String NOT_SET            = null;
  protected boolean          initialized        = false;
  protected String           path;
  protected String           glob = "*";

  public Files(String path) {
    this( path, "*");
  }
  
  public Files( String path, String glob) {
    super( path , Icons.FILES);
    this.path = path;
    this.glob = glob;
  }
  
  @Override
  public List<Detail> getDetails() {
    if (!initialized)
      initialize();
    return super.getDetails();
  }

  @Override
  public Detail getDetail(int index) {
    if (!initialized)
      initialize();
    return super.getDetail(index);
  }
  
  protected void initialize() {
    List<String> entries = model.getEntries(path, glob);
    this.removeChildren();
    for (String entry : entries) {
      addDetail(new File( entry, this));
    }
    initialized = true;
  }
  
  @Override 
  public String getLabel() {
    int i = super.getLabel().lastIndexOf(java.io.File.separator);
    return super.getLabel().substring(i+1);
  }

  

}
