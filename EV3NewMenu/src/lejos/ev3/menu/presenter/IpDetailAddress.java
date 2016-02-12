package lejos.ev3.menu.presenter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lejos.ev3.menu.components.Icons;

public class IpDetailAddress extends IpDetail{

  public IpDetailAddress() {
    super("Pan.address", "Address");
    addSpecialValue("*", "<any>");
  }

  @Override
  public void select() {
    String mode = model.getSetting("Pan.mode", "NONE");
    if (!mode.equals("BT")) {
      super.select();
    }
    else {
      Map<String, String> selMode = new LinkedHashMap<String, String>();
      selMode.put("ANY", "Use any AP");
      selMode.put("BYNAME", "Select AP by name");
      selMode.put("BYIP", "Select AP by IP");
      SelectionList x=new SelectionList(label, Icons.PAN, selMode ); 
      Detail selected = menu.selectFromList(x);
      if (selected == null ) return;
      switch(selected.getKey()) {
      case "ANY": {model.setSetting(key, "*"); break;}
      case "BYNAME": { selectByName();break;}
      case "BYIP": {if (value.equals("*")) value ="0.0.0.0";super.select(); break;}
      }
    }
  }
  
  private void selectByName(){
    List<String> devices = model.getList("PAIRED_DEVICES","");
    Map<String, String> selDevices = new LinkedHashMap<String, String>();
    for (String device : devices) {
      selDevices.put(device, device);
    }
    SelectionList x=new SelectionList(label, Icons.PAN, selDevices ); 
    Detail selected = menu.selectFromList(x);
    if (selected == null ) return;
    String device = selected.getKey();
    
  }
  
  

}
