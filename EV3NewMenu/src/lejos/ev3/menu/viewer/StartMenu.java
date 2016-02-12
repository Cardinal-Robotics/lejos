package lejos.ev3.menu.viewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
    long start;

    Menu menu = GrMenu.getMenu();

    Control control = new EV3Control();

    Model model = ModelContainer.getModel();

    BaseDetail.setEnvironment(control, model, menu);
    BaseNode.setEnvironment(control, model, menu);
    Map<String, String> panModes = new LinkedHashMap<String, String>();
    panModes.put("NONE", "Disable");
    panModes.put("AP", "Access Pt");
    panModes.put("AP+", "Access Pt+");
    panModes.put("BT", "BT Client");
    panModes.put("USB", "USB Client");
    ArrayList<Node> top = new ArrayList<Node>();
    top.add(new BaseNode("leJOS EV3", Icons.LEJOS).addDetail(new RunDefault())
        .addDetail(new SettingDetail("wlan0", "LAN", "%2$s: %3$s", null).addSpecialValue(null, "No Wifi"))
        .addDetail(new SettingDetail("br0", "PAN", "%2$s: %3$s", null).addSpecialValue(null, "No PAN")));
    top.add(new BaseNode("System", Icons.EV3).addDetail(new SettingDetail("system.hostname", "Name", "%2$s: %3$s", "", EditorString.class))
        .addDetail(new SettingDetail("lejos.version", "Ver", "%2$s: %3$s", ""))
        .addDetail(new DynamicDetail("system.time", "Time", "%2$s: %3$s", ""))
        .addDetail(new DynamicDetail("system.volt", "Battery", "%2$s: %3$s", ""))
        .addDetail(new DynamicDetail("system.current", "Current", "%2$s: %3$s", "")));
    top.add(new BaseNode("Wifi", Icons.WIFI)
        .addDetail(new SettingDetail("ssid", "SSID", "%2$s: %3$s", "", new LanNode("Access points", Icons.WIFI)))
        .addDetail(new SettingDetail("wlan0", "IP", "%2$s: %3$s", "")));
     top.add(new BaseNode("BlueTooth", Icons.BLUETOOTH)
        .addDetail(new SubmenuDetail("Pair", new BtDevices("Pair", Icons.SEARCH)))
        .addDetail(new SubmenuDetail("Devices", new BtPairedDevices("Devices", Icons.BLUETOOTH)))
        .addDetail(new BtDetail( "bluetooth.visibility","Visibility", "%3$s", "false", EditorBoolean.class))
        .addDetail(new SettingDetail( "bluetooth.pin","PIN:", "%2$s %3$s","1234", EditorBtKey.class))
        .addDetail(new BtDetail( "bluetooth.address","Address", "%3$s", "?"))
        .addDetail(new BtDetail( "bluetooth.name","Name", "%3$s", "?")));
    top.add(new Files(Files.PROGRAMS_DIRECTORY, Icons.PROGRAM));
    top.add(new Files(Files.SAMPLES_DIRECTORY, Icons.SAMPLES));
    top.add(new Files(Files.TOOLS_DIRECTORY, Icons.TOOLS));
    top.add(new Files(Files.LIB_DIRECTORY, Icons.FILES));
    top.add(new Files("/home/root/lejos/config", Icons.FILES));
    top.add(new BaseNode("Sound", Icons.EYE)
        .addDetail(new SettingDetail("audio.volume", "Volume", "%2$s : %3$4s", "30", EditorNumeric.class))
        .addDetail(new SettingDetail("lejos.keyclick_volume", "Key vol", "%2$s : %3$4s", "30", EditorNumeric.class))
        .addDetail(new SettingDetail("lejos.keyclick_length", "Key length", "%2$s : %3$4s", "30", EditorNumeric.class))
        .addDetail(new SettingDetail("lejos.keyclick_frequency", "Key freq", "%2$s : %3$4s", "800", EditorNumeric.class)));
    // top.add(new ItemBase("PAN",Icons.ACCESSPOINT)
    // .addChild(new ItemBase("Pan Config",Icons.PAN)
    // .addDetail(new IpDetailAddress())
    // .addDetail(new IpDetail("Pan.netmask", "Netmask"))
    // .addDetail(new IpDetail("Pan.broadcast", "Brdcast"))
    // .addDetail(new IpDetail("Pan.gateway", "Gateway"))
    // .addDetail(new IpDetail("Pan.dns", "DNS"))
    // .addDetail(new SettingDetail("Pan.persist", "Persist", "%2$s: %3$4s",
    // "N"))
    // .addDetail(new SettingDetail("Pan.service", "Service", "%2$s: %3$4s",
    // "NAP"))
    // )
    // .addDetail(new PanMode())
    // .addDetail(new PanIP())
    // );

    ;
    menu.runMenu(top);
  }

}

/*
 * .addChild(new ItemBase("PAN",Icons.ACCESSPOINT) .addDetail(new
 * SettingBySubmenu("Pan.mode", "mode", "%3$s", "NONE") .addChild(new
 * ItemBase("PAN mode",Icons.PAN) .addDetail(new
 * PanMode("NONE").addSpecialValue("NONE", "Disable")) .addDetail(new
 * PanMode("AP").addSpecialValue("AP", "Access Pt")) .addDetail(new
 * PanMode("AP+").addSpecialValue("AP+", "Access Pt+")) .addDetail(new
 * PanMode("BT").addSpecialValue("BT", "BT Client")) .addDetail(new
 * PanMode("USB").addSpecialValue("USB", "USB Client")) )
 * .addSpecialValue("NONE", "Disable") .addSpecialValue("AP", "Access Pt")
 * .addSpecialValue("AP+", "Access Pt+") .addSpecialValue("BT", "BT Client")
 * .addSpecialValue("USB", "USB Client") ) .addDetail(new
 * SettingBySubmenu("br0", "IP", "%2$s: %3$s", "") .addChild(new
 * ItemBase("Pan Config",Icons.PAN) .addDetail(new IpDetail("Pan.address",
 * "Address")) .addDetail(new IpDetail("Pan.netmask", "Netmask")) .addDetail(new
 * IpDetail("Pan.broadcast", "Brdcast")) .addDetail(new IpDetail("Pan.gateway",
 * "Gateway")) .addDetail(new IpDetail("Pan.dns", "DNS")) .addDetail(new
 * SettingDetail("Pan.persist", "Persist", "%2$s : %3$4s", "N")) .addDetail(new
 * SettingDetail("Pan.service", "Service", "%2$s : %3$4s", "NAP"))) ) )
 */

