/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame;

import colonygame.resources.WorldMap;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author WilCecil
 */
public class Game implements Runnable {

    int timeStamp;
    public Random rnd;
    WorldMap map;

    public Game() {
        timeStamp = 0;

        rnd = new Random();

        Set<String> maps = Main.resources.getMaps();
        
        map = Main.resources.getMap(
                maps.toArray()[rnd.nextInt(maps.size())].toString());

    }

    public Game(long seed) {
        timeStamp = 0;

        rnd = new Random(seed);

        Set<String> maps = Main.resources.getMaps();
        
        map = Main.resources.getMap(
                maps.toArray()[rnd.nextInt(maps.size())].toString());
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
}
