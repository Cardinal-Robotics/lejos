package lejos.ev3.menu.viewer;

import java.io.File;
import java.nio.file.Paths;

import lejos.ev3.menu.control.EV3Control;
import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.model.*;

/**
 * Defines the menu structure of the leJOS menu and starts the menu
 * 
 * @author Aswin Bouwmeester
 *
 */
public class StartMenu {

  public static void main(String[] args) {
    Control control = new EV3Control();
    MenuItem top;

    top = new ItemBase(control, "Top", Icons.EV3)
        .addChild(
            new ItemBase(control, "leJOS EV3", Icons.EYE)
                .addDetail(new DetailRunDefault(control))
                .addDetail(new DetailStringValue(control, "Name", "HOSTNAME", "%s: %s"))
                .addDetail(new DetailStringValue(control, "Version", "VERSION", "%s: %s"))
                .addDetail(new DetailStringValue(control, "Wifi", "WIFI_WLAN0", "%s: %s"))
                .addDetail(new DetailStringValue(control, "PAN", "WIFI_BR0", "%s: %s")))
        .addChild(new ItemFiles(control, ItemFiles.PROGRAMS_DIRECTORY))
        .addChild(new ItemFiles(control, ItemFiles.SAMPLES_DIRECTORY ))
        .addChild(new ItemFiles(control, ItemFiles.TOOLS_DIRECTORY))
        .addChild(new ItemFiles(control, Paths.get("/home")))
        .addChild(
            new ItemBase(control, "Sound", Icons.EYE)
                .addDetail(new DetailNumericValue(control, "Volume", "VOLUME", "%s : %4d", EditorNumeric.class))
                .addDetail(new DetailNumericValue(control, "Key Vol", "keyVolume", "%s : %4d", EditorNumeric.class))
                .addDetail(new DetailNumericValue(control, "Key length", "keyLength", "%s : %4d", EditorNumeric.class))
                .addDetail(new DetailNumericValue(control, "Key freq", "keyFreq", "%s : %4d", EditorNumeric.class)))

    ;

    GraphicMenu menu = new GraphicMenu(top);
    menu.run();

  }

}
