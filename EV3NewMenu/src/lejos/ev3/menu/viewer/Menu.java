package lejos.ev3.menu.viewer;

import java.util.List;

import lejos.ev3.menu.presenter.MenuItem;
import lejos.hardware.lcd.Image;

/**
 * Represents a menu for leJOS
 * 
 * @author Aswin Bouwmeester
 *
 */
public interface Menu {
  /**
   * Selects the first child of the current menu.
   */
  public void selectChild();

  /**
   * Selects the parent menu item of the current menu
   */
  public void selectParent();

  /**
   * Displays the default message window
   */
  public void notifyOn();

  /**
   * Displays a customized message window
   * 
   * @param icon
   *          The image to display
   * @param message
   *          The text to display. Get multiple lines using a the line separator
   *          /n.
   */
  public void notifyOn(Image icon, String message);

  /**
   * Removes the displayed message window
   * 
   */
  public void notifyOff();

  /**
   * Sets the menu Sctructure
   */
  public void setMenu(MenuItem top);

  /**
   * Returns true is the select method on a detail is being executed and ths
   * detail has instructed the menu to suspend;
   * 
   * @return
   */
  public boolean isSuspended();

  /**
   * Instructs the menu to suspend. While suspended the menu does not write to
   * the display. It waits for the resume method or the "Kill program" key
   * combination to continue running.
   */
  public void suspendMenu();

  /**
   * Instructs the menu to continue running
   */
  public void resumeMenu();

  public void run();

  void selectNextSibling();

  void selectPreviousSibling();

  void selectNextDetail();

  void selectPreviousDetail();
  
  void repopulateParent();
  
  void repopulate();

  public void insertAndRun(List<MenuItem> children);

}
