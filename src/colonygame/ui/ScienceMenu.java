/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.ui;

import colonygame.Main;
import colonygame.resources.Science;
import colonygame.resources.Sprite;
import colonygame.uitool.Build;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

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
        Main.ui.forceRepaint();
        
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

    public void update() {
        int _x;
        int _y;
        int i;


        //Sprite s;
        Image img;
        ArrayList<UIButton> uiButts;
        Iterator<Science> iter;
        
        Color c = Color.DARK_GRAY;

        uiButts = Main.ui.getButtons();
        uiButts.removeAll(myButtons);


        if (isVisible()) {
            i=0;

            _x = 0;
            _y = 0;
            
            //\/\/\/\/\/\/\/\/\/\/\/\/\
            // -- MAKE BUTTONS --
            //\/\/\/\/\/\/\/\/\/\/\/\/\s
            ArrayList<Science> sci = 
                    Main.resources.getAvailableScience(
                    Main.game.getResearched());
            
            iter = sci.iterator();
            
            while(iter.hasNext()){
                final Science temp;
                final UIButton btn;
                
                temp = iter.next();

                img = temp.getImage();
                
                //
                btn = new UIButtonSprite(x+_x, y + _y,width,height, 
                        temp.getShortDesc() , c,
                        Color.red, img, null);

                //btn = new UIButton(x+_x,y+_y, width, height, temp.getId(), c, 
                //        Color.RED, null);
                
                btn.setActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Main.game.setCurrentGoal(temp);
                        Main.ui.getScienceMenu().setVisible(false);
                        Main.ui.getScienceMenu().update();
                    }
                });



                /*
                 * add buttons using ui manager to also add hitbox color
                 */
                Main.ui.addButton(btn);
                
                //add to my references so i can delete it.s
                myButtons.add(btn);


                //change color
                if ((i & 1) == 0) {
                    c = c.darker();
                } else {
                    c = c.brighter();
                }
                i++;
                
                //update position
                _y += height;
                
                if(_y+y+height>Main.ui.getHeight()){
                    _y = 0;
                    _x += width;
                }
            }
        }
    }
}
