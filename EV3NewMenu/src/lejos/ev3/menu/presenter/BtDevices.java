package lejos.ev3.menu.presenter;

import java.util.List;

import lejos.ev3.menu.components.Icons;
import lejos.hardware.lcd.Image;

public class BtDevices extends ItemBase {
 
  
  public BtDevices(String label, Image icon ) {
    super( label , icon);
    this.key = "REMOTE_DEVICES";
    model.attach(key, this);
  }
  
 
  @Override
  protected void populate() {
    menu.notifyOn(Icons.BLUETOOTH, "Searching...");
    List<String> entries = model.getList(key, null);
    clearDetails();
    addDetail(new BtRepopulate());
    if (entries == null || entries.isEmpty() ) {
      addDetail(new BaseDetail("", "<No devices found>", "%2$s", "", false));
    }
    else
      for (String entry: entries) {
        addDetail(new BtCommand( "PAIR","Pair", entry));
      }
    populated = true;
    menu.notifyOff();
  }
  

}
