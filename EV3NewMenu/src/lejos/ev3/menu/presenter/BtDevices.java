package lejos.ev3.menu.presenter;

import java.util.Collection;

import lejos.ev3.menu.components.Icons;
import lejos.hardware.RemoteBTDevice;
import lejos.hardware.lcd.Image;

public class BtDevices extends ItemBase {
 
  
  public BtDevices(String label, Image icon ) {
    super( label , icon);
  }
  
 
  @Override
  protected void populate() {
    menu.notifyOn(Icons.BLUETOOTH, "Searching...");
    Collection<RemoteBTDevice> entries = model.getBTModel().getRemoteDevices(false);
    clearDetails();
    addDetail(new BtRepopulate());
    for (RemoteBTDevice entry: entries) {
      addDetail(new BtCommand( "PAIR","Pair", entry));
    }
    if (entries.isEmpty()) addDetail(new BaseDetail("", "<No devices found>", "%2$s", "", false));
    populated = true;
    menu.notifyOff();
  }
  

}
