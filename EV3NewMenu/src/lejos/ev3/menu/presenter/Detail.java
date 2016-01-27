package lejos.ev3.menu.presenter;

import java.util.Map;


public interface Detail {
  /**
   * Returns true it the detail can be selected by the user
   * 
   * @return
   */
  public boolean isSelectable();

  /**
   * Called after the user has selected the Detail. Functionality depends on
   * the type of detail.
   * 
   * @param menu
   */
  public void select();

  /**
   * To be called if the Detail should be refreshed as the data it displays has
   * changed
   * 
   */
  public void needRefresh();

  /**
   * Returns the text to display in the menu
   * 
   * @return
   */
  public String toString();
  
  public void setValue(String value);
  
  public String getValue();
  
  public void setLabel(String label);
  
  public String getLabel();
  
  public void setKey(String key);
  
  public String getKey();
  
  public void setFormat(String format);
  
  public String getFormat();
  
  public void initialize();
  
  public boolean isInitialized();
  
  public void addSpecialValue(String value, String label);

  public Map<String, String> getSpecials();


}
