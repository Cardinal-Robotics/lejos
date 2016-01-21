package lejos.ev3.menu.model;

import java.util.ArrayList;
import java.util.List;

public class Utils {
  
  private Utils() {}
  
  public static List<String> getStackTrace(Exception e) {
    List<String>target = new ArrayList<String>();
    StackTraceElement[] source = e.getStackTrace();
    for(StackTraceElement line : source) {
      target.add(line.toString());
    }
    return target;
  }

}
