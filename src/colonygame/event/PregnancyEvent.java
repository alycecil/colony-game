/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.event;

import colonygame.Main;
import colonygame.game.Person;
import colonygame.resources.Settings;

/**
 *
 * @author WilCecil
 */
public class PregnancyEvent extends GameEvent {

    Person mother;
    Person father;
    Person child;
    int time;

    public PregnancyEvent(Person mother, Person father) {
        this.mother = mother;
        this.father = father;
        time = Main.game.getTimeStamp() + Settings.DEFAULT_PREGNANCY_LENGTH;
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public boolean doEvent() {
        double rate;

        //get our death rates
        if (Main.game.isMedicalAvailable()) {
            rate = Settings.DEFAULT_MORTALITY_PREGNANT_MEDICAL;
        } else {
            rate = Settings.DEFAULT_MORTALITY_PREGNANT;
        }

        //check if we die
        if (Main.game.rnd.nextDouble() < rate) {
            mother.addState(Person.STATE_DEAD);

        }

        //make mother no longer pregnant
        mother.removeState(Person.STATE_PREGNANT);

        //but give the mother a great fertility bonus
        //if she is young
        if (mother.isState(Person.STATE_TEEN)) {
            mother.addState(Person.STATE_FERTILE);
        }

        //give birth
        child = new Person(Main.game.rnd.nextBoolean(), mother, father);
        return Main.game.addPerson(child);
    }

    @Override
    public String logEvent() {
        return "On "+getTime()+" "+mother.getFirstName()+" "+
                mother.getLastName()+" with the help of "+
                father.getFirstName()+" "+father.getLastName()+" gave birth to "
                + child.getFirstName()+ " " + child.getLastName() +".";
    }
}
