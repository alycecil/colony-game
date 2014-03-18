/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonygame.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    ActionListener e;
    
    Color selected;
    Color unSelected;

    public UIButton(int x, int y, int width, int height, String sText, Color cBg, Color cText, ActionListener e) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sText = sText;
        this.cBg = cBg;
        this.cText = cText;
        this.e = e;
        
        selected = cBg.darker();
        unSelected = cBg;
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

    public void setActionListener(ActionListener e) {
        this.e = e;
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
        g.drawString(sText, x+4, y+14);
        
    }
    
    
     public void renderHit(Graphics g, Color c){
        //draw box
        g.setColor(c);
        g.fillRect(x, y, width, height);
     }



    public void click(ActionEvent evt){
        
        e.actionPerformed(evt);
        
        cBg = selected;

    }
    
    public void unclick(){
        
        cBg = unSelected;
    }
    
}
