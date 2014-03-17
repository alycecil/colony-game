/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame;

import colonygame.resources.BuildingType;
import colonygame.resources.WorldMap;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author WilCecil
 */
public class Game implements Runnable {

    public static final int NUM_LANDERS = 1;
    int timeStamp;
    public Random rnd;
    WorldMap map;
    ArrayList<BuildingType> buildables;

    public Game(long seed) {
        timeStamp = 0;

        rnd = new Random(seed);

        Set<String> maps = Main.resources.getMaps();
        
        map = Main.resources.getMap(
                maps.toArray()[rnd.nextInt(maps.size())].toString());
        
        
        buildables= new ArrayList<>();
        
        addInitialBuildings();
    }

    @Override
    public void run() {


        //
        //Update Population
        //
        simulatePopulation();


        //
        //Update Resources
        //
        updateResources();


        //
        // Update Buildings
        //
        updateBuildings();


        //
        //Update Time
        //
        tick();
    }

    private void simulatePopulation() {
        //
    }

    private void updateResources() {
        //
    }

    private void updateBuildings() {
        //update construction
        //update current buildings
    }

    private void tick() {
        timeStamp++;
    }

    /**
     * Get current tick
     *
     * @return
     */
    public int getTimeStamp() {
        return timeStamp;
    }

    /**
     * get the map
     *
     * @return
     */
    public WorldMap getMap() {
        return map;
    }

    private void addInitialBuildings() {
        //scan resources and add all special type lander buildings
        //these building willonlybe allowed to be built a set number of times
        ArrayList<BuildingType> buildings = Main.resources.getBuildings(BuildingType.TYPE_LANDER);
        
        buildables.addAll(buildings);
        
    }
    
    
    public boolean build(BuildingType type, int x, int y, int z){
        return false;
    }
}
