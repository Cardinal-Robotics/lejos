package lejos.ev3.menu.viewer;

import java.nio.file.Path;

import lejos.ev3.menu.control.Control;

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
  
  public void notifyOn(String message);
  
  public void notifyOff();
  
  void execute(Control control, String command, Path path);
  
}
