/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonygame.ui;

import colonygame.game.Building;
import java.awt.Color;
import java.awt.event.ActionListener;

/**
 *
 * @author WilCecil
 */
public class UIButtonBuilding extends UIButton{

    Building building;
    
    public UIButtonBuilding(int x, int y, int width, int height, 
            Building blding, 
            Color cBg, Color cText, 
            ActionListener e) {
        super(x, y, width, height, null, cBg, cText, e);
        building = blding;
    }

    @Override
    public String getText() {
        if(building.isDisabled()){
            cBg = selected;
            
            return "Disabled";
            
        }else
            cBg = unSelected;
            
            return "Enabled";
    }
}
