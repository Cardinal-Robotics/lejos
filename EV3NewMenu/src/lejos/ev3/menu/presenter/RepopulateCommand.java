package lejos.ev3.menu.presenter;

public class RepopulateCommand extends BaseDetail {

  public RepopulateCommand() {
    super("" , "<Scan>", "%2$s", " ", true);
  }

  @Override
  public void select() {
    super.select();
    menu.repopulate();
  }
  
  

}
