/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.ui;

import colonygame.Main;
import colonygame.uitool.Build;
import colonygame.resources.BuildingType;
import colonygame.resources.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author WilCecil
 */
public class BuildMenu {

    int x, y, width, height;
    ArrayList<UIButton> myButtons;
    boolean visible;

    public BuildMenu(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        myButtons = new ArrayList<>();
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void update() {
        int _x;
        int _y;


        Sprite s;
        Image img;
        ArrayList<UIButton> uiButts;

        Color c = Color.DARK_GRAY;

        uiButts = Main.ui.getButtons();
        uiButts.removeAll(myButtons);

        if (isVisible()) {

            _x = 0;
            _y = 0;


            //\/\/\/\/\/\/\/\/\/\/\/\/\
            // -- MAKE BUTTONS --
            //\/\/\/\/\/\/\/\/\/\/\/\/\s
            ArrayList<BuildingType> builds = Main.game.getBuildables();



            for (int i = 0; i < builds.size(); i++) {
                final UIButton btn;
                final BuildingType temp;
                //get sprite image
                temp = builds.get(i);

                s = temp.getSprite();

                img = s.getCell(temp.getSpriteX(), temp.getSpriteY());


                //
                btn = new UIButtonSprite(x+_x, y + _y,width,height, temp.getId(), c,
                        Color.red, img, null);

                //btn = new UIButton(x+_x,y+_y, width, height, temp.getId(), c, 
                //        Color.RED, null);
                
                btn.setActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Main.ui.setCurrentTool(new Build(btn, temp));
                        Main.ui.setBuildmenuVisible(false);
                        Main.ui.getBuildmenu().update();
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


                _y += height;
                
                if(_y+y+height>Main.ui.getHeight()){
                    _y = 0;
                    _x += width;
                }

            }

        }

        Main.ui.forceRepaint();
    }

    /**
     *
     * @param g
     */
    public void render(Graphics g) {
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
