/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonygame.ui;

import java.util.ArrayList;

/**
 *
 * @author WilCecil
 */
public class ScienceMenu {
    int x, y, width, height;
    ArrayList<UIButton> myButtons;
    boolean visible;

    public ScienceMenu(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.myButtons = new ArrayList<>();
        this.visible = false;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    
    
    

}
