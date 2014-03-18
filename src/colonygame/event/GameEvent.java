/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonygame.event;

/**
 *
 * @author WilCecil
 */
public abstract class GameEvent implements Comparable<GameEvent>{

    /**
     * returns the time the event is to trigger in tick marks
     * @return tick
     */
    public abstract int getTime();
    
    /**
     * Processes the event
     * @return boolean, true if the event processed successfully
     */
    public abstract boolean doEvent();
    
    
}
