package lejos.ev3.menu.presenter;

import lejos.hardware.lcd.Image;

public class ItemFile extends ItemBase {

  private String file;

  public ItemFile(String file, String label, Image icon) {
    super(label, icon);
    this.file = file;
    model.attach("DELETE", this);
  }

  @Override
  public void keyChanged(String key, String value) {
  if (key.equals("DELETE") && value.equals(file)) {
    // The file that is the target of this menu screen has been deleted
    model.detach("DELETE", this);
    menu.selectParent();
  }
  }

}
