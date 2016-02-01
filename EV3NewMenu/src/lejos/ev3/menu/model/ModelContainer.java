package lejos.ev3.menu.model;


import java.util.ArrayList;
import java.util.List;

public class ModelContainer implements Model{
  private static ModelContainer model;
  private List<Model> subModels = new ArrayList<Model>(); 

  private ModelContainer(){
    subModels.add(new FilesModel());
    subModels.add(new BtModel());
    subModels.add(new PropertyModel());
    subModels.add(new SystemModel());
    subModels.add( new WifiModel());
    subModels.add( new PanModel());
  };
  
  public static ModelContainer getModel() {
    if (model == null) 
      model = new ModelContainer();
    return model;
  }

  @Override
  public void attach(String key, ModelListener listener) {
    Model target;
    target = this.getResponsibleForSetting(key);
    if (target != null) target.attach(key, listener);
    target = this.getResponsibleForCommand(key);
    if (target != null) target.attach(key, listener);
    target = this.getResponsibleForList(key);
    if (target != null) target.attach(key, listener);
  }

  @Override
  public void detach(String key, ModelListener listener) {
    for (Model subModel : subModels) 
      subModel.detach(key, listener);
  }

  @Override
  public boolean hasSetting(String key) {
    Model target;
    target = this.getResponsibleForSetting(key);
    if (target != null) return true;
    return false;
  }

  @Override
  public boolean canExecute(String command) {
    Model target;
    target = this.getResponsibleForCommand(command);
    if (target != null) return true;
    return false;
  }

  @Override
  public boolean canList(String list) {
    Model target;
    target = this.getResponsibleForList(list);
    if (target != null) return true;
    return false;
  }

  @Override
  public String getSetting(String key, String defaultValue) {
    Model target;
    target = this.getResponsibleForSetting(key);
    if (target != null) return target.getSetting(key, defaultValue);
    else
      throw new RuntimeException("Key " + key + " is not supported by the model");
  }

  @Override
  public void setSetting(String key, String value) {
    Model target;
    target = this.getResponsibleForSetting(key);
    if (target != null) target.setSetting(key, value);
    else
      throw new RuntimeException("Key " + key + " is not supported by the model");
  }

  @Override
  public List<String> execute(String command, String target, String... arguments) {
    Model targetModel;
    targetModel = this.getResponsibleForCommand(command);
    if (targetModel != null) return targetModel.execute(command, target, arguments );
    else
      throw new RuntimeException("Command " + command + " is not supported by the model");
  }

  @Override
  public List<String> getList(String list, String parameter) {
    Model target;
    target = this.getResponsibleForList(list);
    if (target != null) return target.getList(list, parameter);
    else
      throw new RuntimeException("List " + list + " is not supported by the model");
  }
  
  private Model getResponsibleForSetting(String key) {
    for (Model subModel : subModels)
      if (subModel.hasSetting(key)) return subModel;
    return null;
  }
  
  private Model getResponsibleForCommand(String command) {
    for (Model subModel : subModels)
      if (subModel.canExecute(command)) return subModel;
    return null;
  }
  
  private Model getResponsibleForList(String list) {
    for (Model subModel : subModels)
      if (subModel.canList(list)) return subModel;
    return null;
  }

 
  
  
}
