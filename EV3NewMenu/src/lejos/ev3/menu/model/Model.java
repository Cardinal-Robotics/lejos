package lejos.ev3.menu.model;


import lejos.hardware.LocalBTDevice;

public class Model {
  private static Model model;
  private EV3FilesModel filesModel;
  private BtModel btModel;
  private EV3SettingsModel settingsModel;

  private Model(){};
  
  public EV3FilesModel getFilesModel() {
    if (filesModel == null) 
      filesModel = new EV3FilesModel();
    return filesModel;
  }

  public BtModel getBTModel() {
    if (btModel == null) 
      btModel = new BtModel();
    return btModel;
  }

  public EV3SettingsModel getSettingsModel() {
    if (settingsModel == null) 
      settingsModel = new EV3SettingsModel();
    return settingsModel;
  }

  public static Model getModel() {
    if (model == null) 
      model = new Model();
    return model;
  }
  
  
}
