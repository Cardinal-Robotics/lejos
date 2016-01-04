package lejos.ev3.menu.presenter;

import java.util.List;


public class RunDefault extends SettingDetail{
  
  public RunDefault() {
    super("lejos.default_program", "", "Run %2$s", null);
    initialize();
    this.addSpecialValue("", "No default set");
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
  public int select() {
    menu.suspend();
    int d= super.select();
    menu.resume();
    return d;
  }

@Override
public List<String> execute() {
  return control.execute("RUN_PROGRAM", value);
}


protected String shortName() {
  int i = value.lastIndexOf(java.io.File.separator);
  return value.substring(i+1);
}


}
