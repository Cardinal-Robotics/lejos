package lejos.ev3.menu.viewer;

import java.util.ArrayList;
import java.util.List;

import lejos.ev3.menu.components.*;
import lejos.ev3.menu.presenter.Detail;
import lejos.ev3.menu.presenter.MenuItem;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.Image;

/**
 * A menu implementation for leJOS EV3.
 * 
 * @author Aswin Bouwmeester
 *
 */
public class GraphicMenu implements Menu {
  // TODO: work on suspend
  

  private List<MenuItem>   siblings;
  private int              iNode           = -1;
  private MenuItem         currentNode;
  private GraphicsLCD      canvas          = LocalEV3.get().getGraphicsLCD();
  private List<Detail>     details;
  private int              toprow          = 0;
  private int              iDetail         = -1;
  private int              visibleDetails  = (Config.DETAILS.height) / (Config.DETAIL.height);
  private List<MenuItem>   navigationStack = new ArrayList<MenuItem>(0);
  private List<Integer>    navigationIndex = new ArrayList<Integer>(0);
  private MenuItem         top;
  private ScrollBar        bar;
  private int              nDetails;
  private boolean running = false ;
  private boolean suspended = false;
  
  
  
  
  private TextPanel statusBar ;
  private CompoundPanel title ;
  private TextPanel detailLine ;
  private ImagePanel selector;
  private Panel detailPanel;
  private CompoundPanel messagePanel; 

  /**
   * @param top
   *          The structure of the menu consisting of a single top level menu
   *          item and any number of child items.
   */
  public GraphicMenu() {
    statusBar = new TextPanel("Status bar", 0, 0);
    statusBar.setWidth(canvas.getWidth());
    statusBar.setFont(Fonts.Courier12);
    statusBar.setReverse(true);
    
    title = new CompoundPanel(Icons.EYE, "Title", 0, statusBar.getBottom()+1);
    title.setWidth(canvas.getWidth());
    title.setHeight(24);
    title.setFont(Fonts.Courier17);
    title.setBorders(Panel.BOTTOMBORDER);
    title.setTextAlign(GraphicsLCD.HCENTER + GraphicsLCD.VCENTER);
    
    detailPanel = new Panel(0, title.getBottom()+3,canvas.getWidth(), canvas.getHeight() - title.getBottom()-3);
    
    selector = new ImagePanel(Icons.ARROW_RIGHT);
    selector.setX(0);
    
    detailLine = new TextPanel ("Detail");
    detailLine.setX(selector.getRight() + 2);
    detailLine.setWidth(canvas.getWidth()-selector.getRight() - 2);
    detailLine.setFont(Fonts.Courier13);
    detailLine.setTextAlign(GraphicsLCD.LEFT + GraphicsLCD.TOP);
    visibleDetails = detailPanel.getHeight()   / detailLine.getHeight();
    
    messagePanel = new CompoundPanel(Icons.HOUR_GLASS, "Wait\na\nsecond...");
    messagePanel.setBorders(15);
    messagePanel.setShadow(true);

  }

  @Override
  public void setMenu(MenuItem top) {
    this.top = top;
    siblings = top.getChildren();
    selectNode(0);
  }

  
  
  public void run() {
    if (top == null) throw new RuntimeException("No menu specified.");
    running = true;
    paint();
    while (true) {
      switch (Button.waitForAnyPress()) {
      case Button.ID_LEFT: {
        if (iNode > 0)
          selectNode(iNode - 1);
        else
          selectNode(siblings.size() - 1);
        paint();
        break;
      }
      case Button.ID_RIGHT: {
        if (iNode < siblings.size() - 1)
          selectNode(iNode + 1);
        else
          selectNode(0);
        paint();
        break;
      }
      case Button.ID_DOWN: {
        if (currentNode.hasSelectableDetails()) {
          iDetail = findNext(iDetail);
          if (iDetail >= toprow + visibleDetails)
            toprow = iDetail - visibleDetails + 1;
          if (bar != null)
            bar.setToprow(toprow);
          paint();
        } else {
          if (toprow < details.size() - visibleDetails) {
            toprow++;
            if (bar != null)
              bar.setToprow(toprow);
            paint();
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
          paint();
        } else {
          if (toprow > 0) {
            toprow--;
            if (bar != null)
              bar.setToprow(toprow);
            paint();
          }
        }
        break;
      }
      case Button.ID_ENTER: {
        if (currentNode.hasSelectableDetails()) {
          Detail d = details.get(iDetail);
          int r = d.select();
          if (r==-1) selectParent();
          if (r==1) selectChild();
          paint();
        }
        break;
      }
      case Button.ID_ESCAPE: {
        selectParent();
        if (top == null) {
          running = false;
          return;
        }
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
    title.setMessage(currentNode.getLabel());
    title.setIcon(currentNode.getIcon());
    
    details = currentNode.getDetails();

      nDetails = details.size();
      if (currentNode.hasSelectableDetails())
        iDetail = findFirst();

      if (nDetails > visibleDetails) {
        bar = new ScrollBar(canvas, Config.DETAILS.right - 12, Config.DETAILS.y + 3, Config.DETAILS.height - 3, nDetails, visibleDetails,
            iDetail);
      }
 
  }

  private void paint() {
    canvas.clear();
    statusBar.paint();
    title.paint();
    for (int i = 0; i < visibleDetails; i++) {
      if (i + toprow < details.size()) {
        Detail d = details.get(i + toprow);
        detailLine.setMessage(d.toString());
        detailLine.setY(detailPanel.getY() + detailLine.getHeight() * i);
        detailLine.paint();
      }
    }

    if (iDetail != -1) {
      selector.setY((iDetail - toprow) * detailLine.getHeight() + title.getBottom()+2);
      selector.paint();
    }
    if (bar != null)
      bar.paint();
  }

  private int findPrevious(int current) {
    if (current == 0)
      return 0;
    for (int i = current - 1; i >= 0; i--) {
      Detail d = details.get(i);
      if (d.isSelectable())
        return i;
    }
    return current;
  }

  private int findNext(int current) {
    if (current == nDetails - 1)
      return current;
    for (int i = current + 1; i < nDetails; i++) {
      Detail d = details.get(i);
      if (d.isSelectable())
        return i;
    }
    return current;
  }

  private int findFirst() {
    for (int i = 0; i < nDetails; i++) {
      Detail d = details.get(i);
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
      paint();
    }

  }

  @Override
  public void selectParent() {
    if (!navigationStack.isEmpty()) {
      top = navigationStack.remove(navigationStack.size() - 1);
      siblings = top.getChildren();
      selectNode(navigationIndex.remove(navigationIndex.size() - 1));
      paint();
    } else
      top = null;
  }

  @Override
  public void notifyOn() {
    CompoundPanel p = new CompoundPanel(Icons.HOUR_GLASS, "Wait/na/nsecond...");
    p.paint();
  }

  @Override
  public void notifyOn(Image icon, String message) {
    CompoundPanel p = new CompoundPanel(icon, message);
    p.paint();
  }

  
  @Override
  public void notifyOff() {
    paint();
  }


  @Override
  public void needRefresh() {
    paint();
    
  }

  @Override
  public boolean isRunning() {
    return running;
  }

  @Override
  public boolean isSuspended() {
    return suspended;
  }

  @Override
  public void suspend() {
    suspended = true;
    messagePanel.paint();
    canvas.refresh();
    canvas.setAutoRefresh(false);
  }

  @Override
  public void resume() {
    suspended = false;
    canvas.setAutoRefresh(true);
    paint();
  }

  
  



}
