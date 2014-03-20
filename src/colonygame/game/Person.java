/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.game;

import colonygame.Main;
import colonygame.event.PregnancyEvent;
import colonygame.resources.Settings;
import java.util.ArrayList;
import javax.annotation.Resource;

/**
 *
 * @author WilCecil
 */
public class Person implements Comparable<Person> {

    public static int nextId = 0;

    public static int getState(int age) {
        if (age > MIN_ELDER) {
            return STATE_ELDER;
        } else if (age > MIN_ADULT) {
            return STATE_ADULT;
        } else if (age > MIN_TEEN) {
            return STATE_TEEN;
        } else if (age > MIN_CHILD) {
            return STATE_CHILD;
        } else if (age > MIN_BABY) {
            return STATE_BABY;
        }
        return STATE_DEAD;
    }
    int id;
    int state;
    int DOB;
    int DOD;
    boolean gender;
    boolean educated;
    Person mother;
    Person mate;
    Building home;
    Building work;
    String firstName;
    String lastName;
    ArrayList<Person> children;
    public static final boolean MALE = true;
    public static final boolean FEMALE = !MALE;
    //AGE BASED STATES
    public static final int STATE_BABY = 1 << 1;
    public static final int STATE_CHILD = 1 << 2;
    public static final int STATE_TEEN = 1 << 3;
    public static final int STATE_ADULT = 1 << 4;
    public static final int STATE_ELDER = 1 << 5;
    //LIFE TIME STATESs
    public static final int STATE_PREGNANT = 1 << 6;
    public static final int STATE_SICK = 1 << 7;
    public static final int STATE_MARRIED = 1 << 8;
    public static final int STATE_FERTILE = 1 << 9;
    //FATAL STATES
    public static final int STATE_DEAD = 1;
    //Hu... how do you not have any states?
    public static final int STATE_BLANK = 0;
    public static final int MIN_BABY = 0000;
    public static final int MIN_CHILD = 0300;
    public static final int MIN_TEEN = 1300;
    public static final int MIN_ADULT = 1800;
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

        DOD = -1;

