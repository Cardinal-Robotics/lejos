package lejos.ev3.menu.viewer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class viewProps {
  private static Properties props = new Properties();
  private static final String PROPS_FILE = "/home/root/lejos/settings.properties";

  public viewProps() {
    // TODO Auto-generated constructor stub
  }

  public static void main(String[] args) {
    
      try {
              //load a properties file
        props.load(new FileInputStream(PROPS_FILE));
        props.list(System.out);
      } catch (FileNotFoundException e) {
        // Ignore 
        } catch (IOException e) {
        System.err.println("Failed to load properties file");
      }


  }

}
