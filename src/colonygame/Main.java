package colonygame;

import colonygame.ui.UI;
import colonygame.ui.UIManager;
import colonygame.resources.Resources;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author WilCecil
 */
public class Main {

    public static final String SETTINGS_FILE = "settings.ini";
    public static UI ui;
    public static Resources resources;
    public static Game game;
    public static GameManager manager;
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            
            /*
             * Load Resources
             */
            
            resources = new Resources();

            resources.loadXML();


            game = new Game(System.currentTimeMillis());
            
            /*
             * Start UI 
             */
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Main.ui = new UI();
                    Main.ui.setVisible(true);
                }
            });

            /**
             * start UI Manager
             */
            new Timer(20, new UIManager()).start();
            
            /*
             * Start Game Loop
             */
            manager = new GameManager();
            manager.start();
            

            
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
