package lejos.ev3.menu.presenter;

import java.util.ArrayList;
import java.util.List;

import lejos.ev3.menu.components.Icons;
import lejos.ev3.menu.components.Viewer;
import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.model.Model;
import lejos.ev3.menu.model.ModelListener;
import lejos.ev3.menu.viewer.Menu;
import lejos.hardware.lcd.Image;

/**
 * A basic implementation of a menu item
 * 
 * @author Aswin Bouwmeester
 *
 */
public class ItemBase implements MenuItem, ModelListener {

  private Image            icon;
  protected List<MenuItem> children           = new ArrayList<MenuItem>();
  private List<Detail>     details            = new ArrayList<Detail>();
  private String           label;
  protected boolean        selectableDetails  = false;
  protected boolean        markedForExecution = false;
  protected String         script;
  protected boolean          populated        = false;
  protected String key;


  protected static Control control;
  protected static Menu    menu;
  protected static Model   model;

  public static void setEnvironment(Control c, Model m, Menu m3) {
    control = c;
    model =m;
    menu =m3;
  }

  /**
   * @param control
   *          The control that provides access to the leJOS VM from this menu
   *          item
   * @param label
   *          The title of this manu item
   * @param icon
   *          The icon of this menu item
   */
  public ItemBase(String label, Image icon) {
    if (control == null || menu == null)
      throw new RuntimeException("Menu Items can only be instantiated after both Menu and Control are set");
    this.label = label;
    this.icon = icon;
  }

  public ItemBase(String label) {
    this(label, Icons.DEFAULT);
  }
  
  protected void populate() {
    populated =true;
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
  public List<Detail> getDetails() {
    if (!populated) populate();
    return details;
  }

  @Override
  public MenuItem addDetail(Detail detail) {
    details.add(detail);
    if (detail.isSelectable())
      selectableDetails = true;
    return this;
  }

  @Override
  public boolean hasSelectableDetails() {
    if (!populated) populate();
    return selectableDetails;
  }

  @Override
  public Detail getDetail(int index) {
    if (!populated) populate();
    if (index >= details.size())
      throw new IndexOutOfBoundsException();
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
    if (!populated) populate();
    return details.isEmpty() ? false : true;
  }

  @Override
  public void removeChildren() {
    children.clear();

  }

  @Override
  public void removeDetails() {
    details.clear();
    populated = true;
  }

  public static void setControl(Control c) {
    control = c;
  }

  public static void setMenu(Menu m) {
    menu = m;
  }

  @Override
  public void setScript(String path) {
    this.script = path;

  }

  @Override
  public String getScript() {
    return script;
  }

  @Override
  public boolean hasScript() {
    return (script != null);

  }

  @Override
  public void markScriptForExecution() {
    markedForExecution = true;

  }

  @Override
  public void conditionallyExecuteScript() {
    if (hasScript() && markedForExecution) {
      menu.suspendMenu();
      List<String> output = control.runScript(script);
      if (output != null)
        Viewer.view(output);
      markedForExecution = false;
      menu.resumeMenu();
    }
  }

  @Override
  public void repopulate() {
    populated = false;
  }

protected void clearDetails() {
  details.clear();
}

@Override
public void keyChanged(String key, String value) {
}

@Override
public void listChanged(String list, String parameter) {
  if (list.equals(key)) 
      this.populated=false; 
}

}
