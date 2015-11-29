package lejos.ev3.menu.model;

import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.EditorNumeric;
import lejos.ev3.menu.viewer.Icons;

public class NodeSettings extends BaseNode {

  public NodeSettings(MenuControl control) {
    super(control);
    this.selectableDetails = true;
    setLabel("Settings");
    setIcon(Icons.INFO);
    addDetail(new DetailDefault(control));
    addDetail(new DetailAutorun(control));
    addDetail(new DetailSleep(control));
    addDetail(new DetailPowerOff(control));
  }
  
  public class DetailAutorun extends BasePropertyDetail {
    public DetailAutorun(MenuControl control) {
      super(control,"Autorun:  ", "lejos.default_autoRun","%s", "Not set");
      editable = false;
    }
  }

  public class DetailDefault extends BasePropertyDetail {
    public DetailDefault(MenuControl control) {
      super(control,"Default:  ", "lejos.default_program","%s", "Not set");
      editable = false;
    }
  }

  public class DetailSleep extends BasePropertyDetail {
    public DetailSleep(MenuControl control) {
      super(control,"Auto off: ", "lejos.sleep_time","%2d min", 0);
      editable = true;
      editor =new EditorNumeric().setLimits(0, 15).setIncrement(1).setFormat("%2d min");
    }
  }

  public class DetailPowerOff extends BasePropertyDetail {
    public DetailPowerOff(MenuControl control) {
      super(control,"Auto off: ", "lejos.autoPowerOff","%4d mV", 6500);
      editable = true;
      editor =new EditorNumeric().setLimits(0, 7500).setIncrement(250).setFormat("%4d mV");
    }
  }
}
