package lejos.ev3.menu.presenter;

import lejos.ev3.menu.model.FilesChanged;
import lejos.hardware.lcd.Image;

public class ItemFile extends ItemBase implements FilesChanged {

  private String file;

  public ItemFile(String file, String label, Image icon) {
    super(label, icon);
    this.file = file;
    model.getFilesModel().attach(this);
  }

  @Override
  public void filesChanged(String path) {
    if (path.equals(file)) menu.selectParent(); 
    
  }

  @Override
  public void detach() {
    model.getFilesModel().detach(this);
  }

}
