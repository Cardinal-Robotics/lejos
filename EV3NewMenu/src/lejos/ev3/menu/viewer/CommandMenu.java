package lejos.ev3.menu.viewer;

import java.util.List;

import lejos.ev3.menu.viewer.Config.Icon;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;

public class CommandMenu {
  private GraphicsLCD  g              = LocalEV3.get().getGraphicsLCD();

  private List<String> menu;
  
  private int selected ;
  
  private Icon icon = Config.ICONSELECT;

  public CommandMenu(List<String> menu ) {
    this.menu = menu;
  }

  public int select (int defaultItem, int x, int y) {
    selected = defaultItem;
    Font font = Config.DETAIL.font;
    int height = font.getHeight() + 3;
    int width = 0;
    int nItems= menu.size();
    for (String command : menu) {
      width = Math.max(width, font.stringWidth(command)+ 5 + Icons.ARROW_RIGHT.getWidth()) + icon.width;
    }
     if (x + width >= 178) x=178 - width-1;
     if (y + nItems * height >= 125) y = 125  - nItems * height -1;
  
  while (true) {
    g.setColor(GraphicsLCD.WHITE);
    g.fillRect(x, y, width, height *nItems);
    g.setColor(GraphicsLCD.BLACK);
    
    g.setFont(Config.DETAIL.font);
    for (int i =0; i<nItems; i++) {
      g.drawRect(x, y + i * height, width, height );
      g.drawString(menu.get(i), x+2 + icon.width, y + 2 + i * height , 0);
    }
    g.setFont(Font.getDefaultFont());
    g.drawRegion(icon.icon, 0, 0,  icon.width, icon.height, 0,  x+2 , (int) (y + 2 + (selected) * height) ,GraphicsLCD.TOP | GraphicsLCD.LEFT);
    
    switch(Button.waitForAnyPress()) {
    case (Button.ID_UP): {
      if (selected >0 ) selected --;
      break;
    }
    case (Button.ID_DOWN): {
      if (selected < nItems-1 ) selected ++;
      break;
    }
    case (Button.ID_ENTER): {
      return selected;
    }
    case (Button.ID_ESCAPE): {
      return -1;
    }
    }
    
  }
  }
}
