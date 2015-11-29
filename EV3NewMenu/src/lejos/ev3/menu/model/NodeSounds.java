package lejos.ev3.menu.model;

import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.EditorNumeric;
import lejos.ev3.menu.viewer.Icons;

public class NodeSounds extends BaseNode {

  public NodeSounds(MenuControl control) {
    super(control);
    setIcon(Icons.SOUND);
    setLabel("Sound");
    this.selectableDetails = true;
    addDetail(new DetailVolume(control));
    addDetail(new DetailKeyVolume(control));
    addDetail(new DetailKeyFreq(control));
    addDetail(new DetailKeyLength(control));
  }
  
  
  public class DetailKeyFreq extends BasePropertyDetail {
    public DetailKeyFreq(MenuControl control) {
      super(control,"Key freq:  ", "lejos.keyclick_frequency","%4d", 1000);
      editor =new EditorNumeric().setLimits(200, 2000).setIncrement(100).setFormat("%4d");
    }
  }

  public class DetailVolume extends BasePropertyDetail {
    public DetailVolume(MenuControl control) {
      super(control,"Volume:    ", "lejos.volume","%3d", 80);
      editor =new EditorNumeric().setLimits(0, 100).setIncrement(10).setFormat("%3d");
    }
  }

  public class DetailKeyVolume extends BasePropertyDetail {
    public DetailKeyVolume(MenuControl control) {
      super(control,"Key volume:", "lejos.keyclick_volume","%3d", 80);
      editor =new EditorNumeric().setLimits(0, 100).setIncrement(10).setFormat("%3d");
    }
  }
  
  public class DetailKeyLength extends BasePropertyDetail {
    public DetailKeyLength(MenuControl control) {
      super(control,"Key length:", "lejos.keyclick_length", "%3d", 5);
      editor =new EditorNumeric().setLimits(0, 100).setIncrement(10).setFormat("%3d");
    }
  }
  



}
