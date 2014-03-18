/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonygame.uitool;

import colonygame.game.Building;
import colonygame.Main;
import colonygame.ui.UIButton;
import colonygame.resources.WorldMap;
import java.awt.Cursor;

/**
 *
 * @author WilCecil
 */
public class Bulldoze implements Tool{

    UIButton parent;

    public Bulldoze() {
    }

    public Bulldoze(UIButton parent) {
        this.parent = parent;
    }
    
    
    
    @Override
    public boolean interact(int x, int y, int z) {
        
        //get map tile
        char tile = Main.game.getMap().getTile(x,y,z);
        Building b = Main.game.getMap().getBuilding(x, y, z);
        //is tile not impassible?
        if(tile != WorldMap.IMPASSIBLE && b == null){
        //if so doze tile
            Main.game.getMap().setTile(x,y,z, WorldMap.DOZED);
            return true;
        }
        return false;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getDefaultCursor();
    }

    @Override
    public boolean unselect() {
        if(parent!=null){
            parent.unclick();
        }
        return true;
    }

    @Override
    public boolean select() {
        return true;
    }

    @Override
    public String toString() {
        return "Bulldozer";
    }
    
    

}
