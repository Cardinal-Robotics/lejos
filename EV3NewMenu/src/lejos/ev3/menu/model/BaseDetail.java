package lejos.ev3.menu.model;

import java.util.ArrayList;
import java.util.List;

import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.CommandMenu;
import lejos.ev3.menu.viewer.Config;
import lejos.ev3.menu.viewer.Editor;

public abstract class BaseDetail implements Detail {
  @Override
  
  public void runMenu(int x, int y) {
  }

  protected boolean editable = false;
  private List<String> menu;
  protected Editor editor;
  protected MenuControl control;
  String label ;





  @Override
  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public String getLabel() {
    return label;
  }

  public BaseDetail(MenuControl control) {
    this.control = control;
  }

  @Override
  public boolean isEditable() {
    return editable;
  }


  @Override
  public Detail addMenuItem(String label) {
    if (menu == null) menu = new ArrayList<String>();
    menu.add(label);
    return this;
  }

  @Override
  public void edit(int x, int y) {
  }

  @Override
  public boolean hasMenu() {
    
    return menu == null ? false : true;
  }

  @Override
  public int runMenu(int defaultItem, int x, int y) {
    if (!hasMenu()) throw new RuntimeException("No menu for this item");
    CommandMenu command = new CommandMenu(menu);
    return command.select(defaultItem, x + Config.DETAILS.font.stringWidth(label), y);
  }
  
  @Override
  public String toString() {
    return label;
  }
  
 
  

}
