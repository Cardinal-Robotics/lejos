package lejos.ev3.menu.presenter;

import java.util.List;

import lejos.ev3.menu.components.Icons;
import lejos.hardware.lcd.Image;

public class ItemLan extends ItemBase{

  public ItemLan(String label, Image icon) {
    super(label, icon);
    this.key = "ACCESSPOINTS";
  }

  @Override
  protected void populate() {
    super.populate();
    this.clearDetails();
    menu.notifyOn(Icons.WIFI,"Scanning for\n access points");
    List<String> accesspoints = model.getList(key, " ");
    menu.notifyOff();
    addDetail(new RepopulateCommand());
    for (String ap : accesspoints) {
      this.addDetail(new LanConnect(ap));
    }
    
  }
  
  
  

}
