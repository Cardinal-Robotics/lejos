package lejos.ev3.menu.presenter;

import java.util.ArrayList;
import java.util.List;

import lejos.ev3.menu.components.Icons;

public class SsidDetail extends SettingDetail{

  public SsidDetail(String key, String label, String format) {
    super(key, label, format, "");
    this.selectable = true;
    this.addSpecialValue("", "Connect");
  }

  @Override
  public void select() {
    List<Node> submenu = new ArrayList<Node>();
    menu.selectFromList(new LanNode("Access points", Icons.WIFI));
    }
  
  

}
