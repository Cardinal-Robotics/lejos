package lejos.ev3.menu.viewer;

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
            new ItemBase(control, "System", Icons.EYE).addDetail(new DetailStringValue(control, "Name", "BRICKNAME", "%s: %s"))
                .addDetail(new DetailStringValue(control, "Version", "VERSION", "%s: %s")).addDetail(new DetailLabel("0:0:0:0"))
                .addDetail(new DetailLabel("0:0:0:0")))
        .addChild(new ItemFiles(control, ItemFiles.PROGRAMS_DIRECTORY, ".jar"))
        .addChild(new ItemFiles(control, ItemFiles.SAMPLES_DIRECTORY, ".jar"))
        .addChild(new ItemFiles(control, ItemFiles.TOOLS_DIRECTORY, ".jar"))
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
