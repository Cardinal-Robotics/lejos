package lejos.ev3.menu.presenter;

import java.util.ArrayList;
import java.util.List;

import lejos.ev3.menu.components.Icons;

public class SsidDetail extends SettingDetail{

  public SsidDetail(String key, String label, String format) {
    super(key, label, format, "");
    this.selectable = true;
  }

  @Override
  public void select() {
    List<MenuItem> submenu = new ArrayList<MenuItem>();
    submenu.add(new ItemLan("Access points", Icons.WIFI));
    menu.insertAndRun(submenu);
    return;  
    }
  
  

}
