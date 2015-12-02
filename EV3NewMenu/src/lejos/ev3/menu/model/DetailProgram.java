package lejos.ev3.menu.model;


import lejos.ev3.menu.control.MenuControl;

public class DetailProgram extends BaseDetail {


  public DetailProgram(MenuControl control, String file) {
    super(control, "",file,"%s", Detail.TYPE_SELECTABLE);
    this.addCommand("Run");
    this.addCommand("Debug");
    this.addCommand("Delete");
    this.addCommand("Set default");
    this.addCommand("Set autorun");
    this.addCommand("View output");
    this.addCommand("View error");
  }

  @Override
  public void executeCommand(int index) {
    switch (index) {
      default: {
        System.out.println("Command not implemented");
        //throw new RuntimeException("Command not implemented");
      }
    }
  }


}
