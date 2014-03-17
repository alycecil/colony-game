/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WilCecil
 */
public class UIManager implements ActionListener {

    public UIManager() {
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (Main.ui != null) {

            Main.ui.renderFrame();


        } else {
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, "UI Is closed!");
        }
    }
}
