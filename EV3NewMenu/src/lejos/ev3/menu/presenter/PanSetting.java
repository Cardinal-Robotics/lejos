package lejos.ev3.menu.presenter;

public class PanSetting extends SettingDetail {

  public PanSetting() {
    super("Pan.mode", "Mode", "%3$s", "NONE");
    this.selectable = true;
    this.addSpecialValue("NONE", "Not set");
    this.addSpecialValue("AP", "Access Pt");
    this.addSpecialValue("AP+", "Access Pt+");
    this.addSpecialValue("BT", "BT Client");
    this.addSpecialValue("USB", "USB Client");
  }
  
  @Override
  public void select() {
    menu.selectChild();
  }

}
