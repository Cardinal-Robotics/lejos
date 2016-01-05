package lejos.ev3.menu.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class EV3SettingsModel implements SettingsModel {
  static EV3SettingsModel          model;
  private PropertySettings props;
  private SystemClass      system;

  public static SettingsModel getModel() {
    if (model == null)
      model = new EV3SettingsModel();
    return model;
  }

  private EV3SettingsModel() {
    props = new PropertySettings();
    system = new SystemClass();
  }


  @Override
  public String getSetting(String key, String defaultValue) {
    String[] k = key.split("\\.");
    if (k[0].equals("lejos"))
      return props.getProperty(key, defaultValue);
    if (k[0].equals("system"))
      return system.getSetting(key, defaultValue);
    throw new RuntimeException("Unable to resolve " + key);
  }

  @Override
  public void setSetting(String key, String value) {
    String[] k = key.split("\\.");
    if (k[0].equals("lejos"))
      props.setProperty(key, value);
    if (k[0].equals("system"))
      system.setSetting(key, value);
  }



  private class PropertySettings {

    private Properties   props      = new Properties();
    private final String PROPS_FILE = "/home/root/lejos/settings.properties";

    private PropertySettings() {
      try {
        // load a properties file
        props.load(new FileInputStream(PROPS_FILE));
      } catch (FileNotFoundException e) {
        // Ignore
      } catch (IOException e) {
        System.err.println("Failed to load properties file");
      }
    }

    public void setProperty(String key, String value) {
      props.setProperty(key, value);
      try {
        props.store(new FileOutputStream(PROPS_FILE), null);
      } catch (IOException e) {
        System.err.println("Failed to store properties");
      }
    }

    public String getProperty(String key, String defaultValue) {
      if (props.containsKey(key))
        return props.getProperty(key);
      return defaultValue;
    }

  }

  private class SystemClass {

    public String getSetting(String key, String defaultValue) {
      String[] k = key.split("\\.");
      if (k[1].equals("wifi"))
        return getInetAddress(k[2], defaultValue);
      if (k[1].equals("hostname"))
        return getHostname();
      if (k[1].equals("lejos"))
        return "?";
      throw new RuntimeException("Unable to resolve " + key);
    }

    public void setSetting(String key, String value) {
      // TODO Auto-generated method stub
      
    }

    public String getHostname() {
      List<String> f = EV3FilesModel.getModel().readFile("/etc/hostname");
      if (f == null)
        return null;
      return f.get(0);
    }

    public String getInetAddress(String wifiInterface, String defaultValue) {
      {
        Enumeration<NetworkInterface> interfaces;
        try {
          interfaces = NetworkInterface.getNetworkInterfaces();
          while (interfaces.hasMoreElements()) {
            NetworkInterface current = interfaces.nextElement();
            try {
              if (!current.isUp() || current.isLoopback() || current.isVirtual())
                continue;
            } catch (SocketException e) {
              System.err.println("Failed to get network properties: " + e);
            }
            Enumeration<InetAddress> addresses = current.getInetAddresses();
            while (addresses.hasMoreElements()) {
              InetAddress current_addr = addresses.nextElement();
              if (current_addr.isLoopbackAddress())
                continue;
              if (current.getName().equals(wifiInterface))
                return current_addr.getHostAddress();
            }
          }
        } catch (SocketException e) {
          System.err.println("Failed to get network interfaces: " + e);
        }
        return defaultValue;

      }
    }

  }

}
