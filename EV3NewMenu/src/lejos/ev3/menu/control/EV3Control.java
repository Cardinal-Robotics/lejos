package lejos.ev3.menu.control;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
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
    return getProperty(key, "");
  }

  @Override
  public int getNumericProperty(String key) {
    return getNumericProperty(key, 0);
  }

  @Override
  public String getProperty(String key, String defaultValue) {
    return Settings.getProperty(key, defaultValue);
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
  public void execute(String command, String id) {
    // TODO Auto-generated method stub
  }

  @Override
  public File getFile(String name) {
    // TODO Auto-generated method stub
    return new File(name);
  }

  public void execute(String target) {
    // TODO Auto-generated method stub

  }

}
