package lejos.ev3.menu.model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModel implements Model{
  
  private List<ModelListener> listeners = new ArrayList<ModelListener>();
  protected List<String> myKeys = new ArrayList<String>(); 
  protected List<String> myCommands = new ArrayList<String>(); 
  protected List<String> myLists = new ArrayList<String>(); 
  
  @Override
  public boolean hasSetting(String key) {
    if (myKeys.contains(key)) return true;
    return false;
  }

  @Override
  public boolean canExecute(String command) {
    if (myCommands.contains(command)) return true;
    return false;
  }

  @Override
  public boolean canList(String list) {
    if (myLists.contains(list)) return true;
    return false;
  }

  @Override
  public void attach(String key, ModelListener listener) {
    listeners.add(listener);
  }

  @Override
  public void detach(String key, ModelListener listener) {
    listeners.remove(listener);
  }
  
  protected void broadcast(String id, String value) {
    for (ModelListener listener : listeners) {
      listener.keyChanged(id, value);
      listener.listChanged(id, value);
    }
  }
  
  protected List<String> getStackTrace(Exception e) {
    List<String>target = new ArrayList<String>();
    StackTraceElement[] source = e.getStackTrace();
    for(StackTraceElement line : source) {
      target.add(line.toString());
    }
    return target;
  }

  @Override
  public String getSetting(String key, String defaultValue) {
    return null;
  }

  @Override
  public void setSetting(String key, String value) {
  }

  @Override
  public List<String> execute(String command, String target, String... arguments) {
    return null;
  }

  @Override
  public List<String> getList(String list, String parameter) {
    return null;
  }

}
