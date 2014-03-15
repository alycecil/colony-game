/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame;

/**
 *
 * @author WilCecil
 */
public class World  implements xmlStored{

    Sprite tile;
    String id;

    public World(Sprite tile, String id) {
        this.tile = tile;
        this.id = id;
    }

    public Sprite getTile() {
        return tile;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean readXML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
