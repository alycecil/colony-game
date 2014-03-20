/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.ui;

import colonygame.resources.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;

/**
 *
 * @author WilCecil
 */
public class UIButtonSprite extends UIButton {

    Image s;

    public UIButtonSprite(
            int x, int y, String sText,
            Color cBg, Color cText, Image img,
            ActionListener e) {
        super(x, y, img.getWidth(null) + 10, img.getHeight(null) + 10,
                sText, cBg, cText, e);
        s = img;
    }

    public UIButtonSprite(int x, int y, int width, int height,
            String sText, Color cBg, Color cText, Image s,
            ActionListener e) {
        super(x, y, width, height, sText, cBg, cText, e);
        this.s = s;
    }

    @Override
    public void render(Graphics g) {
        //draw box
        g.setColor(cBg);
        g.fillRect(x, y, width, height);

        //draw image
        //scaling as needed
        if (height < s.getHeight(null)) {
            s = s.getScaledInstance(-1, height, Image.SCALE_DEFAULT);
        }
        if (width < s.getWidth(null)) {
            s = s.getScaledInstance(width, -1, Image.SCALE_DEFAULT);
        }
        g.drawImage(s, x+width-s.getWidth(null), y+height-s.getHeight(null), null);

        //write string
        g.setColor(cText);
        g.drawString(sText, x + 5, y + 15);

    }
}
