package lejos.ev3.menu.model;

import java.util.List;

import lejos.hardware.lcd.Image;

public interface Node {
  
  public Node addChild(Node child);
  
  public List<Node> getChildren();
  
  public Image getIcon();
  
  public String getLabel();
  
  public Node addDetail(Detail detail);
  
  public List<Detail> getDetails();
  
  public boolean hasSelectableDetails();
  
  public Detail getDetail(int index);

  public int getAlignment();
  
  public boolean hasChildren();

}
