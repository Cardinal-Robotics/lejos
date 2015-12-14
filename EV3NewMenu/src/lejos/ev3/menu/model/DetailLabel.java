package lejos.ev3.menu.model;

import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.viewer.Menu;

/**
 * Simplest implementation of a Menu Detail. Displays a non selectable and
 * immutable text
 * 
 * @author Aswin Bouwmeester
 *
 */
public class DetailLabel extends DetailBase {

  protected String label;

  /**
   * @param label
   *          The text to display
   */
  public DetailLabel(String label) {
    this(null, label);
  }

  public DetailLabel(Control control, String label) {
    super(control);
    this.label = label;
    isInitialized = true;
  }

  @Override
  protected void initialize() {
  }

  @Override
  public boolean isSelectable() {
    return super.isSelectable();
  }

  @Override
  public String toString() {
    return label;
  }

  @Override
  public void select(Menu menu) {
    // this should never happen
  }

}
