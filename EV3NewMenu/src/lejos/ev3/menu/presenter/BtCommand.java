package lejos.ev3.menu.presenter;

import java.util.List;

import lejos.ev3.menu.components.Icons;
import lejos.hardware.RemoteBTDevice;


public class BtCommand extends BaseDetail {

  private RemoteBTDevice entry;

  public BtCommand(String command, String label, RemoteBTDevice entry) {
    super(command, label, "%3$s", "", true);
    this.value = entry.getName();
    this.entry = entry;
    initialized = true;
  }
  
@Override
  protected List<String> execute() {
    return model.getBTModel().execute(key, entry);
  }

@Override
public void select() {
  menu.notifyOn(Icons.BLUETOOTHCLIENT, label + "\n" + entry.getName());
  super.select();
  menu.notifyOff();
  menu.repopulate();
}
}
 