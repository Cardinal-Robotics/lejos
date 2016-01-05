package lejos.ev3.menu.presenter;

import java.util.List;

public class FilesCommand extends BaseDetail {

  public FilesCommand(String command, String label, String value) {
    super(command, label, "%2$s", value, true);
    this.value = value;
    initialized = true;
  }
  
@Override
  protected List<String> execute() {
    return filesModel.execute(key, value);
  }

@Override
public void select() {
  super.select();
  menu.repopulateParent();
  menu.selectParent();
}
}
