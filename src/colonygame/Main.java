package colonygame;

import colonygame.resources.WorldMap;

/**
 *
 * @author WilCecil
 */
public class Main {

    public static final String SETTINGS_FILE = "/settings.ini";
    public static UI ui;
    public static WorldMap map;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        ui = new UI();

        ui.prep();


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
        });
    }
}
