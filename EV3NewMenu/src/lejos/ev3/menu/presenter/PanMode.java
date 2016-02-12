package lejos.ev3.menu.presenter;

import java.util.LinkedHashMap;
import java.util.Map;

public class PanMode extends ListDetail {
  static Map<String,String> panModes =  new LinkedHashMap<String, String>();
  static { 
  panModes.put("NONE", "Disable");
  panModes.put("AP", "Access Pt");
  panModes.put("AP+", "Access Pt+");
  panModes.put("BT", "BT Client");
  panModes.put("USB", "USB Client");
  }

  public PanMode() {
    super("Pan.mode", "Mode", "%3$s", "NONE",panModes);
  }

}
