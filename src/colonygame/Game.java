/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame;

import colonygame.game.Building;
import colonygame.event.BuildEvent;
import colonygame.event.GameEvent;
import colonygame.game.Person;
import colonygame.resources.BuildingType;
import colonygame.resources.WorldMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WilCecil
 */
public class Game implements Runnable, ActionListener {

    int myLanderCount;
    int timeStamp;
    //
    //resources
    //
    int powerTotal;
    int power;
    int housingTotal;
    int workerNeed;
    int agriculture;
    int agrigultureStored;
    int ore;
    public Random rnd;
    WorldMap map;
    ArrayList<BuildingType> buildables;
    PriorityQueue<GameEvent> events;
    ArrayList<Short> unlockedTech;
    /**
     * we are detailed tracking individuals as for whatever reason I want to do
     * it that way, if you hate it or its slow you can remove the person class
     * and do a DES with simplified classes of people as ints.
     */
    ArrayList<Person> people;
    ArrayList<Person> dead;

    public Game(long seed) {
        timeStamp = 0;
        myLanderCount = 0;
        powerTotal = 0;
        power = 0;
        housingTotal = 0;
        agriculture = 0;
        agrigultureStored = 0;
        ore = 0;

        rnd = new Random(seed);

        Set<String> maps = Main.resources.getMaps();

        map = Main.resources.getMap(
                maps.toArray()[rnd.nextInt(maps.size())].toString());


        unlockedTech = new ArrayList<>();
        unlockedTech.add((short) 0);

        buildables = new ArrayList<>();

        addInitialBuildings();

        //init game queue
        events = new PriorityQueue<>();


        //init people
        people = new ArrayList<>();

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
        //Update Resources
        //
        updateResources();

        //
        // Update Buildings
        //
        updateBuildings();

        //
        //Update Population
        //
        simulatePopulation();

    }

    private void simulatePopulation() {

        // move the dead
        Person p;

        for (int i = 0; i < people.size(); i++) {
            p = people.get(i);

            if (!p.isAlive()) {
                dead.add(p);
                people.remove(i);
            }
        }


    }

    private void updateResources() {
        //Calculate Power
        Collection<BuildingType> list;
        Iterator<BuildingType> iter;
        BuildingType temp;

        list = Main.resources.getBuildings();

        iter = list.iterator();

        while (iter.hasNext()) {
            temp = iter.next();

            //
            // update power //
            //
            powerTotal += temp.getSupplyPower();
            power += temp.getPower();


            //
            // Update Housing/worker Resources //
            //
            housingTotal += temp.getSupplyHousing();
            workerNeed += temp.getCapacity();


            //
            // update food
            //
            agriculture += temp.getSupplyFood();

            //
            // update ore
            //
            ore += temp.getSupplyOre();
        }
        
        //food!
        agrigultureStored+=agriculture;
        agrigultureStored-=people.size();
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
                if (myLanderCount < Main.resources.getSettings().getLanders()) {
                    myLanderCount++;

                    if (myLanderCount >= Main.resources.getSettings().getLanders()) {
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

        //add tech
        addTech((short) 1);


        //seed people
        seedPeople();
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
        while (events.peek() != null && events.peek().getTime() <= timeStamp) {
            GameEvent e = events.poll();
            if (!e.doEvent()) {
                Logger.getLogger(Game.class.getName()).log(
                        Level.WARNING, "Event Failed : {0}", e);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        run();
    }

    private void seedPeople() {
        //ensure no null pointer noise
        if (people == null) {
            people = new ArrayList<>();
        }

        //ensure not already seeded.
        if (!people.isEmpty()) {
            Logger.getLogger(Game.class.getName()).log(
                    Level.WARNING, "Attempting to seed, a seeded colony.");

            return;
        }

        //add em in
        people.addAll(Main.resources.getSettings().getPeople());

        agrigultureStored = 500;
    }

    public int getPopulation() {
        if (people == null) {
            return -1;
        } else {
            return people.size();
        }
    }
}
