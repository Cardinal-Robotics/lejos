package lejos.ev3.menu.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.sun.jna.Memory;

import lejos.hardware.LocalWifiDevice;
import lejos.hardware.Wifi;
import lejos.internal.io.NativeWifi;
import lejos.utility.Delay;

public class WifiModel extends AbstractModel{
  private static final String WIFI_CONFIG="/home/root/lejos/config/wpa_supplicant.conf";
  private static final String WIFI_BASE="/home/root/lejos/config/wpa_supplicant.txt";
  private static final String START_WLAN = "/home/root/lejos/bin/startwlan";
  private static final String START_PAN = "/home/root/lejos/bin/startpan";
  private static final String PAN_CONFIG = "/home/root/lejos/config/pan.config";
  private NativeWifi wifi ;
  NativeWifi.WReqPoint reqP ;
  NativeWifi.WReqSocket reqS ;
  LocalWifiDevice wlan ;
  
  protected WifiModel() {
    myKeys = Arrays.asList("wlan0", "br0","ssid" );
    myLists = Arrays.asList("ACCESSPOINTS");
    myCommands = Arrays.asList("CONNECT");
  }
  
  
  @Override
  public void initialize() {
    openDisplay();
    display("Start/nwifi");
    wifi = new NativeWifi();
    reqP = new NativeWifi.WReqPoint();
    reqS = new NativeWifi.WReqSocket();
    wlan = Wifi.getLocalDevice("wlan0");
    closeDisplay();
  }

  @Override
  public String getSetting(String key, String defaultValue) {
    switch (key) {
    case "wlan0":
    case "br0": {return getInetAddress(key, null);}
    case "ssid": {return getAccessPointName("wlan0");}
    }
    return null;
  }
  

  /**
   * Return the current access point name
   * @return access point name
   */
  public String getAccessPointName(String ifName) {
      // Create buffer for the results
      
      reqP.point.flags = 0;
      reqP.point.length = 256;
      reqP.point.p = new Memory(reqP.point.length);
      // Copy the name to the request structure
      System.arraycopy(ifName.getBytes(), 0, reqP.ifname, 0, ifName.length());
      int ret = wifi.ioctl(NativeWifi.SIOCGIWESSID, reqP);
      //System.out.println("error " + ret);
      if (ret >= 0 ) {
  
          StringBuilder sb = new StringBuilder();
          int len = reqP.point.length;
          //System.out.println("length is " + len);
          for(int j=0;j<len;j++) {
              sb.append((char)reqP.point.p.getByte(j));
          }
          
          //System.out.println("Access Point Name:" + sb.toString());
          
          return sb.toString();
      }
      return "";
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

  @Override
  public List<String> getList(String list, String parameter) {
    switch(list) {
    case "ACCESSPOINTS": return Arrays.asList(wlan.getAccessPointNames());
    default: return null;
    }
  }

  @Override
  public List<String> execute(String command, String target, String... arguments) {
    switch(command) {
    case "CONNECT": {
      try {
        writeConfiguration(WIFI_BASE,  WIFI_CONFIG,  target, arguments[0]);
        startNetwork(START_WLAN, true);
        this.broadcast("ssid", target);
        this.broadcast("wlan0",getInetAddress("wlan0",""));
        return null;
      }
      catch(Exception e) {
        this.broadcast("ssid", "");
        this.broadcast("wlan0","");
        return this.getStackTrace(e);
      }
    }
    default: return null;
    }
  }
  
  
    public void writeConfiguration(String in, String out, String ssid, String pwd) throws  Exception  {
        BufferedReader br = new BufferedReader(new FileReader(new File(in)));
        FileWriter fw = new FileWriter(new File(out));
        String line;
        while((line = br.readLine()) != null) {
          int i = line.indexOf("ssid=");
          if (i >= 0) {
            line += "\"" + ssid + "\"";
          }
          i=line.indexOf("psk=");
          if (i >= 0) {
            line += computePSK(ssid, pwd);
          }
          fw.write(line + "\n");
        }
        br.close();
        fw.close();
    }
    
    protected static final char[] hexChars = "0123456789abcdef".toCharArray();
     
    public static String bytesToHex(byte[] buf) {
      int len = buf.length;
      char[] r = new char[len * 2];
      for (int i = 0; i < len; i++) {
        int v = buf[i];
        r[i * 2] = hexChars[(v >>> 4) & 0xF];
        r[i * 2 + 1] = hexChars[v & 0xF];
      }
      return new String(r);
    }
    
     private static String computePSK(String ssid, String password)
       throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
       byte[] ss = ssid.getBytes("utf8");
       char[] pass = password.toCharArray();
     
       SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
       KeySpec ks = new PBEKeySpec(pass, ss, 4096, 256);
       SecretKey s = f.generateSecret(ks);
       byte[] k = s.getEncoded();
     
       return bytesToHex(k);
     }

     private void startNetwork(String startup, boolean startServices) throws Exception {
           Process p = Runtime.getRuntime().exec(startup);
             BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
//             String statusMsg;
//             while((statusMsg = input.readLine()) != null)
//             {
//                 waitScreen.status(statusMsg);
//             }
       int status = p.waitFor();
       System.out.println("start returned " + status);
//             updateIPAddresses ();
             Delay.msDelay(2000);
       if (startServices)
       {
//           waitScreen.status("Start services");
                 startNetworkServices();
                 Delay.msDelay(2000);
       }
   }     
 
     private void startNetworkServices()
     {
       /*
           // Start the RMI server
           System.out.println("Starting RMI");
           
           String rmiIP = (wlanAddress != null ? wlanAddress : (panAddress != null ? panAddress : "127.0.0.1"));
           System.out.println("Setting java.rmi.server.hostname to " + rmiIP);
           System.setProperty("java.rmi.server.hostname", rmiIP);
           
           try { //special exception handler for registry creation
               LocateRegistry.createRegistry(1099); 
               System.out.println("java RMI registry created.");
           } catch (RemoteException e) {
               //do nothing, error means registry already exists
               System.out.println("java RMI registry already exists.");
           }
           
           try {
               RMIRemoteEV3 ev3 = new RMIRemoteEV3();
               Naming.rebind("//localhost/RemoteEV3", ev3);
               RMIRemoteMenu remoteMenu = new RMIRemoteMenu(menu);
               Naming.rebind("//localhost/RemoteMenu", remoteMenu);
           } catch (Exception e) {
               System.err.println("RMI failed to start: " + e);
           }
           
           // Set the date
           try {
               String dt = SntpClient.getDate(Settings.getProperty(ntpProperty, "1.uk.pool.ntp.org"));
               System.out.println("Date and time is " + dt);
               Runtime.getRuntime().exec("date -s " + dt);
           } catch (IOException e) {
               System.err.println("Failed to get time from ntp: " + e);
           }
     */  
     }

    @Override
    public void setSetting(String key, String value) {
      // TODO Auto-generated method stub
      
    }   

}
  

