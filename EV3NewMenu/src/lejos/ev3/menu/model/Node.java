package lejos.ev3.menu.model;

import java.util.List;

import lejos.hardware.lcd.Image;

/** The Node is the fundamental building block of the visible part of the lejos EV3 menu structure. It represents one item in menu.
 * A node consists of an Icon, a label, a number of details and a number of child nodes.
 * @author Aswin Bouwmeester
 *
 */
public interface Node {
  
  /** Adds a child node (submenu item) to this node
   * @param child
   * @return this
   */
  public Node addChild(Node child);
  
  /** Returns a list of all the child nodes (sub menu items)
   * @return
   */
  public List<Node> getChildren();
  
  /** Returns the icon for this node
   * @return
   */
  public Image getIcon();
  
  /** Returns the label for this nod
   * @return
   */
  public String getLabel();
  
  /** Adds a Detail to this node
   * @param detail
   * @return this
   */
  public Node addDetail(Detail detail);
  
  /** Returns a list of all the details of this node
   * @return
   */
  public List<Detail> getDetails();
  
  /**  Tests wheather at least one of the details can be selected. Selectable details are either Details that can be edited or represent a command. 
   * @return
   */
  public boolean hasSelectableDetails();
  
  /** Returns the Detail with corresponding index nu ber
   * @param index
   * @return
   */
  public Detail getDetail(int index);

  public int getAlignment();
  
  /** Returns true if this nodehas children (a submenuu)
   * @return
   */
  public boolean hasChildren();

  /** Sets the label for this node
   * @param format
   */
  public void setLabel(String format);

  /** Sets the ID for this node. The ID represent the item (most often a file) where the menu is active on 
   * @param id
   */
  public void setID(String id);

  public void execute(String command);

}
