/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonygame;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author WilCecil
 */
public class UIButton {
    int x, y;
    int width, height;
    String sText;
    Color cBg;
    Color cText;

    public UIButton(int x, int y, int width, int height, String sText, Color cBg, Color cText) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sText = sText;
        this.cBg = cBg;
        this.cText = cText;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getText() {
        return sText;
    }

    public Color getBackgroundColor() {
        return cBg;
    }

    public Color getTextColor() {
        return cText;
    }
    
    /**
     * Render the button
     * @param g 
     */
    public void render(Graphics g){
        //draw box
        g.setColor(cBg);
        g.fillRect(x, y, width, height);
        
        //write string
        g.setColor(cText);
        g.drawString(sText, x+5, y+5);
        
    }
    
    
     public void renderHit(Graphics g, Color c){
        //draw box
        g.setColor(c);
        g.fillRect(x, y, width, height);
     }
    
}
