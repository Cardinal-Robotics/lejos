package lejos.ev3.menu.model;

import lejos.ev3.menu.viewer.Menu;

/**
 * Represents a detail line in a menu screen. Details can be a label or a
 * property or setting
 * 
 * @author Aswin Bouwmeester
 *
 */
public interface MenuDetail {

  /**
   * Returns true it the detail can be selected by the user
   * 
   * @return
   */
  public boolean isSelectable();

  /**
   * Called after the user has selected the Detail. Functionallity depends on
   * the type of detail.
   * 
   * @param menu
   */
  public void select(Menu menu);

  /**
   * To be called if the Detail should be refreshed as the data it displays has
   * changed
   * 
   */
  public void needRefresh();

  /**
   * Returns the text to display in the menu
   * 
   * @return
   */
  public String toString();

}
