/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame;

/**
 *
 * @author WilCecil
 */
public class WorldMap implements xmlStored{

    World parent;
    
    public static final short CLEAR = 1;
    public static final short DOZED = 0;
    public static final short ROUGH = 2;
    public static final short DIFFICULT = 3;
    public static final short IMPASSIBLE = 4;
    public static short DEFAULT_VALUE = CLEAR;
    short[][][] map;

    public WorldMap(short[][][] map, World pWorld) {
        this.map = map;
        
        parent = pWorld;
    }

    public WorldMap(int size, World pWorld) {
        this(size, size, 5, pWorld);
    }

    public WorldMap(int size_width, int size_height, int depth, World pWorld) {
        map = new short[depth][size_width][size_height];
        parent = pWorld;

        for (int z = 0; z < 5; z++) {
            for (int i = 0; i < size_width; i++) {
                for (int j = 0; j < size_height; j++) {
                    map[z][i][j] = DEFAULT_VALUE;
                }
            }
        }
    }

  

    @Override
    public boolean readXML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
