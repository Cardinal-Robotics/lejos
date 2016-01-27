package lejos.ev3.menu.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

import lejos.ev3.menu.model.ModelListener;
import lejos.hardware.ev3.LocalEV3;
import lejos.utility.Delay;

public class EV3Control implements Control {

  
  private static final String JAVA_RUN_CP = "jrun -cp ";
  private static final String JAVA_DEBUG_CP = "jrun -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8000,suspend=y -cp ";
  
  private static final String EV3_WRAPPER = "lejos.internal.ev3.EV3Wrapper";
  
  private Process program;
  private boolean forceDestruction = false;
  private List<ModelListener> listeners = new ArrayList<ModelListener>();


  public void attach(ModelListener listener) {
    listeners.add(listener);
  }
  
  public void detach(ModelListener listener) {
    listeners.remove(listener);
  }


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
	    forceDestruction = false;
	    JarFile jar = new JarFile(file);
	    String prefix = (command.equals("DEBUG_PROGRAM") ? JAVA_DEBUG_CP : JAVA_RUN_CP);
	    String cmd = prefix + path.toString() + " " + EV3_WRAPPER + "  " + jar.getManifest().getMainAttributes().getValue("Main-class");
	    jar.close();
	    String directory = path.substring(0, path.lastIndexOf(java.io.File.separator)+1);
	    program = new ProcessBuilder(cmd.split(" ")).directory(new File(directory)).start();
	    	
        BufferedReader input = new BufferedReader(new InputStreamReader(program.getInputStream()));
        BufferedReader err= new BufferedReader(new InputStreamReader(program.getErrorStream()));
            
        EchoThread echoIn = new EchoThread(path.toString().replace(".jar", ".out"), input, System.out);
        EchoThread echoErr = new EchoThread(path.toString().replace(".jar", ".err"), err, System.err);
            
        echoIn.start(); 
        echoErr.start();
        LocalEV3.get().getGraphicsLCD().clear();
            
        while(true) {
          if (forceDestruction) {
            program.destroy(); 
            break;
          }
          if (!echoIn.isAlive() && !echoErr.isAlive()) break;           
          Delay.msDelay(200);
        }
            
        program.waitFor();
        program = null;
        for (ModelListener listener : listeners) listener.listChanged("GET_FILES", directory);  


      } catch (Exception e) {
        e.printStackTrace();
        return getStackTrace(e);
	  }
      break;
    }
    case "DELETE": {
      try {
        Files.delete(Paths.get(path));
      } catch (IOException e) {
        return getStackTrace(e);
      }
    	//file.delete();
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

@Override
public void killProgram() {
  forceDestruction = true;
  
}

@Override
public void shutdown() {
  // TODO: Modify when the newMenu is no longer run as a program from the Menu
  System.exit(0);
}

@Override
public List<String> runScript(String script) {
  // TODO Auto-generated method stub
  return null;
  
}
  

}
