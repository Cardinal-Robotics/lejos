package lejos.ev3.menu.presenter;

public class PanMode extends BaseDetail{
  
  public PanMode(String value) {
  super("Pan.mode", "Mode", "%3$s", "NONE");
  this.value = value;
  initialized = true;
  this.selectable = true;
  this.addSpecialValue("NONE", "Disable");
  this.addSpecialValue("AP", "Access Pt");
  this.addSpecialValue("AP+", "Access Pt+");
  this.addSpecialValue("BT", "BT Client");
  this.addSpecialValue("USB", "USB Client");
  }

}
