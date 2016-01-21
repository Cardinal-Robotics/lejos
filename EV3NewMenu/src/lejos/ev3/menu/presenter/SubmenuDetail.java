package lejos.ev3.menu.presenter;

import java.util.ArrayList;
import java.util.List;


public class SubmenuDetail extends BaseDetail {

  private List<MenuItem> children;
  
  public SubmenuDetail(String label) {
    super("", label, "%2$s", "");
    children = new ArrayList<MenuItem>();
    selectable = false;
    initialized = true;
  }
  

  public SubmenuDetail(String label, List<MenuItem> subMenu) {
    super("", label, "%2$s", "");
    this.children = subMenu;
    selectable = true;
    initialized = true;
  }

  @Override
  public void select() {
    menu.insertAndRun(children);
  }
  

  public SubmenuDetail addChild(MenuItem child) {
    children.add(child);
    selectable = true;
    return this;
  }
  
  

}
