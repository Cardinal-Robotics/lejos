package lejos.ev3.menu.presenter;

import java.util.Collection;
import java.util.List;

import lejos.ev3.menu.components.Icons;
import lejos.hardware.RemoteBTDevice;
import lejos.hardware.lcd.Image;

public class BtPairedDevices extends ItemBase {
 
  public BtPairedDevices(String label, Image icon ) {
    super( label , icon);
  }
  
 
  @Override
  protected void populate() {
    Collection<RemoteBTDevice> entries = model.getBTModel().getRemoteDevices(true);
    clearDetails();
      for (RemoteBTDevice entry: entries) {
        addDetail(new BtCommand( "FORGET","Forget", entry));
      }
      if (entries.isEmpty()) addDetail(new BaseDetail("", "<No paired devices>", "%2$s", "", false));
    populated = true;
  }
  

}
