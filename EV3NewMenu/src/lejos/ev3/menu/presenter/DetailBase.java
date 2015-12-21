package lejos.ev3.menu.presenter;

import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.viewer.Menu;

/**
 * Basic abstract implementation of a MenuDetail
 * 
 * @author Aswin Bouwmeester
 *
 */

public abstract class DetailBase implements MenuDetail {
  protected boolean isInitialized = false;
  protected static Control control;
  protected static Menu menu;
  // protected static Model model;
  protected boolean isSelectable  = false;

  public DetailBase() {
    if (control == null || menu == null) throw new RuntimeException("Menu Details can only be instantiated after both Menu and Control are set");
  }

  @Override
  public void needRefresh() {
    isInitialized = false;
  }

  @Override
  public boolean isSelectable() {
    return isSelectable;
  }

  /**
   * Used to initialize the menuDetail. <br>
   * MenuDetails are only initialized upon first display for performance and
   * synchronization
   * 
   */
  protected abstract void initialize();

  @Override
  public String toString() {
    if (!isInitialized) {
      initialize();
    }
    return null;
  }

  public static void setControl(Control c) {
    control = c;
  }

  public static void setMenu(Menu m) {
    menu = m;
  }

  
}
