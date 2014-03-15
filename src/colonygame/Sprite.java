/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame;

import java.awt.image.BufferedImage;

/**
 *
 * @author WilCecil
 */
public class Sprite implements xmlStored{

    BufferedImage tileSet;
    int width, height, startX, startY, deltaX, deltaY;

    public Sprite(BufferedImage tileSet, int width, int height, int startX, int startY, int deltaX, int deltaY) {
        this.tileSet = tileSet;
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public Sprite() {
    }

    @Override
    public boolean readXML() {
        
        
        
        return true;
    }
}
