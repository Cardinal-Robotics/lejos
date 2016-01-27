package lejos.ev3.menu.model;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;

public class WifiModel extends AbstractModel{
  
  protected WifiModel() {
    myKeys = Arrays.asList("wlan0", "br0" );
  }

  @Override
  public String getSetting(String key, String defaultValue) {
    switch (key) {
    case "wlan0":
    case "br0": {return getInetAddress(key, null);}
    }
    return null;
  }

  private String getInetAddress(String wifiInterface, String defaultValue) {
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
  

