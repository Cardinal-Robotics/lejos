package lejos.ev3.menu.viewer;

import lejos.ev3.menu.components.Icons;
import lejos.ev3.menu.control.EV3Control;
import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.model.Model;
import lejos.ev3.menu.model.ModelContainer;
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
    Model model = ModelContainer.getModel();
    BaseDetail.setEnvironment(control, model, menu);
    ItemBase.setEnvironment(control, model, menu);

    MenuItem top = new ItemBase( "Top", Icons.EV3)
        .addChild(
            new ItemBase( "leJOS EV3", Icons.LEJOS)
                .addDetail(new RunDefault())
                .addDetail(new SettingDetail( "wlan0", "LAN",  "%2$s: %3$s", ""))
                .addDetail(new SettingDetail( "br0", "PAN",  "%2$s: %3$s", "")))
        .addChild(
            new ItemBase( "System", Icons.EV3)
                .addDetail(new SettingDetail( "system.hostname", "Name", "%2$s: %3$s", "",EditorString.class))
                .addDetail(new SettingDetail( "lejos.version", "Ver",  "%2$s: %3$s", ""))
                .addDetail(new DynamicDetail( "system.time", "Time",  "%2$s: %3$s", ""))
                .addDetail(new DynamicDetail( "system.volt", "Battery",  "%2$s: %3$s", ""))
                .addDetail(new DynamicDetail( "system.current", "Current",  "%2$s: %3$s", "")))
        .addChild(
            new ItemBase( "Wifi", Icons.WIFI)
                .addDetail(new SsidDetail("ssid","SSID","%2$s: %3$s"))
                .addDetail(new SettingDetail( "wlan0", "IP",  "%2$s: %3$s", "")))
        .addChild(
            new ItemBase("BlueTooth", Icons.BLUETOOTH)
                .addDetail(new SubmenuDetail("Pair").addChild( new BtDevices("Pair", Icons.SEARCH)))
                .addDetail(new SubmenuDetail("Devices").addChild( new BtPairedDevices("Devices", Icons.BLUETOOTH)))
                .addDetail(new BtDetail( "bluetooth.visibility","Visibility", "%2$s: %3$5s", "false", EditorBoolean.class))
                .addDetail(new SettingDetail( "bluetooth.pin","PIN:", "%2$s %3$s","1234", EditorBtKey.class))
                .addDetail(new BtDetail( "bluetooth.address","Address", "%3$s", "?"))
                .addDetail(new BtDetail( "bluetooth.name","Name", "%3$s", "?"))
        )        
        .addChild(new Files( Files.PROGRAMS_DIRECTORY, Icons.PROGRAM))
        .addChild(new Files( Files.SAMPLES_DIRECTORY, Icons.SAMPLES ))
        .addChild(new Files( Files.TOOLS_DIRECTORY, Icons.TOOLS))
        .addChild(new Files( Files.LIB_DIRECTORY, Icons.FILES))
        .addChild(new Files( "/home/root/lejos/config", Icons.FILES))
        .addChild(
            new ItemBase( "Sound", Icons.EYE)
                .addDetail(new SettingDetail( "audio.volume","Volume", "%2$s : %3$4s", "30", EditorNumeric.class))
                .addDetail(new SettingDetail( "lejos.keyclick_volume","Key vol", "%2$s : %3$4s", "30", EditorNumeric.class))
                .addDetail(new SettingDetail( "lejos.keyclick_length","Key length", "%2$s : %3$4s", "30", EditorNumeric.class))
                .addDetail(new SettingDetail( "lejos.keyclick_frequency","Key freq", "%2$s : %3$4s", "800" , EditorNumeric.class)))
        .addChild(new ItemBase("PAN",Icons.ACCESSPOINT)
                .addChild(new ItemBase("PAN mode",Icons.PAN)
                    .addDetail(new PanMode("NONE"))
                    .addDetail(new PanMode("AP"))
                    .addDetail(new PanMode("AP+"))
                    .addDetail(new PanMode("BT"))
                    .addDetail(new PanMode("USB"))
                    )
                .addDetail(new PanSetting( ))
                .addDetail(new SettingDetail( "br0", "IP",  "%2$s: %3$s", ""))
        )
    ;
    menu.setMenu(top);
    menu.run();

  }

}
