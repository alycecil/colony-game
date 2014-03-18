/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.resources;

import colonygame.Main;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.ini4j.Wini;

/**
 *
 * @author WilCecil
 */
public class Resources {

    AbstractMap<String, World> worlds;
    AbstractMap<String, Sprite> sprites;
    AbstractMap<String, WorldMap> maps;
    AbstractMap<String, BuildingType> buildings;
    BuildingType contruction;

    /**
     * Creates the space for resources, loads nothing.
     * <p>
     * use loadXML()
     */
    public Resources() {
        worlds = new HashMap<>();
        sprites = new HashMap<>();
        maps = new HashMap<>();
        buildings = new HashMap<>();
    }

    /**
     * Called to initialize the resources for the game from XML
     *
     * @throws IOException
     */
    public void loadXML() throws IOException {
        //ini read
        Wini ini = new Wini(new File(Main.SETTINGS_FILE));
        File worldsXML = new File(ini.get("resources", "worlds"));
        File spritesXML = new File(ini.get("resources", "sprites"));
        File mapsXML = new File(ini.get("resources", "maps"));
        File buildingsXML = new File(ini.get("resources", "buildings"));

        //parseSprites
        Sprite.readXML(spritesXML);


        //parseWorlds
        World.readXML(worldsXML);


        //parseMaps
        WorldMap.readXML(mapsXML);

        //parse buildings
        BuildingType.readXML(buildingsXML);

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
    
    public BuildingType getBuilding(String keyId) {
        return buildings.get(keyId);
    }

    public Set<String> getMaps() {
        return maps.keySet();
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

    void addBuilding(String tempId, BuildingType buildingType) {

        if(buildingType.isType(BuildingType.TYPE_CONSTRUCTION)){
            contruction = buildingType;
        }
        
        
        buildings.put(tempId, buildingType);

    }

    public BuildingType[] getBuildings() {
        return (BuildingType[]) buildings.values().toArray();
    }

    public ArrayList<BuildingType> getBuildings(short type) {
        ArrayList<BuildingType> wanted = new ArrayList<>();
        Iterator<BuildingType> iter = buildings.values().iterator();
        BuildingType temp;

        while (iter.hasNext()) {
            temp = iter.next();
            
            if(temp.isType(type)){
                wanted.add(temp);
            }
        }
        
        
        
        return wanted;
    }
    
    public ArrayList<BuildingType> getBuildingsTech(short tech) {
        ArrayList<BuildingType> wanted = new ArrayList<>();
        Iterator<BuildingType> iter = buildings.values().iterator();
        BuildingType temp;

        while (iter.hasNext()) {
            temp = iter.next();
            
            if(temp.getTech()==tech){
                wanted.add(temp);
            }
        }
        
        
        
        return wanted;
    }

    public BuildingType getContruction() {
        return contruction;
    }
    
    
}
