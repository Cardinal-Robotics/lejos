package lejos.ev3.menu.viewer;

import java.util.ArrayList;
import java.util.List;

import lejos.ev3.menu.components.*;
import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.model.Model;
import lejos.ev3.menu.presenter.Detail;
import lejos.ev3.menu.presenter.MenuItem;
import lejos.hardware.Button;
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
  private int            visibleDetails  ;
  private List<MenuItem> navigationStack = new ArrayList<MenuItem>(0);
  private List<Integer>  navigationIndex = new ArrayList<Integer>(0);
  private MenuItem       top;
  private ScrollBar      bar;
  private int            nDetails;
  private boolean        suspended       = false;

  private TextPanel      statusBar;
  private CompoundPanel  title;
  private TextPanel      detailLine;
  private ImagePanel     selector;
  private Panel          detailPanel;
  private CompoundPanel  messagePanel;
  private Detail         currentDetail;
  private Panel          nodePanel;

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
    title.setWidth(canvas.getWidth());
    title.setHeight(24);
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
    visibleDetails = detailPanel.getHeight() / detailLine.getHeight();

    messagePanel = new CompoundPanel(Icons.HOUR_GLASS, "Wait\na\nsecond...");
    messagePanel.setBorders(15);
    messagePanel.setShadow(true);
  }

  public void run() {
    if (top == null)
      throw new RuntimeException("No menu structure specified");
    int current = 0;
    int last = 0;
    while (true) {
      current = Button.getButtons();
      if (current == 0 && last != 0 && !suspended) {
        // Process regular key handling for menu navigation
        switch (last) {
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
      if (suspended && current != 0 && current != last) {
        // handle special key combinations while menu is suspended
        switch (current) {
        case (Button.ID_ENTER + Button.ID_DOWN): {
          control.killProgram();
          resumeMenu();
          break;
        }
        }
      }
      last = current;
    }
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
    nDetails = details.size();
    if (nDetails > visibleDetails) {
//      bar = new ScrollBar(canvas, Config.DETAILS.right - 12, Config.DETAILS.y + 3, Config.DETAILS.height - 3, nDetails, visibleDetails,
//          iDetail);
    }
    paintDetails();
    selectFirstDetail();
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
      paintDetails();
    }
    if (iDetail > toprow + visibleDetails) {
      toprow = iDetail - visibleDetails;
      paintDetails();
    }
    paintSelector();
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
    detailPanel.paint();
    for (int i = 0; i <= visibleDetails; i++) {
      if (i + toprow < details.size()) {
        Detail d = details.get(i + toprow);
        detailLine.setMessage(d.toString());
        detailLine.setY(detailPanel.getY() + detailLine.getHeight() * i);
        detailLine.paint();
      }
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
    statusBar.paint();
    title.paint();
    paintDetails();
    paintSelector();
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
  

}
