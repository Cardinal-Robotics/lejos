package lejos.ev3.menu.control;

import java.util.ArrayList;
import java.util.List;

import lejos.ev3.menu.model.*;
import lejos.ev3.menu.viewer.Menu;

public class StartMenu {

  public static void main(String[] args) {
    MenuControl control = new Control();
   List<Node> nodes = new ArrayList<Node>();
   nodes.add(new NodeSystem(control));
   nodes.add(new NodePrograms(control));
   nodes.add(new NodeSamples(control));   
   
   Node top = new NodeTop(control)
                   .addChild(new NodeSystem(control)
                     .addChild(new NodeSounds(control))
                     .addChild(new NodeTools(control))
                     .addChild(new NodeSettings(control))
                     .addChild(new NodeLibs(control))
                   )
                   .addChild(new NodePrograms(control))
                   .addChild(new NodeSamples(control))
                   .addChild(new NodeConnect(control)
                     .addChild(new NodeWifi(control))
                     .addChild(new NodePAN(control))
                     .addChild(new NodeBT(control))
                   )
                   ;
   Menu menu = new Menu(top);
   menu.run();

  }

}
