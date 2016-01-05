package lejos.ev3.menu.model;

import java.util.List;

public interface FilesModel {
  List<String> readFile(String path);

  List<String> getEntries(String path, String glob);

  boolean isDirectory(String path);
  
  void delete(String path);

  List<String> execute(String command, String path);

}
