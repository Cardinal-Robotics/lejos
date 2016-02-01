package lejos.ev3.menu.viewer;

import java.util.ArrayList;
import java.util.List;

import lejos.ev3.menu.components.*;
import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.model.Model;
import lejos.ev3.menu.presenter.Detail;
import lejos.ev3.menu.presenter.MenuItem;
import lejos.hardware.Button;
import lejos.hardware.Keys;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.Image;
import lejos.utility.Delay;

/**
 * A menu implementation for leJOS EV3.
 * 
 * @author Aswin Bouwmeester
 *
 */
public class GraphicMenu implements Menu {
  // TODO: work on suspend
  // TODO: work on scrollbar
  // TODO: work on system exit

  private static Control control;
  private static Model   model;
  private List<MenuItem> siblings;
  private int            iNode           = -1;
  private MenuItem       currentNode;
  private GraphicsLCD    canvas          = LocalEV3.get().getGraphicsLCD();
  private List<Detail>   details;
  private int            toprow          = 0;
  private int            iDetail         = -1;
  private int            lastDetail =0;
  private List<MenuItem> navigationStack = new ArrayList<MenuItem>(0);
  private List<Integer>  navigationIndex = new ArrayList<Integer>(0);
  private MenuItem       top;
  private ScrollBar      bar;
  private boolean        suspended       = false;
  private boolean        idle            = false;

  private TextPanel      statusBar;
  private CompoundPanel  title;
  private TextPanel      detailLine;
  private ImagePanel     selector;
  private Panel          detailPanel;
  private CompoundPanel  messagePanel;
  private Detail         currentDetail;
  private Panel          nodePanel;
  
  private Refresh        refresh;
  private int            refreshInterval = 3000;


  public static void setEnvironment(Control c, Model m) {
    control = c;
    model = m;
  }

  public GraphicMenu() {
    statusBar = new TextPanel("Status bar", 0, 0);
    statusBar.setWidth(canvas.getWidth());
    statusBar.setFont(Fonts.Courier12);
    statusBar.setReverse(true);
    
    nodePanel = new Panel(0,statusBar.getBottom() + 1, canvas.getWidth(), canvas.getHeight() -  statusBar.getBottom() - 1);

    title = new CompoundPanel(Icons.EYE, "Title", 0, statusBar.getBottom() + 1);
    title = new CompoundPanel(Icons.EYE, "Title", 0, 0);
    title.setWidth(canvas.getWidth());
    title.setHeight(24);
    title.setHeight(33);
    title.setFont(Fonts.Courier17);
    title.setBorders(Panel.BOTTOMBORDER);
    title.setTextAlign(GraphicsLCD.HCENTER + GraphicsLCD.VCENTER);

    detailPanel = new Panel(0, title.getBottom() + 3, canvas.getWidth(), canvas.getHeight() - title.getBottom() - 3);

    selector = new ImagePanel(Icons.ARROW_RIGHT);
    selector.setX(0);

    detailLine = new TextPanel("Detail");
    detailLine.setX(selector.getRight() + 2);
    detailLine.setWidth(canvas.getWidth() - selector.getRight() - 2);
    detailLine.setFont(Fonts.Courier13);
    detailLine.setTextAlign(GraphicsLCD.LEFT + GraphicsLCD.TOP);

    messagePanel = new CompoundPanel(Icons.HOUR_GLASS, "Wait\na\nsecond...");
    messagePanel.setBorders(15);
    messagePanel.setShadow(true);
    
    refresh = new Refresh();
    refresh.setDaemon(true);
    refresh.start();
  }

  public void run() {
    if (top == null)
      throw new RuntimeException("No menu structure specified");
    int current = 0;
    while (true) {
      idle = true;
      current = UI.getUI(Keys.ID_DOWN + Keys.ID_UP, 1000, 1000);
      idle = false;
      switch (current) {
        case Button.ID_LEFT: {
          selectPreviousSibling();
          break;
        }
        case Button.ID_RIGHT: {
          selectNextSibling();
          break;
        }
        case Button.ID_UP: {
          selectPreviousDetail();
          break;
        }
        case Button.ID_DOWN: {
          selectNextDetail();
          break;
        }
        case Button.ID_ESCAPE: {
          selectParent();
          break;
        }
        case Button.ID_ENTER: {
          activate();
          break;
        }
        }
      }
//      if (suspended && current != 0 && current != last) {
//        // handle special key combinations while menu is suspended
//        switch (current) {
//        case (Button.ID_ENTER + Button.ID_DOWN): {
//          control.killProgram();
//          resumeMenu();
//          break;
//        }
//        }
//      }
//      last = current;
//    }
  }

  @Override
  public void setMenu(MenuItem top) {
    this.top = top;
    siblings = top.getChildren();
    selectNode(0);
  }

  @Override
  public void selectNextSibling() {
    if (iNode < siblings.size() - 1)
      selectNode(iNode + 1);
    else
      selectNode(0);
  }

  @Override
  public void selectPreviousSibling() {
    if (iNode > 0)
      selectNode(iNode - 1);
    else
      selectNode(siblings.size()-1);
  }

  @Override
  public void selectChild() {
    if (currentNode.hasChildren()) {
      navigationStack.add(top);
      navigationIndex.add(iNode);
      top = currentNode;
      siblings = top.getChildren();
      selectNode(0);
    }
  }

  @Override
  public void selectParent() {
    if (!navigationStack.isEmpty()) {
      top = navigationStack.remove(navigationStack.size() - 1);
      siblings = top.getChildren();
      selectNode(navigationIndex.remove(navigationIndex.size() - 1));
    } else {
      // TODO: Let the control shut down the brick
      System.exit(0);
      //control.shutdown();
    }
  }

