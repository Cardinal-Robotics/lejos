package lejos.ev3.menu.presenter;

import java.util.List;

import lejos.ev3.menu.components.Icons;
import lejos.ev3.menu.viewer.EditorString;

public class LanConnect extends BaseDetail{
  String remoteKey=" ";


  public LanConnect(String entry) {
    super("CONNECT", "Connect", "%3$s", "", true);
    this.value = entry;
    initialized = true;
  }
  
  
  
@Override
  protected boolean preExecute() {
  String newValue = EditorString.editValue();
  if (newValue.isEmpty()) return false;
  remoteKey = newValue;
  return true;
  }



@Override
  protected List<String> execute() {
  menu.notifyOn(Icons.WIFI, "Connecting to:\n" + value);
    List<String> r = model.execute(key, value, remoteKey);
    menu.notifyOff();
    if (r == null) {
      menu.selectParent();
    }
    else {
      menu.dialog("Pairing failed",2);
    }
    return null;
  }

  

}
