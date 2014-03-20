/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonygame.game;

import colonygame.Main;
import java.util.ArrayList;

/**
 *
 * @author WilCecil
 */
public class StateOfTheColony {

    public static ArrayList<StateOfTheColony> all = new ArrayList<>();
//    int DEMOGRAPHICS_male, 
//            DEMOGRAPHICS_female;
//    
//    int DEMOGRAPHICS_baby,
//            DEMOGRAPHICS_child,
//            DEMOGRAPHICS_teen,
//            DEMOGRAPHICS_adult,
//            DEMOGRAPHICS_elder;
//    
//    int DEMOGRAPHICS_pregnant;
//    
//    int DEMOGRAPHICS_educated_adult;
    
    int buildings;
    
    int food;
    int ore;
    int power;
    int housing;
    int totalPop;

    int timeStamp;
    
    public StateOfTheColony() {
        timeStamp = Main.game.getTimeStamp();
        food = Main.game.getAgrigultureStored();
        ore = Main.game.getOre();
        power = Main.game.getPower();
        housing = Main.game.getHousingTotal();
        totalPop = Main.game.getPopulation();
        buildings = Main.game.getBuildingCount();
        
        all.add(this);
    }
    
    
}
