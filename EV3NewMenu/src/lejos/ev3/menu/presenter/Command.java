package lejos.ev3.menu.presenter;

import java.util.List;

public class Command extends BaseDetail {

  public Command(String command, String label, String value) {
    super(command, label, "%2$s", value, true);
    this.value = value;
    initialized = true;
  }
  
@Override
  protected List<String> execute() {
    return control.execute(key, value);
  }

@Override
public int select() {
  menu.suspend();
  super.select();
  menu.resume();
  return -1;
}




  
  

}
