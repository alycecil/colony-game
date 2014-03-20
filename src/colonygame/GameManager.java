/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonygame;

import javax.swing.Timer;

/**
 *
 * @author WilCecil
 */
public class GameManager {

    Timer t;
    
    public static int fastest = 25;
    public static int fast = 200;
    public static int normal = 500; 
    public static int slow = 1000; 
    public static int superslow = 10000; 
    

    public GameManager() {
        
        t = new Timer(normal, Main.game);
    }
    
    public void setDelay(int delay){
        t.setDelay(delay);
    }

    public void start(){
        t.start();
    }
    
    public void stop(){
        t.stop();
    }
    
    public void toggle(){
        if(t.isRunning()){
            t.stop();
        }else{
            t.start();
        }
    }
    
}
