/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonygame.game;

import colonygame.resources.BuildingType;

/**
 *
 * @author WilCecil
 */
public class Building {

    BuildingType type;
    
    int current;

    public Building(BuildingType type, int current) {
        this.type = type;
        this.current = current;
    }

    public BuildingType getType() {
        return type;
    }

    public int getCurrent() {
        return current;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
    
    
    
}
