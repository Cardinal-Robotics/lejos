package lejos.ev3.menu.presenter;

public class BtRepopulate extends BaseDetail {

  public BtRepopulate() {
    super("" , "<Scan>", "%2$s", " ", true);
  }

  @Override
  public void select() {
    super.select();
    menu.repopulate();
  }
  
  

}
