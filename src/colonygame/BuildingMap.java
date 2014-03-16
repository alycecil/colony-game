/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonygame;

import colonygame.resources.WorldMap;

/**
 *
 * @author WilCecil
 */
public class BuildingMap {

    Building[][][] buildings;

    public BuildingMap(WorldMap map) {
        buildings = new Building[map.getDepth()][map.getWidth()][map.getHeight()];
    }
    
    
}
