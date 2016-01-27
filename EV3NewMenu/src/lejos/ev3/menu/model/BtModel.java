package lejos.ev3.menu.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import lejos.hardware.BluetoothException;
import lejos.hardware.LocalBTDevice;
import lejos.hardware.RemoteBTDevice;

/** This model controls all Bluetooth related stuff
 * @author Aswin Bouwmeester
 *
 */
public class BtModel extends AbstractModel {
  static LocalBTDevice bt = new LocalBTDevice();
  private Collection<RemoteBTDevice> cachedPaired;
  private Collection<RemoteBTDevice> cachedRemote;
  

  
  protected BtModel(){
    myKeys = Arrays.asList("bluetooth.visibility", "bluetooth.address", "bluetooth.name" );
    myCommands = Arrays.asList("PAIR", "FORGET" );
    myLists = Arrays.asList("PAIRED_DEVICES", "REMOTE_DEVICES" );
  }
  
  
  public String getSetting(String key, String defaultValue) {
    switch(key.split("\\.")[1]) {
    case "address" :  return bt.getBluetoothAddress();
    case "visibility" : return Boolean.toString(bt.getVisibility());
    case "name" : return bt.getFriendlyName(); 
    }
  return null;
  }
  
  public void setSetting(String key, String value) {
    switch(key.split("\\.")[1]) {
    case "visibility" : { bt.setVisibility(Boolean.parseBoolean(value)); break;}
    default: return;
    }
    broadcast(key, value); 
  }
  
  
  @Override
  public List<String> getList(String list, String parameter) {
    switch(list) {
    case "PAIRED_DEVICES": {
      cachedPaired = bt.getPairedDevices();
      return toNameList(cachedPaired);
    }
    case "REMOTE_DEVICES": {
      cachedRemote =  bt.search();
      return toNameList(cachedRemote);
    }
    }
    return null;
  }


  private List<String> toNameList(Collection<RemoteBTDevice> b) {
    List<String> a = new ArrayList<String>();
    for (RemoteBTDevice device : b) a.add(device.getName());
    return a;
  }

  public List<String> execute(String command, String name) {
    switch (command) {
    case ("PAIR"): { 
      try {
        bt.authenticate(toAddress(this.cachedRemote, name), ModelContainer.getModel().getSetting("lejos.bluetooth.pin", "1234"));
        broadcast("REMOTE_DEVICES","");
        broadcast("PAIRED_DEVICES","");
        } catch (BluetoothException e) {
          System.err.println("Failed to set visibility: " + e);
          return getStackTrace(e);
      }
      break;
      }
    case ("FORGET"): {
      try {
        bt.removeDevice(toAddress(this.cachedPaired, name));
        broadcast("REMOTE_DEVICES","");
        broadcast("PAIRED_DEVICES","");
        } catch (BluetoothException e) {
          System.err.println("Failed to unpair device: " + e);
          return getStackTrace(e);
      }
      break;
    }
    
    }
    return null;
  }
  
private String toAddress(Collection<RemoteBTDevice> collection, String name) {
  for (RemoteBTDevice device : collection) 
    if(device.getName().equals(name)) return device.getAddress();
  return null;
}


}