  private void selectNode(int i) {
    iNode = i;
    currentNode = siblings.get(iNode);
    title.setMessage(currentNode.getLabel());
    title.setIcon(currentNode.getIcon());
    nodePanel.clear();
    title.paint();

    toprow = 0;
    bar = null;
    details = currentNode.getDetails();
    selectFirstDetail();
    paintDetails();
  }

  @Override
  public void selectNextDetail() {
    if (!currentNode.hasSelectableDetails() || iDetail == details.size() -1)
      return;
    for (int i = iDetail + 1; i < details.size(); i++) {
      Detail d = details.get(i);
      if (d.isSelectable()) {
        selectDetail(i);
        return;
      }
    }
  }

  @Override
  public void selectPreviousDetail() {
    if (!currentNode.hasSelectableDetails())
      return;
    for (int i = iDetail - 1; i >= 0; i--) {
      Detail d = details.get(i);
      if (d.isSelectable()) {
        selectDetail(i);
        return;
      }
    }
  }

  private void selectFirstDetail() {
    iDetail = -1;
    currentDetail = null;
    if (!currentNode.hasSelectableDetails())
      return;
    for (int i = 0; i < details.size(); i++) {
      Detail d = details.get(i);
      if (d.isSelectable()) {
        selectDetail(i);
        return;
      }
    }
  }

  private void selectDetail(int i) {
    iDetail = i;
    currentDetail = details.get(iDetail);
    if (iDetail < toprow) {
      toprow = iDetail;
    }
    if (iDetail > lastDetail) {
      toprow ++;
    }
    paintDetails();
  }

  private void activate() {
    if (currentNode.hasSelectableDetails()) {
      activateDetail();
      paintAll();
    }
    else
      selectChild();
  }

  private void activateDetail() {
    currentDetail.select();
  }

  private void paintDetails() {
    selector.clear();
    detailPanel.paint();
    int y = detailPanel.getY();
    int i = 0;
    while(y + detailLine.getFont().getHeight() < detailPanel.getBottom() && i + toprow < details.size()) {
        Detail d = details.get(i + toprow);
        detailLine.setMessage(split(d.toString()));
        detailLine.setY(y);
        detailLine.paint();
        y += detailLine.getHeight();
        if (d==currentDetail) {
          selector.setY(detailLine.getVCenter()-selector.getHeight()/2);
          selector.paint();
        }
        lastDetail = i + toprow;
        i++;
    }
  }
  
  private String[] split(String text) {
    int max = detailLine.getWidth() / detailLine.getFont().width;
    if (text.length() <= max) {
      return new String[]{text}; 
    }
    else {
      max=max-1;
      int lines = (int) Math.ceil((double)text.length() / (double)(max));
      String[] ret = new String[lines];
      for (int i =0;i<lines;i++) {
        if (i==0)
          ret[i]=text.substring(0, max+1);
        else if (i<lines-1)
          ret[i]=" "+ text.substring(i*max+1, (i+1) * max+1);
        else 
          ret[i]=" "+ text.substring(i*max+1);
      }
      return ret;
    }
    
  }

  private void paintSelector() {
    selector.clear();
    if (currentDetail != null) {
      selector.setY((iDetail - toprow) * detailLine.getHeight() + title.getBottom() + 2);
      selector.paint();
    }
  }

  private void paintAll() {
    canvas.clear();
//    statusBar.paint();
    title.paint();
    paintDetails();
    //paintSelector();
    if (bar != null)
      bar.paint();
  }

  @Override
  public void notifyOn() {
    CompoundPanel p = new CompoundPanel(Icons.HOUR_GLASS, "Wait/na/nsecond...");
    p.paint();
  }

  @Override
  public void notifyOn(Image icon, String message) {
    CompoundPanel p = new CompoundPanel(icon, message);
    p.setBorders(15);
    p.setShadow(true);
    p.paint();
  }

  @Override
  public void notifyOff() {
    paintAll();
  }
  
  @Override
  public boolean dialog(String text, int buttons) {
    return Dialog.display(text, buttons);
  }

  @Override
  public boolean isSuspended() {
    return suspended;
  }

  @Override
  public void suspendMenu() {
    suspended = true;
    messagePanel.paint();
    canvas.refresh();
    canvas.setAutoRefresh(false);
  }

  @Override
  public void resumeMenu() {
    suspended = false;
    canvas.setAutoRefresh(true);
    paintAll();
  }

  @Override
  public void repopulateParent() {
    top.repopulate();
  }
  
  @Override
  public void repopulate() {
    currentNode.repopulate();
    selectNode(iNode);
  }

  @Override
  public void insertAndRun(List<MenuItem> subMenu) {
    if (!currentNode.hasChildren()) {
      navigationStack.add(top);
      navigationIndex.add(iNode);
      top = currentNode;
      siblings = subMenu;
      selectNode(0);
    } 
    else 
      throw new RuntimeException("Menu item already has children");
    
  }

  @Override
  public void refreshDetail(Detail detail) {
    if (suspended || !idle) return;
    for (Detail d : this.details) {
      if (d==detail) {
        paintDetails();
        paintSelector();
        return;
      }
    }
  }
  
  class Refresh extends Thread {
    long lastTime = System.currentTimeMillis();
           
    @Override
    public void run(){  
      // TODO: Make thread safe
      while (true) {
        Delay.msDelay(refreshInterval);
        if (idle && !suspended) {
          for (Detail detail : details)
            if (detail.isAutoFefresh() || !detail.isInitialized()) {
              paintDetails();
              paintSelector();
              break;
            }
        }
      }
      
    }
}

  
}
