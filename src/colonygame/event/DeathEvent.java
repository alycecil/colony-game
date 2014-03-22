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
public class DeathEvent extends GameEvent{

    Person p;
    int time;

    public DeathEvent(Person p, int time) {
        this.p = p;
        this.time = time;
    }
    
    public DeathEvent(Person p) {
        this.p = p;
        this.time = Main.game.getTimeStamp()+1;
    }

    
    
    
    @Override
    public int getTime() {
        return time;
    }

    @Override
    public boolean doEvent() {
        p.addState(Person.STATE_DEAD);
        if(p.getMate()!=null){
            p.setMate(null);
            p.removeState(Person.STATE_MARRIED);
        }
        return true;
    }

    @Override
    public String logEvent() {
         return "On "+getTime()+" "+p.getFirstName()+" "+
                p.getLastName()+" died at the age of "+(p.getDOD()-p.getDOB());
    }

}
