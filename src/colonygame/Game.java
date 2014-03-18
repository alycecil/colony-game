/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame;

import colonygame.game.Building;
import colonygame.event.BuildEvent;
import colonygame.event.GameEvent;
import colonygame.resources.BuildingType;
import colonygame.resources.WorldMap;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author WilCecil
 */
public class Game implements Runnable {

    public static final int NUM_LANDERS = 1;
    int myLanderCount;
    int timeStamp;
    public Random rnd;
    WorldMap map;
    ArrayList<BuildingType> buildables;
    PriorityQueue<GameEvent> events;

    public Game(long seed) {
        timeStamp = 0;
        myLanderCount = 0;

        rnd = new Random(seed);

        Set<String> maps = Main.resources.getMaps();

        map = Main.resources.getMap(
                maps.toArray()[rnd.nextInt(maps.size())].toString());


        buildables = new ArrayList<>();

        addInitialBuildings();

        //init game queue
        events = new PriorityQueue<>();
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

    public boolean build(BuildingType type, int x, int y, int z) {

        if (map.getBuilding(x, y, z) == null && 
                map.getTile(x, y, z)!=WorldMap.IMPASSIBLE) {

            if (type.isType(BuildingType.TYPE_LANDER)) {
                if (myLanderCount < NUM_LANDERS) {
                    myLanderCount++;

                    if (myLanderCount >= NUM_LANDERS) {
                        removeLanders();
                        Main.ui.getBuildmenu().update();
                        Main.ui.setCurrentTool(null);
                    }

                    map.setBuilding(x, y, z, new Building(Main.resources.getContruction(), 0));
                    return events.offer(new BuildEvent(timeStamp + type.getBuildtime(), type, x, y, z));
                }
                return false;
            } else {
                map.setBuilding(x, y, z, new Building(Main.resources.getContruction(), 0));
                return events.offer(new BuildEvent(timeStamp + type.getBuildtime(), type, x, y, z));
            }
        }
        return false;
    }

    public ArrayList<BuildingType> getBuildables() {
        return buildables;
    }

    private void removeLanders() {
        //scan resources and remove all special type lander buildings

        ArrayList<BuildingType> buildings =
                Main.resources.getBuildings(BuildingType.TYPE_LANDER);

        buildables.removeAll(buildings);
    }
}
