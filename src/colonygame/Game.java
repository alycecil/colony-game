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
import java.util.logging.Level;
import java.util.logging.Logger;

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
    ArrayList<Short> unlockedTech;

    public Game(long seed) {
        timeStamp = 0;
        myLanderCount = 0;

        rnd = new Random(seed);

        Set<String> maps = Main.resources.getMaps();

        map = Main.resources.getMap(
                maps.toArray()[rnd.nextInt(maps.size())].toString());


        unlockedTech = new ArrayList<>();
        unlockedTech.add((short)0);

        buildables = new ArrayList<>();

        addInitialBuildings();

        //init game queue
        events = new PriorityQueue<>();
    }

    @Override
    public void run() {

        //
        //Update Time
        //
        tick();
        
        //
        // process event queue
        //
        processEvents();

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

        if (map.getBuilding(x, y, z) == null
                && map.getTile(x, y, z) != WorldMap.IMPASSIBLE) {

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

    /**
     * Unlocks Tech One
     */
    private void removeLanders() {
        //scan resources and remove all special type lander buildings

        ArrayList<BuildingType> buildings =
                Main.resources.getBuildings(BuildingType.TYPE_LANDER);

        buildables.removeAll(buildings);

        addTech((short)1);
    }

    /**
     * adds specified tech level to buildables ensures the level is only added
     * once, by checking a List of Integers
     *
     * @param i
     */
    private void addTech(short tech) {
        //ensure not unlocked
        if (!unlockedTech.contains(tech)) {

            //unlock add
            unlockedTech.add(tech);
            
            //get tech from resources
             ArrayList<BuildingType> buildings = 
                     Main.resources.getBuildingsTech(tech);
             
             //add all
             buildables.addAll(buildings);
        }
    }

    private void processEvents() {
        while(events.peek()!=null && events.peek().getTime()<=timeStamp){
            GameEvent e =events.poll();
            if(!e.doEvent()){
                Logger.getLogger(Game.class.getName()).log(
                    Level.WARNING, "Event Failed : {0}", e);
            }
        }
        
    }
    
    
}
