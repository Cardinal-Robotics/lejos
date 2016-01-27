package lejos.ev3.menu.viewer;

import lejos.ev3.menu.components.TextPanel;
import lejos.ev3.menu.presenter.Detail;
import lejos.hardware.Button;

public class EditorBtKey implements Editor{

  @Override
  public void edit(Detail detail) {
    int selected =0;
    TextPanel p = new TextPanel("Enter EV3 PIN\n\n");
    p.setBorders(15);
    p.setShadow(true);
    p.setHeight(70);
    p.setMargin(8);
    int xOffset = p.getX() + 15;
    int yOffset= p.getY() + p.getFont().getHeight() + 10;
    String oldValue = detail.getValue();
    int len = oldValue.length();
    char[] pin = new char[len];
    TextPanel[] n = new TextPanel[len];
    for (int i = 0; i < len; i++) {
        pin[i] = oldValue.charAt(i);
        n[i] = new TextPanel(oldValue.substring(i, i), xOffset, yOffset);
        n[i].setMargin(4);
        xOffset += 20;
    }
    while (true){
      p.paint();
      for (int i=0;i<len;i++) {
        n[i].setBorders(i==selected ? 15 : 0);
        n[i].setMessage(String.copyValueOf(pin, i, 1));
        n[i].paint();
      }
      switch (Button.waitForAnyPress()) {
      case Button.ID_ESCAPE: {return;}
      case Button.ID_ENTER: {detail.setValue(String.copyValueOf(pin)); return;}
      case Button.ID_LEFT: { if (selected > 0) selected--; break;}
      case Button.ID_RIGHT: { if (selected < len) selected++; break;}
      case Button.ID_UP: { pin[selected]++; if (pin[selected] > '9') pin[selected]='0' ; break;}
      case Button.ID_DOWN: { pin[selected]--; if (pin[selected] < '0') pin[selected]='9' ; break;}
      }
      
  }

}
}
