package lejos.ev3.menu.model;


import lejos.ev3.menu.control.MenuControl;

public class DetailProgram extends BaseDetail {

  public DetailProgram(MenuControl control, String file, String ID) {
    super(control, "",file,"%s", Detail.TYPE_SELECTABLE);
    this.ID = ID;
  }
}
