package lejos.ev3.menu.model;

import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.Icons;

public class NodeConnect extends BaseNode {

  public NodeConnect(MenuControl control) {
    super(control);
    setIcon(Icons.PAN);
    setLabel("Connect");
    this.selectableDetails = false;
    addDetail(new DetailWifi(control));
    addDetail(new DetailBT(control));
    addDetail(new DetailPAN(control));
  }
  
  public class DetailBT extends BaseDetail{
    public DetailBT(MenuControl control) {
      super(control);
      setLabel("Bluetooth: visible");
    }
  }

  public class DetailWifi extends BaseDetail{
    public DetailWifi(MenuControl control) {
      super(control);
      setLabel("IP: 192.168.1.21");
    }
  }
  
  public class DetailPAN extends BaseDetail{
    public DetailPAN(MenuControl control) {
      super(control);
      setLabel("IP: 10.1.1.1");
    }
  }


  
}
