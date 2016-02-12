package lejos.ev3.menu.presenter;

import java.util.List;

import lejos.ev3.menu.viewer.Editor;


public class BtDetail extends SettingDetail  {

  public BtDetail(String key, String label, String format, String defaultValue, Class<? extends Editor> editor) {
    super(key, label, format, defaultValue, editor);
    if (key.equals("bluetooth.visibility")) {
      addSpecialValue("true", "visible");
      addSpecialValue("false", "invisible");
    }
    else
      selectable = false;
    model.attach(key, this);
  }

public BtDetail(String key, String label, String format, String defaultValue) {
    this(key, label, format, defaultValue, null);
  }

@Override
public void refresh() {
  super.refresh();
  value=model.getSetting(key, defaultValue);
}

@Override
public void setValue(String value) {
  model.setSetting(key, value);
}

@Override
protected List<String> execute() {
  try {
    Editor edit = this.editor.newInstance();
    edit.edit(this);
  } catch (InstantiationException e) {
    e.printStackTrace();
  } catch (IllegalAccessException e) {
    e.printStackTrace();
  }
  return null;
}

@Override
public void keyChanged(String key, String newValue) {
  isFresh = false;
}

}
