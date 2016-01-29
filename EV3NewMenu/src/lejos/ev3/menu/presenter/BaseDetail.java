package lejos.ev3.menu.presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lejos.ev3.menu.components.Viewer;
import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.model.Model;
import lejos.ev3.menu.model.ModelListener;
import lejos.ev3.menu.viewer.Editor;
import lejos.ev3.menu.viewer.Menu;

public class BaseDetail implements Detail, ModelListener{
  protected String key;
  protected String label;
  protected String format;
  protected String value;
  protected boolean selectable = false;
  protected boolean initialized = false;
  protected Map<String, String> specials = new HashMap<String, String>();
  protected String defaultValue;
  protected static Control control;
  protected static Menu menu;
  protected static Model model;
  protected Class<? extends Editor> editor     = null;

  
  public static void setEnvironment(Control c, Model m,  Menu m3) {
    control = c;
    model =m;
    menu =m3;
  }
  
  
  public BaseDetail(String key, String label, String format, String defaultValue, Class<? extends Editor> editor) {
    this(key, label, format, defaultValue, true);
    this.editor = editor;
    if (editor == null) selectable = false;
  }

  public BaseDetail(String key, String label, String format, String defaultValue) {
    this(key, label, format, defaultValue, null);
  }

  
  public BaseDetail(String key, String label, String format, String defaultValue, boolean selectable) {
    this.key = key;
    this.label = label;
    this.format = format;
    this.selectable = selectable;
    this.defaultValue = defaultValue;
  }
  
   @Override
  public boolean isSelectable() {
    return selectable;
  }

  @Override
  public void select() {
    if (!selectable) throw new RuntimeException("Detail is not selectable") ;
    if (preExecute()) postExecute(execute());
  }

  protected void postExecute(List<String> feedBack) {
    if (feedBack == null) return;
    if(feedBack.size() == 0 ) return;
    Viewer.view(feedBack);
  }

  protected List<String> execute() {
    return null;
  }

  protected boolean preExecute() {
    return true;
  }

  @Override
  public void needRefresh() {
    initialized = false;
    menu.refreshDetail(this);
    
  }

  @Override
  public void setValue(String value) {
    this.value = value;
    
  }

  @Override
  public String getValue() {
    if (!initialized) initialize();
    return value;
  }

  @Override
  public void setLabel(String label) {
    this.label = label;
    
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public void setKey(String key) {
    this.key = key;
    
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public void setFormat(String format) {
    this.format = format;
  }

  @Override
  public String getFormat() {
    return format;
  }
  
  @Override 
  public String toString() {
    if (!initialized) initialize();
    if (specials.containsKey(value)) 
      return specials.get(value).toString();
    return String.format(format, key, label, value == null ? "" : value);
  }

  @Override
  public void initialize() {
    initialized = true;
  }

  public boolean isInitialized() {
    return initialized;
  }
  
  @Override
  public void addSpecialValue(String value, String label) {
    specials.put(value, label);
  }

  @Override
  public Map<String, String> getSpecials() {
    return specials;
  }


  @Override
  public void keyChanged(String key, String value) {
    if (key.equals(this.key)) initialized = false;
  }


  @Override
  public void listChanged(String list, String parameter) {
    // Empty method, details never contain lists
  }
  
  

  
 



}
