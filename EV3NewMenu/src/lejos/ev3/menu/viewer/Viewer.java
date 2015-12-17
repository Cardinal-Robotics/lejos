package lejos.ev3.menu.viewer;

import java.nio.file.Path;
import java.util.List;

import lejos.ev3.menu.control.Control;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;

/** Simple file viewer for text files
 * @author Aswin Bouwmeester
 *
 */
public class Viewer {
  private static GraphicsLCD canvas = LocalEV3.get().getGraphicsLCD(); 
  
  private Viewer(){};
  
  
public static void view(Control control, Path path) {
    List <String> lines = control.readFile(path);
    view (lines);
}
    
public static void view(List<String> lines) {    
    int top = 0;
    int start = 0;
    int longest = 0; 
    int oldstart =-1;
    int oldtop =-1;
    int n = Config.VIEWER.height / Config.VIEWLINE.height;
    int l = Config.VIEWLINE.width / Config.VIEWLINE.font.width;
    while (true) {
      if (oldstart != start | oldtop != top) {
      Config.VIEWER.clear(canvas);
      for (int i =top ; i< n +top && i < lines.size() ; i ++) {
        longest = Math.max(longest, lines.get(i).length());
        String line = lines.get(i);
        if (start < line.length()) {
          line = line.substring(start);
        }
        else {
          line ="";
        }
        int y = (i-top) * Config.VIEWLINE.height + Config.VIEWER.y;
        canvas.drawString(line, 0,y , 0); 
      }
      oldstart = start ;
      oldtop = top;
      }
      Delay.msDelay(200);
      int but = Button.getButtons();
      if ((but & Button.ID_LEFT) > 0) {if (start > 0) start --;}
      if ((but & Button.ID_RIGHT) > 0) {if (start < longest-l) start ++;}
      if ((but & Button.ID_UP) > 0) {if (top > 0) top --;}
      if ((but & Button.ID_DOWN) > 0) {if (start < lines.size()-n) start ++;}
      if ((but & Button.ID_ESCAPE) > 0) { 
        Config.VIEWER.clear(canvas);
        while (Button.ESCAPE.isDown()) Delay.msDelay(10);
        return;
       }
      
    }
  }

}
