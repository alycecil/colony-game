/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonygame.resources;

import colonygame.Main;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import org.ini4j.Wini;

/**
 *
 * @author WilCecil
 */
public class Resources {

    
    AbstractMap<String, World> worlds;
    AbstractMap<String, Sprite> sprites;
    AbstractMap<String, WorldMap> maps;
    
    
    /**
     * Creates the space for resources, loads nothing.
     * <p>
     * use loadXML()
     */
    public Resources() {
        worlds = new HashMap<>();
        sprites = new HashMap<>();
        maps = new HashMap<>();
    }

    
    
    /**
     * Called to initialize the resources for the game from XML
     * @throws IOException 
     */
    public void loadXML() throws IOException{
        //ini read
        Wini ini = new Wini(new File(Main.SETTINGS_FILE));
        File worldsXML = new File(ini.get("resources", "worlds"));
        File spritesXML = new File(ini.get("resources", "sprites"));
        File mapsXML = new File(ini.get("resources", "maps"));
        
        //parseSprites
        Sprite.readXML(spritesXML);
        
        
        //parseWorlds
        World.readXML(worldsXML);
        
        
        //parseMaps
        WorldMap.readXML(mapsXML);
        
    }

    public World getWorld(String keyId) {
        return worlds.get(keyId);
    }
    
    public WorldMap getMap(String keyId) {
        return maps.get(keyId);
    }
    
    public Sprite getSprite(String keyId) {
        return sprites.get(keyId);
    }

    public void addMap(String keyId, WorldMap map) {
        maps.put(keyId, map);
    }
    
    public void addWorld(String keyId, World world) {
        worlds.put(keyId, world);
    }
    
    public void addSprite(String keyId, Sprite sprite) {
        sprites.put(keyId, sprite);
    }
    
    
    
    
    
    
}
