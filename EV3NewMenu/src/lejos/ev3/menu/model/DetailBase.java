package lejos.ev3.menu.model;

import lejos.ev3.menu.control.Control;

/**
 * Basic abstract implementation of a MenuDetail
 * 
 * @author Aswin Bouwmeester
 *
 */

public abstract class DetailBase implements MenuDetail {
  protected boolean isInitialized = false;
  protected Control control;
  protected boolean isSelectable  = false;

  public DetailBase(Control control) {
    this.control = control;
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

}
