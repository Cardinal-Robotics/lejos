package lejos.ev3.menu.presenter;

import java.util.Map;

import lejos.ev3.menu.components.Icons;

/**
 * @author Aswin Bouwmeester
 *
 */
public class ListDetail extends SettingDetail{

  
  public ListDetail(String key, String label, String format, String defaultValue, Map<String,String> list) {
    super(key, label, format, defaultValue);
    this.specials = list;
    selectable = true;
  }
  


  @Override
  public void select() {
    SelectionList x=new SelectionList(label, Icons.YES, specials); 
    Detail selected = menu.selectFromList(x);
    if (selected == null ) return;
    setValue(selected.getKey());
  }
  

  
  


}
