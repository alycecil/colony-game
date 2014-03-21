/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.event;

import colonygame.Main;
import colonygame.resources.Science;

/**
 *
 * @author WilCecil
 */
public class ScienceEvent extends GameEvent {

    int time;
    Science sc;

    public ScienceEvent(Science sc) {
        this.time = Main.game.getTimeStamp() + 1;
        this.sc = sc;
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public boolean doEvent() {
        //we finished paying so add to finished
        if(!Main.game.getResearched().contains(sc)){
            Main.game.getResearched().add(sc);
        }
        
        if(sc.getTech()!=Science.NO_UNLOCK){
            Main.game.addTech(sc.getTech());
        }
        
        return true;

    }

    @Override
    public String logEvent() {
         return "On "+getTime()+" we discovered "+sc.getShortDesc();
    }
}
