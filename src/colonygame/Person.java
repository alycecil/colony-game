/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame;

/**
 *
 * @author WilCecil
 */
public class Person {

    char state;
    int DOB;
    boolean gender;
    boolean educated;
    Person mother;
    BuildingType home;
    BuildingType work;
    public static final boolean MALE = true;
    public static final boolean FEMALE = false;
    public static final char STATE_BABY = 10;
    public static final char STATE_CHILD = 20;
    public static final char STATE_TEEN = 30;
    public static final char STATE_ADULT = 40;
    public static final char STATE_MARRIED = 50;
    public static final char STATE_ELDER = 60;
    public static final char STATE_DEAD = 70;
    public static final char MIN_BABY = 0000;
    public static final char MIN_CHILD = 0300;
    public static final char MIN_TEEN = 1100;
    public static final char MIN_ADULT = 1700;
    public static final char MIN_ELDER = 6000;

    public Person(boolean gender, Person mother) {
        this.state = STATE_BABY;
        this.DOB = Main.game.getTimeStamp();
        this.gender = gender;
        this.educated = false;
        this.mother = mother;
        this.home = null;
        this.work = null;
    }

    public Person(char state, int DOB, boolean gender, boolean educated, Person mother, BuildingType home, BuildingType work) {
        this.state = state;
        this.DOB = DOB;
        this.gender = gender;
        this.educated = educated;
        this.mother = mother;
        this.home = home;
        this.work = work;
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

    public char getState() {
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

    public void setState(char state) {
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
}
