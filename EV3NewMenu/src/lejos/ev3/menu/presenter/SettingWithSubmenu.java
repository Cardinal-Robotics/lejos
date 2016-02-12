package lejos.ev3.menu.presenter;

import java.util.ArrayList;
import java.util.List;

import lejos.ev3.menu.components.Icons;

public class SettingWithSubmenu extends SettingDetail{

  private Node subMenu;

  public SettingWithSubmenu(String key, String label, String format, String defaultValue, Node subMenu) {
    super(key, label, format, defaultValue);
    this.subMenu = subMenu;
  }
  
  @Override
  public List<String> execute() {
    List<Node> submenu = new ArrayList<Node>();
    menu.selectFromList(new LanNode("Access points", Icons.WIFI));
    return null;
    }


}
