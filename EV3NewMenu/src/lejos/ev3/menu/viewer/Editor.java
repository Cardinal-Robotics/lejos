package lejos.ev3.menu.viewer;

import lejos.ev3.menu.model.DetailNumericValue;
import lejos.ev3.menu.model.DetailStringValue;

/**
 * Defines editors to modify properties or settings of the leJOS VM. Editors
 * work in conjunction with DetailValue's
 * 
 * @author Aswin Bouwmeester
 *
 */
public interface Editor {

  /**
   * This method should be implemented by editors for numeric values
   * 
   * @param model
   */
  public void edit(DetailNumericValue model);

  /**
   * This method should be implemented by editors for character values
   * 
   * @param model
   */
  public void edit(DetailStringValue model);
}
