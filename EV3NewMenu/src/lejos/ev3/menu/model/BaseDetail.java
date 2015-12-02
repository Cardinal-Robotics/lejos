package lejos.ev3.menu.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import lejos.ev3.menu.control.MenuControl;

public class BaseDetail implements Detail {
 
  
  protected boolean editable = false;
  private List<String> commands;
  protected MenuControl control;
  String label ;
  private final int type;
  private final Properties props = new Properties();
  private final int possibility;
  private int nValue;
  private String sValue;
  

  public BaseDetail(MenuControl control, String label, String value, String format, int type ) {
    this.control = control;
    this.type = STRING;
    this.sValue = value;
    this.possibility = type;
    props.setProperty("label", label);
    props.setProperty("format", format);
  }
  
  public BaseDetail(MenuControl control, String label, int value, String format, int type ) {
    this.control = control;
    this.type = NUMERIC;
    this.nValue = value;
    this.possibility = type;
    props.setProperty("label", label);
    props.setProperty("format", format);
  }
  


  @Override
  public Detail setLabel(String label) {
    props.setProperty("label", label);
    return this;
  }

  @Override
  public String getLabel() {
    return props.getProperty("label", "");
  }


  @Override
  public boolean isEditable() {
    return possibility == TYPE_EDITABLE ? true : false;
  }


  @Override
  public Detail addCommand(String label) {
    if (commands == null) commands = new ArrayList<String>();
    commands.add(label);
    return this;
  }


  @Override
  public boolean hasCommands() {
    return commands == null ? false : true;
  }

  @Override
  public String toString() {
    if (type == NUMERIC) 
      return String.format(props.getProperty("label") + props.getProperty("format"),getNValue());
    else 
      return String.format(props.getProperty("label") + props.getProperty("format"),getSValue());
  }

  @Override
  public boolean isSelectable() {
    return possibility == TYPE_SELECTABLE ? true : false;
  }

  @Override
  public Detail setNValue(int value) {
    nValue = value;
    return this;
  }

  @Override
  public int getNValue() {
    return nValue;
  }

  @Override
  public Detail setSValue(String value) {
    sValue = value;
    return this;
    
  }

  @Override
  public String getSValue() {
    return sValue;
  }

  @Override
  public Detail setFormat(String format) {
    props.setProperty("format", format);
    return this;
  }

  @Override
  public String getFormat() {
    return props.getProperty("format");
  }

  @Override
  public Properties getProperties() {
    return props;
  }

  @Override
  public List<String> getCommands() {
    return commands;
  }
  
  @Override
  public int getType() {
    // TODO Auto-generated method stub
    return type;
  }

  @Override
  public Detail setProperty(String key, String value) {
    props.setProperty(key, value);
    return this;
  }

  @Override
  public void executeCommand(int index) {
  }
  
 
  

}
