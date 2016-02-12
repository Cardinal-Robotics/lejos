package lejos.ev3.menu.presenter;

import java.util.Map;

import lejos.hardware.lcd.Image;

public class SelectionList extends BaseNode {

  public SelectionList(String label, Image icon, Map<String, String> list) {
    super(label, icon);
    for (Map.Entry<String, String> item : list.entrySet()) {
      this.addDetail(new BaseDetail(item.getKey(), item.getValue(), "%2$s", "" , true));
    }
    isFresh = true;
  }
}
