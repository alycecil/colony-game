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
    AbstractMap<String, World> sprites;
    
    public Resources() {
        worlds = new HashMap<>();
        sprites = new HashMap<>();
    }

    
    
    
    public void loadXML() throws IOException{
        //ini read
        Wini ini = new Wini(new File(Main.SETTINGS_FILE));
        File worldsXML = new File(ini.get("resources", "worlds"));
        File spriteXML = new File(ini.get("resources", "sprite"));
        
        //parseSprites
        
        
        //parseWorlds
        
        
    }
    
    
}
