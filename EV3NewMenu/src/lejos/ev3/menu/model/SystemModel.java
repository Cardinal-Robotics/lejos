package lejos.ev3.menu.model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SystemModel extends AbstractModel{
  
  protected SystemModel() {
    myKeys = Arrays.asList("system.hostname", "lejos.version");

  }
  
  public String getHostname() { 
    List<String> f;
    try {
      f = Files.readAllLines(Paths.get("/etc/hostname"), Charset.defaultCharset());
      return f.get(0);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
  
  public String getVersion() { 
    List<String> f;
    try {
      f = Files.readAllLines(Paths.get("/home/root/lejos/version"), Charset.defaultCharset());
      return f.get(0);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public String getSetting(String key, String defaultValue) {
    switch(key) {
    case "system.hostname" : return getHostname();
    case "lejos.version" : return getVersion();
    }
    return null;
  }
}
