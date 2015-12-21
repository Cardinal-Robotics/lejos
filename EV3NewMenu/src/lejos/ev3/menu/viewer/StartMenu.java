package lejos.ev3.menu.viewer;

import java.nio.file.Paths;

import lejos.ev3.menu.components.EditorNumeric;
import lejos.ev3.menu.components.Icons;
import lejos.ev3.menu.control.EV3Control;
import lejos.ev3.menu.control.Control;
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
    
    DetailBase.setControl(control);
    DetailBase.setMenu(menu);
    ItemBase.setControl(control);
    ItemBase.setMenu(menu);
    
    DetailFile.setMaxLength(Config.DETAIL.width / Config.DETAIL.font.width);

    MenuItem top = new ItemBase( "Top", Icons.EV3)
        .addChild(
            new ItemBase( "leJOS EV3", Icons.EYE)
                .addDetail(new DetailRunDefault())
                .addDetail(new DetailStringValue( "Name", "HOSTNAME", "%s: %s"))
                .addDetail(new DetailStringValue( "Version", "VERSION", "%s: %s"))
                .addDetail(new DetailStringValue( "Wifi", "WIFI_WLAN0", "%s: %s"))
                .addDetail(new DetailStringValue( "PAN", "WIFI_BR0", "%s: %s")))
        .addChild(new ItemFiles( ItemFiles.PROGRAMS_DIRECTORY))
        .addChild(new ItemFiles( ItemFiles.SAMPLES_DIRECTORY ))
        .addChild(new ItemFiles( ItemFiles.TOOLS_DIRECTORY))
        .addChild(new ItemFiles( Paths.get("/home")))
        .addChild(
            new ItemBase( "Sound", Icons.EYE)
                .addDetail(new DetailNumericValue( "Volume", "VOLUME", "%s : %4d", EditorNumeric.class))
                .addDetail(new DetailNumericValue( "Key Vol", "keyVolume", "%s : %4d", EditorNumeric.class))
                .addDetail(new DetailNumericValue( "Key length", "keyLength", "%s : %4d", EditorNumeric.class))
                .addDetail(new DetailNumericValue( "Key freq", "keyFreq", "%s : %4d", EditorNumeric.class)))

    ;
    menu.setMenu(top);
    menu.run();

  }

}
