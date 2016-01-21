package lejos.ev3.menu.viewer;

import lejos.ev3.menu.components.Icons;
import lejos.ev3.menu.control.EV3Control;
import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.model.Model;
import lejos.ev3.menu.presenter.*;

/**
 * Defines the menu structure of the leJOS menu and starts the menu
 * 
 * @author Aswin Bouwmeester
 *
 */
public class StartMenu {

  public static void main(String[] args) {
    Control control = new EV3Control();
    Menu menu = new GraphicMenu();
    Model model = Model.getModel();
    BaseDetail.setEnvironment(control, model, menu);
    ItemBase.setEnvironment(control, model, menu);

    MenuItem top = new ItemBase( "Top", Icons.EV3)
        .addChild(
            new ItemBase( "leJOS EV3", Icons.EYE)
                .addDetail(new RunDefault())
                .addDetail(new SettingDetail( "system.hostname", "Name", "%2$s: %3$s", ""))
                .addDetail(new SettingDetail( "system.lejos.version", "Version",  "%2$s: %3$s", ""))
                .addDetail(new SettingDetail( "system.wifi.wlan0", "LAN",  "%2$s: %3$s", ""))
                .addDetail(new SettingDetail( "system.wifi.br0", "PAN",  "%2$s: %3$s", "")))
        .addChild(
            new ItemBase("BlueTooth", Icons.BLUETOOTH)
                .addChild(new ItemBase("Configure BT", Icons.BLUETOOTH)
                          .addDetail(new BtDetail( "system.bluetooth.visibility","Visibility", "%2$s: %3$5s", "false", EditorBoolean.class))
                          .addDetail(new SettingDetail( "lejos.bluetooth.pin","PIN:", "%2$s %3$s","1234", EditorBtKey.class)))
                .addChild(new BtPairedDevices("Devices", Icons.BLUETOOTH))
                .addChild(new BtDevices("Pair", Icons.BLUETOOTH))
                          
                .addDetail(new BtDetail( "system.bluetooth.visibility","Visibility", "%2$s: %3$5s", "false"))
                .addDetail(new BtDetail( "system.bluetooth.address","Address", "%3$s", "?"))
                .addDetail(new BtDetail( "system.bluetooth.name","Name", "%3$s", "?"))
                .addDetail(new SettingDetail( "lejos.bluetooth.pin","PIN:", "%2$s %3$s","1234")) 
        )        
        .addChild(new Files( Files.PROGRAMS_DIRECTORY))
        .addChild(new Files( Files.SAMPLES_DIRECTORY ))
        .addChild(new Files( Files.TOOLS_DIRECTORY))
        .addChild(new Files( Files.LIB_DIRECTORY))
        .addChild(
            new ItemBase( "Sound", Icons.EYE)
                .addDetail(new SettingDetail( "lejos.volume","Volume", "%2$s : %3$4s", "30", EditorNumeric.class))
                .addDetail(new SettingDetail( "lejos.key.volume","Key vol", "%2$s : %3$4s", "30", EditorNumeric.class))
                .addDetail(new SettingDetail( "lejos.key.length","Key length", "%2$s : %3$4s", "30", EditorNumeric.class))
                .addDetail(new SettingDetail( "lejos.key.freq","Key freq", "%2$s : %3$4s", "800" , EditorNumeric.class)))
    ;
    menu.setMenu(top);
    menu.run();

  }

}
