/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonygame.uitool;

import colonygame.Main;
import colonygame.ui.UIButton;
import colonygame.resources.BuildingType;
import java.awt.Cursor;

/**
 *
 * @author WilCecil
 */
public class Build implements Tool{

    UIButton parent;
    BuildingType building;

    public Build(UIButton parent, BuildingType building) {
        this.parent = parent;
        this.building = building;
    }
    
    public Build( BuildingType building) {
        this.building = building;
    }
    
    
    
    
    

    @Override
    public boolean interact(int x, int y, int z) {
        return Main.game.build(building, x, y, z);
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
        return "Build "+building.toString();
    }

}
