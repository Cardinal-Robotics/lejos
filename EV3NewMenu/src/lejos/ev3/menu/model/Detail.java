package lejos.ev3.menu.model;


public interface Detail {
  
public boolean isEditable();
  
public boolean hasMenu();
  
 public void edit(int x, int y);
 
 public Detail addMenuItem(String label);
 
 public void setLabel(String label);
 
 public String getLabel();

 public void runMenu( int x, int y);
 
 public int runMenu(int defaultItem, int x, int y);

}
