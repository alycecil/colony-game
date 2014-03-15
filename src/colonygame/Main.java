package colonygame;

import colonygame.resources.Resources;
import colonygame.resources.WorldMap;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WilCecil
 */
public class Main {

    public static final String SETTINGS_FILE = "settings.ini";
    public static UI ui;
    public static WorldMap map;
    public static Resources resources;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here

            resources = new Resources();
            
            resources.loadXML();
            
            
            //ui = new UI();

            //ui.prep();

            
            
            /*
             java.awt.EventQueue.invokeLater(new Runnable() {
             @Override
             public void run() {
             Main.ui.renderFrame();
             try {
             this.wait(1000);
             } catch (InterruptedException e) {
             Main.ui.error(e);
             }
             }
             }*/
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