        nameMe();



    }

    public Person(boolean gender, Person mother, Person father) {
        this.state = STATE_BABY;
        this.DOB = Main.game.getTimeStamp();
        this.gender = gender;
        this.educated = false;
        this.mother = mother;

        this.work = null;



        nameMe();
        if (mother != null) {
            this.home = mother.home;

            //if our mother is married add a boost
            if (mother.isState(STATE_MARRIED)) {
                addState(STATE_FERTILE);

                //special case get fathers last name!
                if (father != null && father.lastName != null) {
                    lastName = father.lastName;
                }

            }

            //if our parents have the same mother 
            //make sick
            if (mother.mother != null
                    && father != null && father.mother != null
                    && mother.mother.id == father.mother.id) {
                addState(STATE_SICK);

            }


            //add me to my mothers children
            if (mother.children == null) {
                mother.children = new ArrayList<>();
            }
            mother.children.add(this);

        }

        id = nextId++;
        DOD = -1;

    }

    public Person(int state, int DOB, boolean gender, boolean educated,
            Person mother, Building home, Building work) {
        this.state = state;
        this.DOB = DOB;
        this.gender = gender;
        this.educated = educated;
        this.mother = mother;
        this.home = home;
        this.work = work;

        id = nextId++;

        DOD = -1;

        nameMe();
    }

    public int getDOB() {
        return DOB;
    }

    public int getDOD() {
        return DOD;
    }

    public Building getHome() {
        return home;
    }

    public Person getMother() {
        return mother;
    }

    public int getState() {
        return state;
    }

    public Building getWork() {
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

    public void setHome(Building home) {
        this.home = home;
    }

    public void setWork(Building work) {
        this.work = work;
    }

    //public void setState(int state) {
    //    this.state = state;
    //}
    public boolean isState(int check) {
        return (state & check) != 0;
    }

    public void addState(int newState) {
        if (DOD == -1 && newState == STATE_DEAD) {
            DOD = Main.game.getTimeStamp();
        }
        state = state | newState;
    }

    public void removeState(int oldState) {
        state = state & ~oldState;
    }

    public boolean isAlive() {
        return (state & STATE_DEAD) == 0;
    }

    public Person getMate() {
        return mate;
    }

    public void setMate(Person mate) {
        this.mate = mate;
    }

    public void simulate() {
        double rate = 0;
        boolean fertile = false;
        boolean hasMedical = Main.game.isMedicalAvailable();

        double roll = Main.game.rnd.nextDouble();

        //only simulate the living!
        if (!isAlive()) {
            return;
        }

        //update state
        if (Main.game.getTimeStamp() - getDOB() > MIN_ELDER) {
            addState(STATE_ELDER);
        } else if (Main.game.getTimeStamp() - getDOB() > MIN_ADULT) {
            addState(STATE_ADULT);
        } else if (Main.game.getTimeStamp() - getDOB() > MIN_TEEN) {
            addState(STATE_TEEN);
        } else if (Main.game.getTimeStamp() - getDOB() > MIN_CHILD) {
            addState(STATE_CHILD);
        } else if (Main.game.getTimeStamp() - getDOB() > MIN_BABY) {
            addState(STATE_BABY);
        }

        if (isState(STATE_ELDER)) {
            fertile = false;
            rate = Settings.DEFAULT_MORTALITY_ELDER;

        } else if (isState(STATE_ADULT)) {
            fertile = true;

            rate = Settings.DEFAULT_MORTALITY_ADULT;
        } else if (isState(STATE_TEEN)) {
            fertile = true;
            rate = Settings.DEFAULT_MORTALITY_TEEN;
        } else if (isState(STATE_CHILD)) {
            fertile = false;
            rate = Settings.DEFAULT_MORTALITY_CHILD;
        } else if (isState(STATE_BABY)) {
            fertile = false;
            rate = Settings.DEFAULT_MORTALITY_INFANT;
        } else {
            //wtf, guy.
            rate = 0;
        }


        if (hasMedical) {
            rate *= Settings.DEFAULT_MEDICAL_MODIFIER;
        }
        if (isState(STATE_SICK)) {
            rate *= Settings.DEFAULT_SICK_MODIFIER;
        }

        //are people starving?
        if (Main.game.getAgrigultureStored() < 0) {
            //are people starving and is food in the red?
            if (Main.game.getAgriculture() < 0) {
                //roll for hunger sick
                rate *= Settings.DEFAULT_STARVING_MODIFIER;
            }

            rate *= Settings.DEFAULT_HUNGRY_MODIFIER;
        }

        if (roll < rate) {
            //sadly we die here
            addState(STATE_DEAD);
        } else {



            //since we didnt die lets see if we get pregnant instead
            //check if housing is available and if food is available
            if (Main.game.getAgriculture() > 0) {

                if (fertile && !isMale() && !isState(STATE_PREGNANT)) {
                    //role for pregnany
                    if (isState(STATE_TEEN)) {
                        rate = Settings.DEFAULT_PREGNANCY_EVENT_TEEN;
                    } else {
                        rate = Settings.DEFAULT_PREGNANCY_EVENT_ADULT;
                    }

                    if (isState(STATE_FERTILE)) {
                        rate *= Settings.DEFAULT_PREGNANCY_FERTILE_MOD;
                    }
                    if (isState(STATE_MARRIED)) {
                        rate *= Settings.DEFAULT_PREGNANCY_MARRIAGE_MOD;
                    }

                    if (roll < rate) {
                        //happily we get pregnant!
                        //add a birthevent
                        //are we married?
                        addState(STATE_PREGNANT);
                        if (isState(STATE_MARRIED) && mate != null) {
                            Main.game.offerEvent(new PregnancyEvent(this, mate));
                        } else {
                            Main.game.offerEvent(new PregnancyEvent(this,
                                    Main.game.getBreedableMale()));
                        }
                    }
                }
            }
        }
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        String desc = lastName+", "+firstName+ " " + id + " ";
        if (isMale()) {
            desc += "male ";
        } else {
            desc += "female ";
        }

        if (isState(STATE_PREGNANT)) {
            desc += "pregnant ";
        }
        if (isState(STATE_MARRIED)) {
            desc += "married ";
        }
        if (!isState(STATE_ELDER) && (isState(STATE_TEEN) || isState(STATE_ADULT))) {
            desc += "of age ";
        }
        if (isState(STATE_FERTILE)) {
            desc += "fertile ";
        }
        if (isState(STATE_SICK)) {
            desc += "sick ";
        }
        if (!isAlive()) {
            desc += "dead " + (getDOD() - getDOB());
        } else {
            desc += Main.game.getTimeStamp() - getDOB();
        }
        return desc;
    }

    @Override
    public int compareTo(Person o) {
        return this.getId() - o.getId();
    }

    public ArrayList<Person> getChildren() {
        return children;
    }

    private void nameMe() {
        firstName = Main.resources.getNameManager().getFirstName(
                Main.game.rnd, this.gender);

        if (mother != null && mother.lastName != null) {
            lastName = mother.lastName;
        } else {
            lastName = Main.resources.getNameManager().getSurname(
                    Main.game.rnd);
        }
    }
}
