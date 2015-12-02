package lejos.ev3.menu.viewer;


import lejos.ev3.menu.control.Control;
import lejos.ev3.menu.control.MenuControl;
import lejos.ev3.menu.model.*;

public class StartMenu {

  public static void main(String[] args) {
    MenuControl control = new Control();
    Node top;
   
//    top = new BaseNode(control,"Top", Icons.EV3)
//                   .addChild(new BaseNode(control,"System", Icons.EV3)
//                     .addDetail(new BaseDetail(control, "Name :", "EV3", "%s", Detail.TYPE_INFO))
//                     .addDetail(new BaseDetail(control, "VM :", "Lejos EV3", "%s", Detail.TYPE_INFO))
//                     .addDetail(new BaseDetail(control, "Version :", "0.9.1-beta", "%s", Detail.TYPE_INFO))
//                     .addDetail(new BaseDetail(control, "IP :", "0:0:0:0", "%s", Detail.TYPE_INFO))
//                     .addDetail(new BaseDetail(control, "IP :", "0:0:0:0", "%s", Detail.TYPE_INFO))
//                     .addChild(new BaseNode(control,"Sound",Icons.SOUND)
//                       .addDetail(new BasePropertyDetail(control, "Volume  :", "volume", "%3d", 0 ))
//                       .addDetail(new BasePropertyDetail(control, "Key Vol :", "keyVolume", "%3d", 0 ))
//                       .addDetail(new BasePropertyDetail(control, "Key length :", "keyLength", "%3d", 0 ))
//                       .addDetail(new BasePropertyDetail(control, "Key freq:", "keyFreq", "%5d", 0 )) 
//                     )
//                     .addChild(new BaseNode(control, "Tools", Icons.TOOLS)
//                       .addChild(new NodeFiles(control, "Tools", Icons.TOOLS, NodeFiles.TOOLS_DIRECTORY, ".jar"))
//                     .addChild(new BaseNode(control, "Settings", Icons.LEJOS))
//                   )
//                   .addChild(new NodeFiles(control, "Programs", Icons.PROGRAM, NodeFiles.PROGRAMS_DIRECTORY, ".jar"))
//                   .addChild(new NodeFiles(control, "Samples", Icons.PROGRAM, NodeFiles.SAMPLES_DIRECTORY, ".jar"))
//                   )
//                   ;

    top = new BaseNode(control,"Top", Icons.EV3)
      .addChild(new BaseNode(control,"System", Icons.EV3)
       .addDetail(new BaseDetail(control, "", "Brick1", "%s", Detail.TYPE_INFO))
       .addDetail(new BaseDetail(control, "", "Lejos EV3", "%s", Detail.TYPE_INFO))
       .addDetail(new BaseDetail(control, "", "0.9.1-beta", "%s", Detail.TYPE_INFO))
       .addDetail(new BaseDetail(control, "", "0:0:0:0", "%s", Detail.TYPE_INFO))
       .addDetail(new BaseDetail(control, "", "0:0:0:0", "%s", Detail.TYPE_INFO))
       .addChild(new BaseNode(control,"Sound",Icons.SOUND)
         .addDetail(new BasePropertyDetail(control, "Volume  :", "volume", "%3d", 0 ))
         .addDetail(new BasePropertyDetail(control, "Key Vol :", "keyVolume", "%3d", 0 ))
         .addDetail(new BasePropertyDetail(control, "Key length :", "keyLength", "%3d", 0 ))
         .addDetail(new BasePropertyDetail(control, "Key freq:", "keyFreq", "%5d", 0 )) 
         )
       )
      .addChild(new NodeFiles(control, "Programs", Icons.PROGRAM, NodeFiles.PROGRAMS_DIRECTORY, ".jar"))
      .addChild(new NodeFiles(control, "Samples", Icons.PROGRAM, NodeFiles.SAMPLES_DIRECTORY, ".jar"))
    ;

    
   Menu menu = new Menu(top);
   menu.run();
   
   


  }

}
