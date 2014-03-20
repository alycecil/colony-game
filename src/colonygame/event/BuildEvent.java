/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.event;

import colonygame.game.Building;
import colonygame.Main;
import colonygame.resources.BuildingType;
import colonygame.resources.WorldMap;

/**
 *
 * @author WilCecil
 */
public class BuildEvent extends GameEvent {

    int time;
    BuildingType type;
    int x, y, z;

    public BuildEvent(int time, BuildingType type, int x, int y, int z) {
        this.time = time;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public boolean doEvent() {
        try {
            Main.game.finishBuilding(x,y,z,new Building(type, 0));

            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    
}
