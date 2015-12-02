package lejos.ev3.menu.viewer;

import java.util.ArrayList;
import java.util.List;

import lejos.ev3.menu.model.Detail;
import lejos.ev3.menu.model.Node;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;

public class Menu {

  private List<Node>   nodes;
  private int          iNode  = -1;
  private Node         currentNode;
  private GraphicsLCD  g              = LocalEV3.get().getGraphicsLCD();
  private List<Detail> details;
  private int          toprow         = 0;
  private int          iDetail = -1;
  private Detail       currentDetail ;
  private int          visibleDetails          = (Config.DETAILS.height) / (Config.DETAIL.height + Config.DETAIL.y);  
  private List<Node>   navigationStack = new ArrayList<Node>(0);
  private List<Integer>    navigationIndex = new ArrayList<Integer>(0);
  private Node top;
  private ScrollBar bar ;
  private int nDetails;
  
  public Menu(Node top) {
    this.top = top;
    nodes = top.getChildren();
    selectNode(0);
  }
  

  public void run() {
    draw();
    while(true) {  
      switch (Button.waitForAnyPress()) {
      case Button.ID_LEFT: {
        if (iNode >0) 
          selectNode(iNode-1);
        else 
          selectNode(nodes.size()-1);
        draw();
        break;
      }
      case Button.ID_RIGHT: {
        if (iNode <  nodes.size()-1) 
          selectNode(iNode+1);
        else
          selectNode(0);
        draw();
        break;
      }
      case Button.ID_DOWN: {
        if (currentNode.hasSelectableDetails()) {
            iDetail = findNext(iDetail);
            if (iDetail >= toprow + visibleDetails )
              toprow = iDetail - visibleDetails + 1;
            if (bar != null) 
              bar.setToprow(toprow);
            draw();
          }
       else {
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
          }
        else {
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
        if (currentNode.hasChildren()){
          navigationStack.add(top);
          navigationIndex.add(iNode);
          top = currentNode;
          nodes = top.getChildren();
          selectNode(0);
        }
        else {
          Detail detail = details.get(iDetail);
          if (detail.hasCommands()) {
            detail.executeCommand(new CommandMenu(detail.getCommands()).select(0, Config.DETAILS.x, Config.DETAILS.y + (iDetail - toprow) * Config.DETAILS.height));
          }
          if (detail.isEditable()) {
            //detail.edit(Config.DETAILS.x, Config.DETAILS.y + (selectedDetail - toprow) * Config.DETAILS.height);
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
    toprow = 0;
    iDetail = -1;
    iNode = i;
    currentDetail =null;
    bar = null;

    currentNode = nodes.get(iNode);
    details = currentNode.getDetails();

    if (details != null) {
      nDetails = details.size();
      if (currentNode.hasSelectableDetails()) iDetail =findFirst();

      if (nDetails > visibleDetails) {
        bar = new ScrollBar(g, Config.DETAILS.x + Config.DETAILS.width -12, Config.DETAILS.y, Config.DETAILS.height, nDetails, visibleDetails, iDetail);
      }
    }
    
  }

  private void draw() {
    drawStatus();
    drawTitleBar();
    if (details != null) drawDetails();
  }
  
  private void drawStatus() {
    clearRegion(Config.STATUS, g.BLACK);
    g.setColor(g.WHITE);
    g.setFont(Config.STATUS.font);
    g.drawString("Status bar", Config.STATUS.x, Config.STATUS.y+1, Config.STATUS.alignment);
    g.setColor(g.BLACK);
    g.setFont(Font.getDefaultFont());
  }
  
  private void drawTitleBar() {
//    g.drawRegion(Config.ICONLEFT.icon, 0, 0,  Config.ICONLEFT.width, Config.ICONLEFT.height, 0,  Config.ICONLEFT.x, Config.ICONLEFT.y,Config.ICONLEFT.alignment);
//    g.drawRegion(Config.ICONRIGHT.icon, 0, 0,  Config.ICONRIGHT.width, Config.ICONRIGHT.height, 0,  Config.ICONRIGHT.x, Config.ICONRIGHT.y,Config.ICONRIGHT.alignment);
//    g.drawRegion(currentNode.getIcon(), 0, 0,  Config.ICONNODE.width, Config.ICONNODE.height, 0,  Config.ICONNODE.x, Config.ICONNODE.y,Config.ICONNODE.alignment);
    clearRegion(Config.TITLE, g.WHITE);
    g.setFont(Config.TITLE.font);
    g.drawString(currentNode.toString(), Config.TITLE.x + Config.TITLE.width / 2, Config.TITLE.y, Config.TITLE.alignment);
    g.setFont(Font.getDefaultFont());
    g.drawLine(Config.TITLE.x, Config.TITLE.bottom, Config.TITLE.width, Config.TITLE.bottom);
  }
  
  private void drawDetails() {
    clearRegion(Config.DETAILS, g.WHITE);
    if (bar != null) bar.draw();
    g.setFont(Config.DETAIL.font);
    for (int i =0; i< visibleDetails; i++) {
      if (i+toprow < details.size()) {
        Detail d = details.get(i+toprow);
        String label; 
        if (d.getType()==Detail.NUMERIC) 
          label = d.getLabel() + String.format(d.getFormat(), d.getNValue());
        else 
          label = d.getLabel() + String.format(d.getFormat(), d.getSValue());
        g.drawString(label, Config.DETAILS.x + Config.DETAIL.x, Config.DETAILS.y + i * (Config.DETAIL.y +  Config.DETAIL.height) , Config.DETAIL.alignment);
      }
    }
    g.setFont(Font.getDefaultFont());

    if (iDetail != -1 ) 
      g.drawRegion(Config.ICONSELECT.icon, 0, 0, Config.ICONSELECT.icon.getWidth(), Config.ICONSELECT.icon.getHeight(), 0 , 
          Config.DETAILS.x + Config.DETAIL.x -1, Config.DETAILS.y + (iDetail - toprow) * (Config.DETAIL.y +  Config.DETAIL.height) , g.TOP | g.RIGHT); 
  }
  
private int findPrevious(int current) {
  if (current == 0) return 0;
  for (int i = current -1 ; i >= 0; i--) {
    Detail d = details.get(i);
    if (d.isEditable() | d.isSelectable()) return i;
  }
  return current;
}
  
private int findNext(int current) {
  if (current == nDetails-1) return current;
  for (int i = current + 1 ; i < nDetails; i++) {
    Detail d = details.get(i);
    if (d.isEditable() | d.isSelectable()) return i;
  }
  return current;
}
  
private int findFirst() {
  for (int i = 0 ; i < nDetails; i++) {
    Detail d = details.get(i);
    if (d.isEditable() | d.isSelectable()) return i;
  }
  return -1;
}

private void clearRegion(Config.Panel panel, int color) {
  g.setColor(color);
  g.fillRect(panel.x, panel.y, panel.width+1, panel.height+1);
  g.setColor(g.BLACK);
 
  
}

  

}
