package lejos.ev3.menu.components;

import lejos.ev3.menu.presenter.DetailNumericValue;
import lejos.ev3.menu.presenter.DetailStringValue;

public class EditorIP implements Editor{

  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

  private String oldIP;

  @Override
  public void edit(DetailNumericValue model) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void edit(DetailStringValue model) {
//    String newValue = getIP();
//    if (!newValue.equals(model.getValue())) model.setValue(newValue);
  }
  
  String getIP(String startValue) {
    String[] parts = startValue.split("\\.");
    int[] ip = new int[3];
    for (String part : parts) {
//      n = Integer.parseInt(part);
    }
    return startValue;
  }

  
  
}
