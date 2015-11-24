package lejos.ev3.menu.model;


import lejos.ev3.menu.control.MenuControl;

public class DetailProgram extends BaseDetail {


  public DetailProgram(MenuControl control, String file) {
    super(control);
    this.label = file;
    this.addMenuItem("Run");
    this.addMenuItem("Debug");
    this.addMenuItem("Delete");
    this.addMenuItem("Set default");
    this.addMenuItem("Set autorun");
    this.addMenuItem("View output");
    this.addMenuItem("View error");
  }

  @Override
  public void runMenu( int x, int y) {
    switch(super.runMenu(0, x, y)) {
      case 0: {
        break;
      }
      case 1: {
        break;
      }
      case 2: {
        break;
      }
      case 3: {
        break;
      }
      case 4: {
        break;
      }
      case 5: {
        break;
      }
      case 6: {
        break;
      }
    };
  }

}
