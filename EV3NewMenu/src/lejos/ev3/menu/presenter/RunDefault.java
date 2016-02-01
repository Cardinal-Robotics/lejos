package lejos.ev3.menu.presenter;

import java.util.List;


public class RunDefault extends SettingDetail{
  
  public RunDefault() {
    super("lejos.default_program", "", "Run %2$s", null);
    initialize();
    this.addSpecialValue("", "No default set");
    model.attach(key, this);
  }

  @Override
  public void initialize() {
    super.initialize();
    if (value=="") {
      selectable =false;
      label = "";
    }
    else {
      selectable =true;
      label = shortName();
    }
  }
  
@Override
  public void select() {
    menu.suspendMenu();
    super.select();
    menu.resumeMenu();
  }

@Override
public List<String> execute() {
  return control.execute("RUN_PROGRAM", value);
}


protected String shortName() {
  if (value == null) return "";
  int i = value.lastIndexOf(java.io.File.separator);
  if (i == -1) return value;
  return value.substring(i+1);
}


}
