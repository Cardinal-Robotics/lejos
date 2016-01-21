package lejos.ev3.menu.model;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EV3FilesModel {
  private List<FilesChanged> listeners = new ArrayList<FilesChanged>();

  
  
  EV3FilesModel(){};
  
  public void attach(FilesChanged listener) {
    listeners.add(listener);
  }
  
  public void detach(FilesChanged listener) {
    listeners.remove(listener);
  }


  public List<String> readFile(String path) {
    try {
      return Files.readAllLines(Paths.get(path), Charset.defaultCharset());
    } catch (IOException e) {
      System.err.println("Failed to read file: " + path + e);
    }
    return null;
  }

  public List<String> getEntries(String path, String glob) {
    List<String> entries = new ArrayList<String>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(path), glob)) {
      for (Path entry : stream) {
        entries.add(entry.toString());
      }
    } catch (IOException e) {
      System.err.println("Failed to read directory:" + path + e);
    }
    return entries;

  }

  public boolean isDirectory(String path) {
    File f = new File(path);
    return f.isDirectory();
  }

  public void delete(String path) {
    File file = new  File(path);
    file.delete();
  }


private List<String> deleteFile(String path) {

  try {
    Files.delete(Paths.get(path));
  } catch (IOException e) {
    return getStackTrace(e);
  }
  for (FilesChanged listener : listeners) listener.filesChanged(path); 
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


  public List<String> execute(String command, String path) {

    if (command.equals("VIEW")) return readFile(path);
    if (command.equals("DELETE")) return deleteFile(path);
    return null;
  }


}
