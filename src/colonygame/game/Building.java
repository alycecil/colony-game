/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.game;

import colonygame.resources.BuildingType;


/**
 *
 * @author WilCecil
 */
public class Building implements Comparable<Building>{

    BuildingType type;
    int current;
    int x, y, z;
    boolean stateOn = false;
    boolean isConected = false;
    boolean disabled = false;

    public Building(BuildingType type, int current, int x, int y, int z) {
        this.type = type;
        this.current = current;
        isConected = type.isType(BuildingType.TYPE_POLITICAL);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BuildingType getType() {
        return type;
    }

    public int getCurrent() {
        return current;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public boolean isOnline() {
        return stateOn;
    }

    public void setOnline(boolean stateOn) {
        this.stateOn = stateOn;
    }

    public boolean isConected() {
        return isConected;
    }

    public void setConected(boolean isConected) {
        this.isConected = isConected;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    
    
    @Override
    public int compareTo(Building o) {
        return this.getType().compareTo(o.getType());
    }
}
    