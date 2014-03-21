/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.event;

import colonygame.Main;
import colonygame.game.Person;

/**
 *
 * @author WilCecil
 */
public class MarriageEvent extends GameEvent {

    Person m, f;
    int time;

    public MarriageEvent(Person m, Person f, int time) {
        this.m = m;
        this.f = f;
        this.time = time;
    }

    public MarriageEvent(Person m, Person f) {
        this.m = m;
        this.f = f;
        this.time = Main.game.getTimeStamp()+1;
    }
    
    @Override
    public int getTime() {
        return time;
    }

    @Override
    public boolean doEvent() {
        f.addState(Person.STATE_MARRIED);
        m.addState(Person.STATE_MARRIED);

        m.setMate(f);
        f.setMate(m);

        return true;
    }

    @Override
    public String logEvent() {
        return "On "+getTime()+" "+m.getFirstName()+" "+m.getLastName()+" and "+
                f.getFirstName()+" "+f.getLastName()+" got married.";
        
    }
}
