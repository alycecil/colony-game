/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.ui;

import colonygame.Main;
import colonygame.game.Building;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author WilCecil
 */
public class BuildingRightClick {

    int x, y, width, height;
    ArrayList<UIButton> myButtons;
    boolean visible;
    Building blding;
    Color backDrop;
    Color text;
    ArrayList<String> aText;
    int fontHeight = 11;
    int fontWidth = 5;

    public BuildingRightClick(int x, int y, int width, int height,
            Building blding, Color backDrop, Color text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.myButtons = new ArrayList<>();
        this.visible = false;
        this.blding = blding;
        this.backDrop = backDrop;
        this.text = text;


        aText = new ArrayList<>();

        String message = blding.getType().getId() + "\n"
                + "Workers " + blding.getType().getCapacity() + "\n"
                + "Power " + blding.getType().getPower();


        if (blding.getType().getSupplyFood() != 0) {
            message += "\n+Food " + blding.getType().getSupplyFood();
        }

        if (blding.getType().getSupplyHousing() != 0) {
            message += "\n+Housing " + blding.getType().getSupplyHousing();
        }

        if (blding.getType().getSupplyMedical() != 0) {
            message += "\n+Medical " + blding.getType().getSupplyMedical();
        }

        if (blding.getType().getSupplyOre() != 0) {
            message += "\n+Ore " + blding.getType().getSupplyOre();
        }

        if (blding.getType().getSupplyPower() != 0) {
            message += "\n+Power " + blding.getType().getSupplyPower();
        }

        if (blding.getType().getSupplyScience() != 0) {
            message += "\n+Science " + blding.getType().getSupplyScience();
        }


        int charsPerLine = width / fontWidth;

        int i = 0;
        for (i = 0; i < height / fontHeight; i++) {
            int nextSplit = Math.min(message.indexOf("\n"), charsPerLine);
            if (nextSplit == -1) {
                if (message.length() > charsPerLine) {
                    nextSplit = charsPerLine;
                } else {
                    aText.add(message);
                    break;
                }
            }

            aText.add(message.substring(0, nextSplit));
            message = message.substring(nextSplit + 1);
        }
        
        this.height = Math.min(height,(aText.size()+3)*(fontHeight));
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        if (!visible) {

            Main.ui.getButtons().removeAll(myButtons);


            Main.ui.forceRepaint();
        }



        this.visible = visible;
    }

    public boolean isInside(int pX, int pY) {
        return pX >= x && pX <= x + width && pY >= y && pY <= y + height;
    }

    /**
     * Renders the right click menu
     *
     * @param g
     */
    public void render(Graphics g) {

        //ensure not past the edge of the screen
        if (Main.ui.getWidth() - 20 < x + width) {
            x = Main.ui.getWidth() - width - 20;
        }

        if (Main.ui.getHeight() - 30 < y + height) {
            y = Main.ui.getHeight() - height - 30;
        }



        int line = 2;

        if (isVisible()) {
            g.setColor(backDrop);
            g.fillRect(x, y, width, height);

            g.setColor(backDrop.darker());
            g.drawRect(x, y, width, height);
            
            //add toggle button
            //if there is none
            if (myButtons.isEmpty()) {
                //
                UIButton btn = new UIButtonBuilding(x + 2, y + 1,
                        width - 4, (int) (fontHeight * 1.5), blding,
                        Color.LIGHT_GRAY, Color.green, null);

                //btn = new UIButton(x+_x,y+_y, width, height, temp.getId(), c, 
                //        Color.RED, null);

                btn.setActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        blding.setDisabled(!blding.isDisabled());
                    }
                });

                /*
                 * add buttons using ui manager to also add hitbox color
                 */
                Main.ui.addButton(btn);

                //add to my references so i can delete it.s
                myButtons.add(btn);

                //force repaint
                Main.ui.forceRepaint();
            }

            g.setColor(text);
            Iterator<String> iter = aText.iterator();

            while (iter.hasNext()) {
                line++;
                g.drawString(iter.next(), x+3, y + line * fontHeight);
            }

        }
    }
}
