package lejos.ev3.menu.model;

import java.util.ArrayList;
import java.util.List;

import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.viewer.Icons;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.Image;

public class BaseNode implements Node{
  
  private Image icon;
  
  private List<Node> children;
  
  private List<Detail> details;
  
  private String label;
  
  private MenuControl control;
  
  protected int alignment = GraphicsLCD.HCENTER | GraphicsLCD.TOP;
  
  protected boolean selectableDetails = false;
  
  public BaseNode(MenuControl control, String label, Image icon) {
    this.control = control;
    this.label = label;
    this.icon = icon;
  }

  @Override
  public int getAlignment() {
    return alignment;
  }



  public BaseNode(MenuControl control, String label) {
    this(control, label, Icons.DEFAULT);
  }
  

  
  @Override
  public Node addChild(Node child) {
    if (children == null) children = new ArrayList<Node>();
    children.add(child);
    return this;
  }

  @Override
  public List<Node> getChildren() {
    return children;
  }
  
  protected void setIcon(Image icon) {
    this.icon = icon;
  }

  @Override
  public Image getIcon() {
    return icon;
  }

  protected void setLabel(String label) {
    this.label = label;
  }
  
  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public List<Detail> getDetails() {
    return details;
  }

  
  @Override
  public Node addDetail(Detail detail) {
    if (details == null) details = new ArrayList<Detail>();
    details.add(detail);
    if (detail.isEditable() | detail.isSelectable()) selectableDetails = true;
    return this;
  }



  @Override
  public boolean hasSelectableDetails() {
    return selectableDetails;
  }



  @Override
  public Detail getDetail(int index) {
    return details.get(index);
  }

  @Override
  public String toString() {
    return label;
  }

  @Override
  public boolean hasChildren() {
    return children == null ? false : true;
  }
  
}
