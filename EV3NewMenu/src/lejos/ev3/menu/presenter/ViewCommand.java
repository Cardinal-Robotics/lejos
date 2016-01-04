package lejos.ev3.menu.presenter;

import java.util.List;

public class ViewCommand extends BaseDetail {

  public ViewCommand(String value) {
    super("", "View", "%3s15", null, true);
    this.value = value;
    initialized = true;
  }
  
  protected List<String> execute() {
    return model.readFile(value);
  }

}
