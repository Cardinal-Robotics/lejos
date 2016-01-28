package lejos.ev3.menu.presenter;


public class BtForgetCommand extends BaseDetail {

  public BtForgetCommand( String entry) {
    super("FORGET", "Forget", "%3$s", "", true);
    this.value = entry;
    initialized = true;
  }
  
@Override
public void select() {
  model.execute(key, value);
  menu.repopulate();
}
}
 