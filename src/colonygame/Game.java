/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame;

import colonygame.game.Building;
import colonygame.event.BuildEvent;
import colonygame.event.GameEvent;
import colonygame.event.MarriageEvent;
import colonygame.event.ScienceEvent;
import colonygame.game.Person;
import colonygame.resources.BuildingType;
import colonygame.resources.Science;
import colonygame.resources.Settings;

import colonygame.resources.WorldMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.Collections;
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
    int scienceProduced;
    int medicineProduced;
    int medicineUsed;
    int ore;
    int workerCount;
    public static final long seed = System.currentTimeMillis();
    public static Random rnd = new Random(seed);
    WorldMap map;
    ArrayList<BuildingType> buildables;
    PriorityQueue<GameEvent> events;
    ArrayList<Integer> unlockedTech;
    ArrayList<Building> bld;
    ArrayList<Science> researched;
    Science currentGoal;
    ArrayList<String> log;
    /**
     * we are detailed tracking individuals as for whatever reason I want to do
     * it that way, if you hate it or its slow you can remove the person class
     * and do a DES with simplified classes of people as ints.
     */
    ArrayList<Person> people;
    ArrayList<Person> dead;
    ArrayList<Person> maleMinglers;
    ArrayList<Person> femaleMinglers;

    public Game() {
        timeStamp = 0;
        myLanderCount = 0;
        powerTotal = 0;
        power = 0;
        housingTotal = 0;
        agriculture = 0;
        agrigultureStored = 0;
        ore = 0;
        workerCount = 0;
        scienceProduced = 0;
        medicineProduced = 0;
        medicineUsed = 0;

        Set<String> maps = Main.resources.getMaps();

        map = Main.resources.getMap(
                maps.toArray()[rnd.nextInt(maps.size())].toString());


        unlockedTech = new ArrayList<>();
        unlockedTech.add((int) 0);

        buildables = new ArrayList<>();

        addInitialBuildings();

        //init game queue
        events = new PriorityQueue<>();


        //init people
        people = new ArrayList<>();
        dead = new ArrayList<>();


        //init blding
        bld = new ArrayList<>();


        //init science
        researched = new ArrayList<>();

        //init event log
        log = new ArrayList<>();
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
        // Update Buildings
        //
        updateBuildings();

        //
        //Update Resources
        //
        updateResources();

        //
        //Update Population
        //
        simulatePopulation();

        //
        //Update Science
        //
        updateScience();
    }

    private void simulatePopulation() {

        workerCount = 0;

        //iterate through our people
        Iterator<Person> pIter = people.iterator();
        Person focus;

        maleMinglers = new ArrayList<>();
        femaleMinglers = new ArrayList<>();

        while (pIter.hasNext()) {
            focus = pIter.next();

            //if we are homeless, or our house is too full
            if (focus.getHome() == null
                    || focus.getHome().getCurrent() > focus.getHome().getType().getCapacity()) {
                //move focus to a home
                move(focus);
            }

            //get singles ready to mingle
            if (!focus.isState(Person.STATE_MARRIED)
                    && (focus.isState(Person.STATE_TEEN)
                    || focus.isState(Person.STATE_ADULT))) {

                if (focus.isMale()) {
                    //add to male minglers
                    maleMinglers.add(focus);
                } else {
                    //add to female minglers
                    femaleMinglers.add(focus);
                }


            }


            //do we have available workers?
            if (focus.isState(Person.STATE_ADULT)) {
                workerCount++;
            }
        }

        //roll for a marriage
        if (!maleMinglers.isEmpty()
                && !femaleMinglers.isEmpty()
                && rnd.nextDouble() < Settings.DEFAULT_MARRIAGE_CHANCE * femaleMinglers.size()) {

            Person m = maleMinglers.remove(rnd.nextInt(maleMinglers.size()));
            Person f = femaleMinglers.remove(rnd.nextInt(femaleMinglers.size()));

            offerEvent(new MarriageEvent(m, f));
        }

        //simulation pass
        pIter = people.iterator();
        while (pIter.hasNext()) {

            focus = pIter.next();

            //simulate
            focus.simulate();

        }


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
        //Calculate Resources
        Iterator<Building> iter;
        BuildingType temp;
        Building temp_bld;

        //reset
        powerTotal = 0;
        power = 0;
        housingTotal = 0;
        agriculture = 0;
        scienceProduced = 0;
        medicineProduced = 0;
        medicineUsed = 0;
        
        double modifier =1.0;

        Collections.sort(bld);


        iter = bld.iterator();

        while (iter.hasNext()) {
            temp_bld = iter.next();

            //am i onlone?
            if (!temp_bld.isOnline()) {
                continue;
            }
            
            //am i not disabled
            if(temp_bld.isDisabled()){
                continue;
            }
            
            if(!temp_bld.isConected()){
                modifier = 0.75;
            }
            


            temp = temp_bld.getType();



            //
            // update power //
            //
            powerTotal += temp.getSupplyPower()*modifier;
            power += temp.getPower()*modifier;


            //
            // Update Housing/worker Resources //
            //
            housingTotal += temp.getSupplyHousing()*modifier;



            //
            // update food
            //
            agriculture += temp.getSupplyFood()*modifier;

            //
            // update ore
            //
            ore += temp.getSupplyOre()*modifier;


            //update science
            scienceProduced += temp.getSupplyScience()*modifier;

            //update medicine
            medicineProduced += temp.getSupplyMedical()*modifier;

        }

        //food!


        if (agriculture > 0 && agrigultureStored > agriculture * 100) {
            agrigultureStored = agriculture * 100;
        }

        agriculture -= people.size();
        agrigultureStored += agriculture;
    }

    private void updateBuildings() {
        //update current buildings
        Iterator<Building> iter;
        BuildingType temp;
        Building temp_bld;

        Building neighbor;

        workerNeed = 0;
        iter = bld.iterator();


        while (iter.hasNext()) {
            temp_bld = iter.next();
            temp = temp_bld.getType();

            //try and connect this building
            if (!temp_bld.isConected()) {
                //get neighbors
                //if a neighboris connected so am i


                //1
                neighbor = map.getBuilding(temp_bld.getX(), temp_bld.getY() - 1, temp_bld.getZ());

                if (neighbor != null && neighbor.isConected()) {
                    temp_bld.setConected(true);
                }

                //2
                neighbor = map.getBuilding(temp_bld.getX() + 1, temp_bld.getY(), temp_bld.getZ());

                if (neighbor != null && neighbor.isConected()) {
                    temp_bld.setConected(true);
                }

                //3
                neighbor = map.getBuilding(temp_bld.getX(), temp_bld.getY() + 1, temp_bld.getZ());

                if (neighbor != null && neighbor.isConected()) {
                    temp_bld.setConected(true);
                }

                //4
                neighbor = map.getBuilding(temp_bld.getX() - 1, temp_bld.getY(), temp_bld.getZ());

                if (neighbor != null && neighbor.isConected()) {
                    temp_bld.setConected(true);
                }

            }

            if (!temp_bld.isDisabled()) {
                if (powerTotal >= temp.getPower()
                        && workerCount >= temp.getCapacity()) {

                    temp_bld.setOnline(true);

                    powerTotal -= temp.getPower();
                    workerCount -= temp.getCapacity();
                    workerNeed += temp.getCapacity();
                } else {
                    temp_bld.setOnline(false);

//                workerNeed += temp.getCapacity();
                }
            }
        }




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

                    map.setBuilding(x, y, z, new Building(Main.resources.getContruction(), 0, x, y, z));
                    return events.offer(new BuildEvent(timeStamp + type.getBuildtime(), type, x, y, z));
                }
                return false;
            } else {
                map.setBuilding(x, y, z, new Building(Main.resources.getContruction(), 0, x, y, z));
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
        addTech((int) 1);


        //seed people
        seedPeople();
    }

    /**
     * adds specified tech level to buildables ensures the level is only added
     * once, by checking a List of Integers
     *
     * @param i
     */
    public void addTech(int tech) {


        //ensure not unlocked
        if (!unlockedTech.contains(tech)) {

            //unlock add
            unlockedTech.add(tech);

            //get tech from resources
            ArrayList<BuildingType> buildings =
                    Main.resources.getBuildingsTech(tech);

            //add all
            buildables.addAll(buildings);

            //sort
            Collections.sort(buildables);
        }
    }

    private void processEvents() {
        while (events.peek() != null && events.peek().getTime() <= timeStamp) {
            GameEvent e = events.poll();
            if (!e.doEvent()) {
                Logger.getLogger(Game.class.getName()).log(
                        Level.WARNING, "Event Failed : {0}", e);
            } else {
                log.add(e.logEvent());
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

    public int getAgriculture() {
        return agriculture;
    }

    public int getAgrigultureStored() {
        return agrigultureStored;
    }

    public int getHousingTotal() {
        return housingTotal;
    }

    public int getOre() {
        return ore;
    }

    public int getPower() {
        return power;
    }

    public int getPowerTotal() {
        return powerTotal;
    }

    public void finishBuilding(int x, int y, int z, Building b) {
        bld.add(b);
        if (z == 0) {
            map.setTile(x, y, z, WorldMap.DOZED);
        } else {
            map.setTile(x, y, z, WorldMap.UNDERGROUND_DOZED);
        }
        map.setBuilding(x, y, z, b);
    }

    private void move(Person focus) {
        //@todo
    }

    public boolean isMedicalAvailable() {
        medicineUsed++;
        
        return medicineUsed<=medicineProduced;
    }

    public boolean addPerson(Person person) {
        return people.add(person);
    }

    public int getBuildingCount() {
        return bld.size();
    }

    public boolean offerEvent(GameEvent e) {
        return events.offer(e);
    }

    public Person getBreedableMale() {
        return maleMinglers.remove(rnd.nextInt(maleMinglers.size()));
    }

    public ArrayList<Person> getPeople() {
        ArrayList<Person> colo = new ArrayList<>();

        colo.addAll(dead);
        colo.addAll(people);

        return colo;
    }

    public int getWorkers() {
        return workerCount;
    }

    private void updateScience() {
        if (currentGoal == null) {
            ArrayList<Science> avail =
                    Main.resources.getAvailableScience(researched);

            Collections.sort(avail);

            if (!avail.isEmpty()) {
                currentGoal = avail.get(0);
            }
        } else {
            //update current goal by payinginto it
            if (currentGoal.pay(
                    getScience())) {

                //enque research event
                offerEvent(new ScienceEvent(currentGoal));



                //set no goal
                researched.add(currentGoal);
                currentGoal = null;
            }
        }
    }

    public ArrayList<String> getEventLog() {
        return log;
    }

    public ArrayList<Science> getResearched() {
        return researched;
    }

    public int getWorkersAvailable() {
        return workerCount - workerNeed;
    }

    public double getScience() {
        return Math.round(Math.pow(scienceProduced,
                Settings.DEFAULT_SCIENCE_DIMINISH) * 100.00) / 100.00;
    }

    public Science getCurrentGoal() {
        return currentGoal;
    }
}
