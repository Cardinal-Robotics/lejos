package lejos.ev3.menu.presenter;

import java.util.List;

import lejos.ev3.menu.components.Icons;
import lejos.hardware.lcd.Image;

public class BtDevices extends BaseNode {
 
  
  public BtDevices(String label, Image icon ) {
    super( label , icon);
    this.key = "REMOTE_DEVICES";
    model.attach(key, this);
  }
  
 
  @Override
  protected void refresh() {
    super.refresh();
    menu.notifyOn(Icons.BLUETOOTH, "Searching...");
    List<String> entries = model.getList(key, null);
    clearDetails();
    if (entries == null || entries.isEmpty() ) {
      addDetail(new BaseDetail("", "<No devices found>", "%2$s", "", false));
    }
    else
      for (String entry: entries) {
        addDetail(new BtPairCommand( entry));
      }
    this.selectNextDetail();
    isFresh = true;
    menu.notifyOff();
  }
  

}
