package lejos.ev3.menu.model;

import java.util.List;

public interface Model {
  
  /** Subscribes an object as a listener to the model
   * @param listener The ID of the object to register
   */
  public void attach(String key, ModelListener listener);
  
  /** Unsubscribes an object as a listener
   * @param listener
   */
  public void detach(String key, ModelListener listener);
  
  /** Test if the model controls a setting identified by the key
   * @param key
   * @return
   */
  public boolean hasSetting(String key);
  
  /** Test if the model is able to execute the command identified by the parameter
   * @param command
   * @return
   */
  public boolean canExecute(String command);
  
  /** Test if the model is able to generate a list identified by the parameter
   * @param list
   * @return
   */
  public boolean canList(String list);
  
  /** Gets a setting from the model
   * @param key
   * @param defaultValue
   * @return
   */
  public String getSetting(String key, String defaultValue);
  
  /** Updates a setting to the model. The model will permanently store the new value
   * @param key
   * @param value
   */
  public void setSetting(String key, String value);
  
  /** Executes a command on the target. 
   * @param command
   * @param target
   * @return The stack trace if the command fails. null if the command succeeds.
   */
  public List<String> execute(String command, String target, String... arguments);
  
  /** Returns a list of values 
   * @param list
   * @param parameter
   * @return
   */
  public List<String> getList(String list, String parameter);

}
