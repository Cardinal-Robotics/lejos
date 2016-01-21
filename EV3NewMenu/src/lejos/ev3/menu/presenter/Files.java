package lejos.ev3.menu.presenter;

import java.util.List;

import lejos.ev3.menu.components.Icons;
import lejos.ev3.menu.model.FilesChanged;

public class Files extends ItemBase implements FilesChanged{
 
  public static final String PROGRAMS_DIRECTORY = "/home/lejos/programs";
  public static final String LIB_DIRECTORY      = "/home/lejos/lib";
  public static final String SAMPLES_DIRECTORY  = "/home/root/lejos/samples";
  public static final String TOOLS_DIRECTORY    = "/home/root/lejos/tools";
  public static final String NOT_SET            = null;
  protected String           path;
  protected String           glob = "*";

  public Files(String path) {
    this( path, "*");
  }
  
  public Files( String path, String glob) {
    super( path , Icons.FILES);
    this.path = path;
    this.glob = glob;
    model.getFilesModel().attach(this);
  }
  
  @Override
  public List<Detail> getDetails() {
    return super.getDetails();
  }

  @Override
  public Detail getDetail(int index) {
    return super.getDetail(index);
  }
  
  @Override
  protected void populate() {
    List<String> entries = model.getFilesModel().getEntries(path, glob);
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
  public void filesChanged(String path) {
    if (this.path.equals(path)) {
      populated = false;
    }
  }

  @Override
  public void detach() {
    model.getFilesModel().detach(this);
  }

  

}
