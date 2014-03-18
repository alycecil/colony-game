/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.game;

import colonygame.Main;
import colonygame.resources.BuildingType;

/**
 *
 * @author WilCecil
 */
public class Person {

    public static int nextId = 0;

    public static int getState(int age) {
        if(age>MIN_ELDER) return STATE_ELDER;
        else if(age>MIN_ADULT) return STATE_ADULT;
        else if(age>MIN_TEEN) return STATE_TEEN;
        else if(age>MIN_CHILD) return STATE_CHILD;
        else if(age>MIN_BABY) return STATE_BABY;
        return STATE_DEAD;
    }
    
    int id;
    int state;
    int DOB;
    boolean gender;
    boolean educated;
    Person mother;
    Person mate;
    BuildingType home;
    BuildingType work;
    public static final boolean MALE = true;
    public static final boolean FEMALE = false;
    public static final int STATE_BABY = 1<<1;
    public static final int STATE_CHILD = 1<<2;
    public static final int STATE_TEEN = 1<<3;
    public static final int STATE_ADULT = 1<<4;
    public static final int STATE_MARRIED = 1;
    public static final int STATE_ELDER = 1<<5;
    public static final int STATE_DEAD = -1;
    public static final int MIN_BABY = 0000;
    public static final int MIN_CHILD = 0300;
    public static final int MIN_TEEN = 1100;
    public static final int MIN_ADULT = 1700;
    public static final int MIN_ELDER = 6000;

    public Person(boolean gender, Person mother) {
        this.state = STATE_BABY;
        this.DOB = Main.game.getTimeStamp();
        this.gender = gender;
        this.educated = false;
        this.mother = mother;
        this.home = null;
        this.work = null;
        
        id = nextId++;
    }

    public Person(int state, int DOB, boolean gender, boolean educated, 
            Person mother, BuildingType home, BuildingType work) {
        this.state = state;
        this.DOB = DOB;
        this.gender = gender;
        this.educated = educated;
        this.mother = mother;
        this.home = home;
        this.work = work;
        
        id = nextId++;
    }

    public int getDOB() {
        return DOB;
    }

    public BuildingType getHome() {
        return home;
    }

    public Person getMother() {
        return mother;
    }

    public int getState() {
        return state;
    }

    public BuildingType getWork() {
        return work;
    }

    public boolean isMale() {
        return gender == MALE;
    }

    public boolean isEducated() {
        return educated;
    }

    public void setEducated(boolean educated) {
        this.educated = educated;
    }

    public void setHome(BuildingType home) {
        this.home = home;
    }

    public void setWork(BuildingType work) {
        this.work = work;
    }

    public void setState(int state) {
        this.state = state;
    }

    /**
     * Have a pair mate.
     * @param mother
     * @param father
     * @return new person!
     */
    public static Person mate(Person mother, Person father) {
        if (!mother.isMale() && father.isMale()) {

            boolean gender = Main.game.rnd.nextBoolean();

            return new Person(gender, mother);
        } else {
            return null;
        }

    }

    public boolean isAlive() {
        return (state&1)!=STATE_DEAD;
    }
}
