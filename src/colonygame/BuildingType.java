/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame;

import colonygame.resources.Sprite;

/**
 *
 * @author WilCecil
 */
public class BuildingType {

    int capacity;
    char type;
    
    Sprite sprite;
    int spriteX, spriteY;
    
    public static final char TYPE_TUBE = 0;
    public static final char TYPE_HOUSE = 1;
    public static final char TYPE_FACTORY = 2;
    public static final char TYPE_SCIENCE = 4;
    public static final char TYPE_WORK = 8;
}
