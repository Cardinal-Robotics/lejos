package lejos.ev3.menu.control;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import lejos.internal.io.Settings;
import lejos.internal.io.SystemSettings;

public class EV3Control implements Control {

  private static final String PROGRAMS_DIRECTORY = "/home/lejos/programs";
  private static final String LIB_DIRECTORY      = "/home/lejos/lib";
  private static final String SAMPLES_DIRECTORY  = "/home/root/lejos/samples";
  private static final String TOOLS_DIRECTORY    = "/home/root/lejos/tools";
  private static final String MENU_DIRECTORY     = "/home/root/lejos/bin/utils";
  private static final String START_BLUETOOTH    = "/home/root/lejos/bin/startbt";
  private static final String START_WLAN         = "/home/root/lejos/bin/startwlan";
  private static final String START_PAN          = "/home/root/lejos/bin/startpan";
  private static final String PAN_CONFIG         = "/home/root/lejos/config/pan.config";
  private static final String WIFI_CONFIG        = "/home/root/lejos/config/wpa_supplicant.conf";
  private static final String WIFI_BASE          = "wpa_supplicant.txt";
  private static final String WLAN_INTERFACE     = "wlan0";
  private static final String PAN_INTERFACE      = "br0";

  @Override
  public String getProperty(String key) {
    return getProperty(key, null);
  }

  @Override
  public int getNumericProperty(String key) {
    return getNumericProperty(key, 0);
  }

  @Override
  public String getProperty(String key, String defaultValue) {
    switch (key) {
    case ("WIFI_WLAN0"): return getIPAddresses("wlan0");
    case ("WIFI_BR0"): return getIPAddresses("br0");
    case ("HOSTNAME"): {
      List<String> f = readFile(Paths.get("/etc/hostname"));
      if (f == null) return null;
      return f.get(0);
    }
    default: return Settings.getProperty(key, defaultValue);
    }
  }

  @Override
  public void setProperty(String key, String value) {
    Settings.setProperty(key, value);
  }

  @Override
  public int getNumericProperty(String key, int defaultValue) {
    // TODO Auto-generated method stub
    String s = Settings.getProperty(key, "");
    if (s == null)
      return defaultValue;

    try {
      return Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  @Override
  public void setNumericProperty(String key, int value) {
    Settings.setProperty(key, String.format("%d", value));
  }

  @Override
  public void execute(String command, Path path) {
    switch(command) {
    case "RUN": {
      break;
    }
    case "RUN_SEPERATE": {
      break;
    }
    case "DEBUG": {
      break;
    }
    case "DELETE": {
      break;
    }
    }
  }


  
  
  
  private String getIPAddresses(String wifiInterface)
  {
      Enumeration<NetworkInterface> interfaces;
      try
      {
          interfaces = NetworkInterface.getNetworkInterfaces();
          while (interfaces.hasMoreElements()){
            NetworkInterface current = interfaces.nextElement();
            try
            {
                if (!current.isUp() || current.isLoopback() || current.isVirtual()) continue;
            } catch (SocketException e)
            {
              System.err.println("Failed to get network properties: " + e);
            }
            Enumeration<InetAddress> addresses = current.getInetAddresses();
            while (addresses.hasMoreElements()){
                InetAddress current_addr = addresses.nextElement();
                if (current_addr.isLoopbackAddress()) continue;
                if (current.getName().equals(wifiInterface))
                    return current_addr.getHostAddress();
            }
        }
      } catch (SocketException e)
      {
          System.err.println("Failed to get network interfaces: " + e);
      }
      return null;

  } 
  
  
@Override
  public List<String> readFile(Path path) {
    try {
      return   Files.readAllLines(path, Charset.defaultCharset());
    } catch (IOException e) {
      System.err.println("Failed to read file: " + path + e);
    }
    return null;
  }


@Override 
public List<Path> getEntries(Path path, String glob) {
  List<Path> entries = new ArrayList<Path>();  
  try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, glob)) {
     for (Path entry: stream) {
         entries.add(entry);
     }
 } catch (IOException e) {
     System.err.println("Failed to read directory:" + path + e);
 }
  return entries;
  
}

@Override
public boolean isDirectory(Path path) {
  return Files.isDirectory(path);
}

}
