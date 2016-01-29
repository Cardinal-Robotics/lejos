package lejos.ev3.menu.presenter;

import java.util.ArrayList;
import java.util.List;


import lejos.utility.Delay;

public class AutoRefreshDetail extends SettingDetail {
  static List<AutoRefreshDetail> instances = new ArrayList<AutoRefreshDetail>();
  static int refreshInterval = 5000;
  static Refresh refresh;
  

  private AutoRefreshDetail(String key, String label, String format, String defaultValue) {
    super(key, label, format, defaultValue);
  }
  
 public static Detail getInstance(String key, String label, String format, String defaultValue) {
   AutoRefreshDetail i = new AutoRefreshDetail(key, label, format, defaultValue);
   if (instances.isEmpty()) {
     refresh = new Refresh();
     refresh.setDaemon(true);
     refresh.start();
   }
   instances.add(i);
   return i;
 }
 
 static class Refresh extends Thread {
     long lastTime = System.currentTimeMillis();
            
     @Override
     public void run(){  
       while (true) {
         Delay.msDelay(refreshInterval);
         for (AutoRefreshDetail instance : instances)
           instance.needRefresh();
       }
       
     }
 }

}
