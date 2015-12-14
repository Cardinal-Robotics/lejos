package lejos.ev3.menu.viewer;

import java.util.ArrayList;
import java.util.List;

import lejos.ev3.menu.model.MenuDetail;
import lejos.ev3.menu.model.MenuItem;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;

/**
 * A menu implementation for leJOS EV3.
 * 
 * @author Aswin Bouwmeester
 *
 */
public class GraphicMenu implements Menu {

  private List<MenuItem>   siblings;
  private int              iNode           = -1;
  private MenuItem         currentNode;
  private GraphicsLCD      canvas          = LocalEV3.get().getGraphicsLCD();
  private List<MenuDetail> details;
  private int              toprow          = 0;
  private int              iDetail         = -1;
  private int              visibleDetails  = (Config.DETAILS.height) / (Config.DETAIL.height);
  private List<MenuItem>   navigationStack = new ArrayList<MenuItem>(0);
  private List<Integer>    navigationIndex = new ArrayList<Integer>(0);
  private MenuItem         top;
  private ScrollBar        bar;
  private int              nDetails;

  /**
   * @param top
   *          The structure of the menu consisting of a single top level menu
   *          item and any number of child items.
   */
  public GraphicMenu(MenuItem top) {
    this.top = top;
    siblings = top.getChildren();
    selectNode(0);
  }

  public void run() {
    draw();
    while (true) {
      switch (Button.waitForAnyPress()) {
      case Button.ID_LEFT: {
        if (iNode > 0)
          selectNode(iNode - 1);
        else
          selectNode(siblings.size() - 1);
        draw();
        break;
      }
      case Button.ID_RIGHT: {
        if (iNode < siblings.size() - 1)
          selectNode(iNode + 1);
        else
          selectNode(0);
        draw();
        break;
      }
      case Button.ID_DOWN: {
        if (currentNode.hasSelectableDetails()) {
          iDetail = findNext(iDetail);
          if (iDetail >= toprow + visibleDetails)
            toprow = iDetail - visibleDetails + 1;
          if (bar != null)
            bar.setToprow(toprow);
          draw();
        } else {
          if (toprow < details.size() - visibleDetails) {
            toprow++;
            if (bar != null)
              bar.setToprow(toprow);
            draw();
          }
        }
        break;
      }
      case Button.ID_UP: {
        if (currentNode.hasSelectableDetails()) {
          iDetail = findPrevious(iDetail);
          if (iDetail < toprow)
            toprow = iDetail;
          if (bar != null)
            bar.setToprow(toprow);
          draw();
        } else {
          if (toprow > 0) {
            toprow--;
            if (bar != null)
              bar.setToprow(toprow);
            draw();
          }
        }
        break;
      }
      case Button.ID_ENTER: {
        if (currentNode.hasSelectableDetails()) {
          MenuDetail d = details.get(iDetail);
          d.select(this);
          draw();
        }
        break;
      }
      case Button.ID_ESCAPE: {
        selectParent();
        if (top == null)
          return;
      }
        break;
      }
    }
  }

  private void selectNode(int i) {
    toprow = 0;
    iDetail = -1;
    iNode = i;
    bar = null;

    currentNode = siblings.get(iNode);
    details = currentNode.getDetails();
    Config.ICON.icon = currentNode.getIcon();
    Config.TITLE.label = currentNode.getLabel();

    if (details != null) {
      nDetails = details.size();
      if (currentNode.hasSelectableDetails())
        iDetail = findFirst();

      if (nDetails > visibleDetails) {
        bar = new ScrollBar(canvas, Config.DETAILS.right - 12, Config.DETAILS.y + 3, Config.DETAILS.height - 3, nDetails, visibleDetails,
            iDetail);
      }
    }

  }

  private void draw() {
    canvas.clear();
    Config.STATUS.draw(canvas);
    Config.STATUSTEXT.label = "8.0   Brick1";
    Config.STATUSTEXT.draw(canvas);
    Config.NODE.draw(canvas);
    Config.ICON.draw(canvas);
    Config.TITLE.draw(canvas);
    ;
    Config.DETAILS.draw(canvas);

    for (int i = 0; i < visibleDetails; i++) {
      if (i + toprow < details.size()) {
        MenuDetail d = details.get(i + toprow);
        Config.DETAIL.label = d.toString();
        Config.DETAIL.draw(canvas, 0, i * Config.DETAIL.height);
      }
    }

    if (iDetail != -1)
      Config.ICONSELECT.draw(canvas, 0, (iDetail - toprow) * Config.DETAIL.height);

    if (bar != null)
      bar.draw();

  }

  private int findPrevious(int current) {
    if (current == 0)
      return 0;
    for (int i = current - 1; i >= 0; i--) {
      MenuDetail d = details.get(i);
      if (d.isSelectable())
        return i;
    }
    return current;
  }

  private int findNext(int current) {
    if (current == nDetails - 1)
      return current;
    for (int i = current + 1; i < nDetails; i++) {
      MenuDetail d = details.get(i);
      if (d.isSelectable())
        return i;
    }
    return current;
  }

  private int findFirst() {
    for (int i = 0; i < nDetails; i++) {
      MenuDetail d = details.get(i);
      if (d.isSelectable())
        return i;
    }
    return -1;
  }

  @Override
  public void selectChild() {
    if (currentNode.hasChildren()) {
      navigationStack.add(top);
      navigationIndex.add(iNode);
      top = currentNode;
      siblings = top.getChildren();
      selectNode(0);
      draw();
    }

  }

  @Override
  public void selectParent() {
    if (!navigationStack.isEmpty()) {
      top = navigationStack.remove(navigationStack.size() - 1);
      siblings = top.getChildren();
      selectNode(navigationIndex.remove(navigationIndex.size() - 1));
      draw();
    } else
      top = null;
  }

}
