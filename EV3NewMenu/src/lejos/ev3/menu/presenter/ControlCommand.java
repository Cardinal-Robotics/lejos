package lejos.ev3.menu.presenter;

import java.util.List;

public class ControlCommand extends BaseDetail {

  public ControlCommand(String command, String label, String value) {
    super(command, label, "%2$s", value, true);
    this.value = value;
    initialized = true;
  }
  
@Override
  protected List<String> execute() {
    return control.execute(key, value);
  }

@Override
public void select() {
  menu.suspendMenu();
  super.select();
  menu.repopulateParent();
  menu.selectParent();
  menu.resumeMenu();
}




  
  

}
