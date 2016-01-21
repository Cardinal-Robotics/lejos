package lejos.ev3.menu.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lejos.hardware.BluetoothException;
import lejos.hardware.LocalBTDevice;
import lejos.hardware.RemoteBTDevice;

public class BtModel {
  static LocalBTDevice bt = new LocalBTDevice();
  private List<KeyChanged> listeners = new ArrayList<KeyChanged>();

  
  protected BtModel(){}
  
  public void attach(KeyChanged listener) {
    listeners.add(listener);
  }
  
  public void detach(KeyChanged listener) {
    listeners.remove(listener);
  }
  
  public String getSetting(String key, String defaultValue) {
    switch(key.split("\\.")[2]) {
    case "address" :  return bt.getBluetoothAddress();
    case "visibility" : return Boolean.toString(bt.getVisibility());
    case "name" : return bt.getFriendlyName(); 
    }
  return null;
  }
  
  public void setSetting(String key, String value) {
    switch(key.split("\\.")[2]) {
    case "visibility" : { bt.setVisibility(Boolean.parseBoolean(value)); break;}
    default: return;
    }
    for (KeyChanged listener : listeners) listener.keyChanged(key, value); 
  }
  
  public Collection<RemoteBTDevice> getRemoteDevices(boolean paired) {
    try {
    if (paired) 
      return bt.getPairedDevices();
    else
      return bt.search();
    } catch (BluetoothException e) {
      System.err.println("Error getting devices: " + e);
  }
    return new ArrayList<RemoteBTDevice>();
  }

  public List<String> execute(String command, RemoteBTDevice device) {
    switch (command) {
    case ("PAIR"): { 
      try {
        bt.authenticate(device.getAddress(), Model.getModel().getSettingsModel().getSetting("lejos.bluetooth.pin", "1234"));
        } catch (BluetoothException e) {
          System.err.println("Failed to set visibility: " + e);
          return Utils.getStackTrace(e);
      }
      break;
      }
    case ("FORGET"): {
      try {
        bt.removeDevice(device.getAddress());
        } catch (BluetoothException e) {
          System.err.println("Failed to unpair device: " + e);
          return Utils.getStackTrace(e);
      }
      break;
    }
    
    }
    return null;
  }
  

}
