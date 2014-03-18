/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonygame.uitool;

import java.awt.Cursor;

/**
 *
 * @author WilCecil
 */
public interface Tool {

    public Cursor getCursor();
    public boolean interact(int x, int y, int z);
    public boolean unselect();
    public boolean select();
}
