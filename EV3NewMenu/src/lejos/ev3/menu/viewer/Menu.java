package lejos.ev3.menu.viewer;

import java.util.ArrayList;
import java.util.List;

import lejos.ev3.menu.model.Detail;
import lejos.ev3.menu.model.Node;
import lejos.ev3.menu.viewer.Config.Panel;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.CustomFont;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.Image;

public class Menu {

  private List<Node>   nodes;
  private int          selectedIndex  = -1;
  private Node         selectedNode;
  private GraphicsLCD  g              = LocalEV3.get().getGraphicsLCD();
  private List<Detail> details;
  private int          toprow         = 0;
  private int          selectedDetail = 0;
  private int          nrows          = (128 - Config.DETAILS.y) / Config.DETAILS.height;  
  private List<Node>   navigationStack = new ArrayList<Node>(0);
  private List<Integer>    navigationIndex = new ArrayList<Integer>(0);
  private Node top;
  
  public Menu(Node top) {
    this.top = top;
    nodes = top.getChildren();
    selectNode(0);
  }
  

  public void run() {
    selectNode(0);
    draw();
    while(true) {  
      switch (Button.waitForAnyPress()) {
      case Button.ID_LEFT: {
        if (selectedIndex >0) 
          selectNode(selectedIndex-1);
        else 
          selectNode(nodes.size()-1);
        draw();
        break;
      }
      case Button.ID_RIGHT: {
        if (selectedIndex <  nodes.size()-1) 
          selectNode(selectedIndex+1);
        else
          selectNode(0);
        draw();
        break;
      }
      case Button.ID_DOWN: {
        if (selectedNode.hasSelectableDetails()) {
          if (selectedDetail < details.size() - 1) {
            selectedDetail++;
            if (selectedDetail >= toprow + nrows )
              toprow = selectedDetail - nrows + 1;
            draw();
          }
        } else {
          if (toprow < details.size() - nrows) {
            toprow++;
            draw();
          } 
        }
        break;
      }
      case Button.ID_UP: {
        if (selectedNode.hasSelectableDetails()) {
          if (selectedDetail > 0) {
            selectedDetail--;
            if (selectedDetail < toprow)
              toprow = selectedDetail;
            draw();
          }
        } else {
          if (toprow > 0) {
            toprow--;
            draw();
          }
        }
        break;
      }
      case Button.ID_ENTER: {
        if (selectedNode.hasChildren()){
          navigationStack.add(top);
          navigationIndex.add(selectedIndex);
          top = selectedNode;
          nodes = top.getChildren();
          selectNode(0);
        }
        else {
          Detail detail = details.get(selectedDetail);
          if (detail.hasMenu()) {
            detail.runMenu( Config.DETAILS.x, Config.DETAILS.y + (selectedDetail - toprow) * Config.DETAILS.height);
          }
          if (detail.isEditable()) {
            detail.edit(Config.DETAILS.x, Config.DETAILS.y + (selectedDetail - toprow) * Config.DETAILS.height);
          }
        };
        draw();
        break;
      }
      case Button.ID_ESCAPE: {
        if (navigationStack.isEmpty()) return;
        top = navigationStack.remove(navigationStack.size()-1);
        nodes = top.getChildren();
        selectNode(navigationIndex.remove(navigationIndex.size()-1));
        draw();
      }
    }
    }
  }

  private void selectNode(int i) {
    selectedIndex = i;
    selectedNode = nodes.get(selectedIndex);
    details = selectedNode.getDetails();
    toprow = 0;
    selectedDetail = 0;
  }

  private void draw() {
    g.clear();
    drawStatus();
    drawTitleBar();
    drawDetails();
  }
  
  private void drawStatus() {
    g.setFont(Config.STATUS.font);
    g.drawString("Status bar", Config.STATUS.x, Config.STATUS.y, Config.STATUS.alignment);
    g.setFont(Font.getDefaultFont());
  }
  
  private void drawTitleBar() {
    g.drawRegion(Config.ICONLEFT.icon, 0, 0,  Config.ICONLEFT.width, Config.ICONLEFT.height, 0,  Config.ICONLEFT.x, Config.ICONLEFT.y,Config.ICONLEFT.alignment);
    g.drawRegion(Config.ICONRIGHT.icon, 0, 0,  Config.ICONRIGHT.width, Config.ICONRIGHT.height, 0,  Config.ICONRIGHT.x, Config.ICONRIGHT.y,Config.ICONRIGHT.alignment);
    g.drawRegion(selectedNode.getIcon(), 0, 0,  Config.ICONNODE.width, Config.ICONNODE.height, 0,  Config.ICONNODE.x, Config.ICONNODE.y,Config.ICONNODE.alignment);
    g.setFont(Config.TITLE.font);
    g.drawString(selectedNode.toString(), Config.TITLE.x, Config.TITLE.y, Config.TITLE.alignment);
    g.setFont(Font.getDefaultFont());
  }
  
  private void drawDetails() {
    g.setFont(Config.DETAILS.font);
    for (int i =0; i< nrows; i++) {
      if (i+toprow < details.size())
        g.drawString(details.get(i+toprow).toString(), Config.DETAILS.x, Config.DETAILS.y + i * Config.DETAILS.height , Config.DETAILS.alignment);
    }
    g.setFont(Font.getDefaultFont());

    if (toprow != 0) 
      g.drawRegion(Config.ICONUP.icon, 0, 0,  Config.ICONUP.width, Config.ICONUP.height, 0,  Config.ICONUP.x, Config.ICONUP.y,Config.ICONUP.alignment);
    if (details.size() > toprow + nrows ) 
      g.drawRegion(Config.ICONDOWN.icon, 0, 0,  Config.ICONDOWN.width, Config.ICONDOWN.height, 0,  Config.ICONDOWN.x, Config.ICONDOWN.y,Config.ICONDOWN.alignment);
    if (selectedNode.hasSelectableDetails() && selectedDetail >-1 ) 
      g.drawRegion(Config.ICONSELECT.icon, 0, 0,  Config.ICONSELECT.width, Config.ICONSELECT.height, 0,  Config.ICONSELECT.x, Config.DETAILS.y + (selectedDetail - toprow) * Config.DETAILS.height ,Config.ICONSELECT.alignment);
  }
  
  

  

}
