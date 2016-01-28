package lejos.ev3.menu.components;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.utility.Delay;

public class UI {
  private static Keys keys = BrickFinder.getDefault().getKeys();
  private static int firstTimeOut = 500;
  private static int subsequentTimeOuts = 200;
  private static int lastButton =0;
  private static long startTime =0;
  private static long lastTime = 0; 
  private static long delay = 10; 
  
  /** Gets user input
   * Enter and Escape are returned upon button release
   * navigation keys are returned immediately and after time outs
   * @return
   */
  public static int getUI() {
    while (true) {
      int button = keys.getButtons();
      long now = System.currentTimeMillis();
      
      // if an enter or escape key is released return the most recently pressed key
      if (button == 0 && (lastButton == Keys.ID_ENTER || lastButton == Keys.ID_ESCAPE)) {
        int previous = lastButton;
        lastButton = 0;
        return previous;
      }
      
      // if a navigation key has just been pressed start the timers used for repeat function and return the pressed key immediately
      if (lastButton ==0 && forNavigation(button)) {
        startTime = now;
        lastButton = button;
        return button;
      }
      
      // if a navigation key is pressed for the duration of the initial timeout return the pressed key;
      if (forNavigation(button) && button ==lastButton && lastTime ==0 && now > startTime + firstTimeOut) {
        lastTime = now;
        return button;
      }
      
      // if a navigation key is pressed, the initial time out has expied an a subsequent timeout has expired return the pressed key;
      if (forNavigation(button) && button ==lastButton && lastTime !=0 && now > startTime + subsequentTimeOuts) {
        lastTime = now;
        return button;
      }
      
      // if no navigation button is pressed clear all the timers;
      if (!forNavigation(button)) {
        startTime =0;
        lastTime = 0;
      }
      
      // remember last button and wait a bit;
      lastButton = button;
      Delay.msDelay(delay);
      }
    
  }
  
  /** Gets user input, but only for selected buttons after release;
   * @param buttons
   * @return
   */
  static int waitForPressAndRelease(int buttons) {
    int last = 0;
    while (true) {
      int button = keys.getButtons();
      if (button ==0 && last != 0) return last;
      if ((button & buttons) != 0 ) last = button;
      Delay.msDelay(delay);
    }
    
    
    
    
    
  }

  private static boolean forNavigation(int button) {
    switch (button) {
    case Keys.ID_DOWN:
    case Keys.ID_UP:
    case Keys.ID_LEFT:
    case Keys.ID_RIGHT: return true;
    default: return false;
    }
    
  }
  
  
  

}
