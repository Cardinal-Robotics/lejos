package lejos.ev3.menu.presenter;


public class SetDefault extends SettingDetail {

  private String newValue;

  public SetDefault(String label , String newValue) {
    super("lejos.default_program", label, "%2$s", newValue);
    this.newValue = newValue;
    initialized = true;
    selectable = true;
  }
  
  @Override
  public void select() {
    setValue(newValue);
    menu.selectParent();
  }

}
