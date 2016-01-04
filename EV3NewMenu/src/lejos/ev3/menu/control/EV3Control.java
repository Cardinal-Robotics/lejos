package lejos.ev3.menu.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
import java.util.jar.JarFile;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.internal.io.Settings;
import lejos.internal.io.SystemSettings;
import lejos.utility.Delay;

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
  
  private static final String JAVA_RUN_CP = "jrun -cp ";
  private static final String JAVA_DEBUG_CP = "jrun -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8000,suspend=y -cp ";
  
  private static final String EV3_WRAPPER = "lejos.internal.ev3.EV3Wrapper";
  
  private Process program;




  @Override
  public List<String> execute(String command, String path) {
    File file = new  File(path);
        switch(command) {
    case "RUN_TOOL": {
      try {
		JarMain.executeJar(file);
	  } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
      break;
    }
    case "RUN_PROGRAM":
    case "DEBUG_PROGRAM":
    case "RUN_SAMPLE" :
    {
	  try {
	    JarFile jar = new JarFile(file);
	    String prefix = (command.equals("DEBUG_PROGRAM") ? JAVA_DEBUG_CP : JAVA_RUN_CP);
	    String cmd = prefix + path.toString() + " " + EV3_WRAPPER + "  " + jar.getManifest().getMainAttributes().getValue("Main-class");
	    jar.close();
	    String directory = (command.equals("RUN_SAMPLE") ? SAMPLES_DIRECTORY : PROGRAMS_DIRECTORY);
	    program = new ProcessBuilder(cmd.split(" ")).directory(new File(directory)).start();
	    	
        BufferedReader input = new BufferedReader(new InputStreamReader(program.getInputStream()));
        BufferedReader err= new BufferedReader(new InputStreamReader(program.getErrorStream()));
            
        EchoThread echoIn = new EchoThread(path.toString().replace(".jar", ".out"), input, System.out);
        EchoThread echoErr = new EchoThread(path.toString().replace(".jar", ".err"), err, System.err);
            
        echoIn.start(); 
        echoErr.start();
        LocalEV3.get().getGraphicsLCD().clear();
            
        while(true) {
          int b = Button.getButtons(); 
          if (b == 6) {
            program.destroy(); 
            break;
          }
          if (!echoIn.isAlive() && !echoErr.isAlive()) break;           
          Delay.msDelay(200);
        }
            
        program.waitFor();
        program = null;

      } catch (Exception e) {
        e.printStackTrace();
        return getStackTrace(e);
	  }
      break;
    }
    case "DELETE": {
    	file.delete();
      break;
    }
    }
    return null;
  }
  
private List<String> getStackTrace(Exception e) {
  List<String>target = new ArrayList<String>();
  StackTraceElement[] source = e.getStackTrace();
  for(StackTraceElement line : source) {
    target.add(line.toString());
  }
  return target;
  
}
  

}
