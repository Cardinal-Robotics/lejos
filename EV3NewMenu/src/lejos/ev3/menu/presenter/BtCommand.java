package lejos.ev3.menu.presenter;

import java.util.List;

import lejos.ev3.menu.components.Icons;
import lejos.hardware.RemoteBTDevice;


public class BtCommand extends BaseDetail {


  public BtCommand(String command, String label, String entry) {
    super(command, label, "%3$s", "", true);
    this.value = entry;
    initialized = true;
  }
  
@Override
  protected List<String> execute() {
    return model.execute(key, value);
  }

@Override
public void select() {
  menu.notifyOn(Icons.BLUETOOTHCLIENT, label + "\n" + value);
  super.select();
  menu.notifyOff();
  menu.repopulate();
}
}
 