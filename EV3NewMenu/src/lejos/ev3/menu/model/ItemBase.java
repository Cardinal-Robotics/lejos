package lejos.ev3.menu.model;

import java.util.ArrayList;
import java.util.List;

import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.viewer.Icons;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.Image;

/**
 * A basic implementation of a menu item
 * 
 * @author Aswin Bouwmeester
 *
 */
public class ItemBase implements MenuItem {

  private Image            icon;

  protected List<MenuItem> children = new ArrayList<MenuItem>();

  private List<MenuDetail> details  = new ArrayList<MenuDetail>();

  private String           label;

  protected Control        control;

  protected boolean        selectableDetails = false;

  /**
   * @param control
   *          The control that provides access to the leJOS VM from this menu
   *          item
   * @param label
   *          The title of this manu item
   * @param icon
   *          The icon of this menu item
   */
  public ItemBase(Control control, String label, Image icon) {
    this.control = control;
    this.label = label;
    this.icon = icon;
  }

  public ItemBase(Control control, String label) {
    this(control, label, Icons.DEFAULT);
  }

  @Override
  public MenuItem addChild(MenuItem child) {
    children.add(child);
    return this;
  }

  @Override
  public List<MenuItem> getChildren() {
    return children;
  }

  protected void setIcon(Image icon) {
    this.icon = icon;
  }

  @Override
  public Image getIcon() {
    return icon;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public List<MenuDetail> getDetails() {
    return details;
  }

  @Override
  public MenuItem addDetail(MenuDetail detail) {
    details.add(detail);
    if (detail.isSelectable())
      selectableDetails = true;
    return this;
  }

  @Override
  public boolean hasSelectableDetails() {
    return selectableDetails;
  }

  @Override
  public MenuDetail getDetail(int index) {
    if (index >= details.size()) throw new IndexOutOfBoundsException();
    return details.get(index);
  }

  @Override
  public String toString() {
    return label;
  }

  @Override
  public boolean hasChildren() {
    return children.isEmpty() ? false : true;
  }

  @Override
  public boolean hasDetails() {
    return details.isEmpty() ? false : true;
  }

  @Override
  public void removeChildren() {
    children.clear();
    
  }

  @Override
  public void removeDetails() {
    details.clear();
    
  }
}
