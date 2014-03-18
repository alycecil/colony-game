package colonygame.resources;

import colonygame.Main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author WilCecil
 */
public class BuildingType {

    protected static final String ROOT_NODE = "buildings";
    protected static final String CHILD_NODE = "building";

    /*
     * TYPES
     TUBE
     HOUSE
     FACTORY
     SCIENCE
     MEDICAL
     ENTERTAINMENT
     POWER
     POLITICAL
     STORAGE
     AGRICULTURE
     Special Types
     LANDER
     CONSTRUCTION
     * 
     * resources
     * food
     * people
     * power
     * ore
     */
    String id;
    String decription;
    int power;
    int capacity;
    int buildtime;
    short type;
    Sprite sprite;
    int spriteX, spriteY;
    int minZ, maxZ;
    int deltaX, deltaY;
    short tech;
    public static final short TYPE_TUBE = 0;
    public static final short TYPE_HOUSE = 1;
    public static final short TYPE_FACTORY = 2;
    public static final short TYPE_SCIENCE = 4;
    public static final short TYPE_MEDICAL = 8;
    public static final short TYPE_ENTERTAINMENT = 16;
    public static final short TYPE_POWER = 32;
    public static final short TYPE_POLITICAL = 64;
    public static final short TYPE_STORAGE = 128;
    public static final short TYPE_AGRICULTURE = 256;
    public static final short TYPE_LANDER = 512;
    public static final short TYPE_CONSTRUCTION = 1028;
    protected static final String TYPE_TUBE_S = "TUBE";
    protected static final String TYPE_HOUSE_S = "HOUSE";
    protected static final String TYPE_FACTORY_S = "FACTORY";
    protected static final String TYPE_SCIENCE_S = "SCIENCE";
    protected static final String TYPE_MEDICAL_S = "MEDICAL";
    protected static final String TYPE_ENTERTAINMENT_S = "ENTERTAINMENT";
    protected static final String TYPE_POWER_S = "POWER";
    protected static final String TYPE_POLITICAL_S = "POLITICAL";
    protected static final String TYPE_STORAGE_S = "STORAGE";
    protected static final String TYPE_AGRICULTURE_S = "AGRICULTURE";
    protected static final String TYPE_LANDER_S = "LANDER";
    protected static final String TYPE_CONSTRUCTION_S = "CONSTRUCTION";

    public BuildingType(String id, String decription, int power, int capacity,
            int buildtime, short type, Sprite sprite, int spriteX, int spriteY,
            int spriteDX, int spriteDY, int minZ, int maxZ, short tech) {
        this.id = id;
        this.decription = decription;
        this.power = power;
        this.capacity = capacity;
        this.buildtime = buildtime;
        this.type = type;
        this.sprite = sprite;
        this.spriteX = spriteX;
        this.spriteY = spriteY;
        this.minZ = minZ;
        this.maxZ = maxZ;
        this.deltaX = spriteDX;
        this.deltaY = spriteDY;
        this.tech = tech;
    }

    public static boolean readXML(File pfSource) {
        try {
            //parse as DOM

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(pfSource);

            //do norm, as its the norm!
            doc.getDocumentElement().normalize();

            if (!doc.getDocumentElement().getNodeName().equalsIgnoreCase(ROOT_NODE)) {
                throw new ParserConfigurationException("Root Node of Type "
                        + doc.getDocumentElement().getNodeName()
                        + " unexpected, expected " + ROOT_NODE + ".");
            }

            //add children
            if (doc.hasChildNodes()) {
                int index;
                NodeList roots = doc.getChildNodes();

                for (index = 0; index < roots.getLength(); index++) {
                    if (roots.item(index).getNodeName().equalsIgnoreCase(ROOT_NODE)) {
                        break;
                    }
                }

                /////\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
                //this block is :
                //readXML(doc.getChildNodes());
                /////
                int added = 0;

                NodeList children = roots.item(index).getChildNodes();

                for (int count = 0; count < children.getLength(); count++) {
                    //get next
                    Node tempNode = children.item(count);

                    if (readXML(tempNode)) {
                        added++;
                    }
                }

                Logger.getLogger(BuildingType.class.getName()).log(
                        Level.INFO, "Added {0} " + ROOT_NODE + " to resources.", added);
            }



            //we were successful
            return true;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(BuildingType.class.getName()).log(
                    Level.SEVERE, null, ex);

            return false;
        }
    }

    public static boolean readXML(Node pNode) {
        //ensure type
        if (!pNode.getNodeName().equalsIgnoreCase(CHILD_NODE)) {
            return false;
        }



        //check for the types we want

        String tempId = null;
        String tempDecription = null;
        String[] tempType;
        int tPower, tCapacity, tSpriteX, tSpriteY, tBuild, tMin, tMax;
        int tSpriteDX,tSpriteDY;
        short tType,tTech;
        Sprite tSprite = null;

        boolean bid, bDesc, bPower, bCap, bSprite, bSpriteX,
                bSpriteY, bType, bBuild, bMin, bMax;

        //setall to false
        bid = false;
        bDesc = false;
        bPower = false;
        bCap = false;
        bSprite = false;
        bSpriteX = false;
        bSpriteY = false;
        bType = false;
        bBuild = false;
        bMin = false;
        bMax = false;

        //init all
        tPower = 0;
        tCapacity = 0;
        tSpriteX = 0;
        tSpriteY = 0;
        tType = 0;
        tBuild = 0;
        tMin = 0;
        tMax = 0;
        tSpriteDX = 0;
        tSpriteDY = 0;
        tTech= 0;



        /*
         * Example
         * <building>
         <id>CONSTRUCTION</id>
         <description>Construction in Progress</description>
         <type>CONSTRUCTION</type>
         <power>0</power>
         <sprite>tile2</sprite>
         <cellx>2</cellx>
         <celly>2</celly>
         <capacity>0</capacity>
         <mindepth>-1</mindepth>
         <maxdepth>-1</maxdepth>
         <buildtime>0</buildtime>
         </building>
         */
        NodeList children2 = pNode.getChildNodes();

        for (int count2 = 0; count2 < children2.getLength(); count2++) {

            Node tempChild = children2.item(count2);

            if (tempChild.getNodeName().equalsIgnoreCase("id")) {
                //getstring and set
                tempId = tempChild.getTextContent();

                bid = true;
            } else if (tempChild.getNodeName().equalsIgnoreCase("description")) {
                tempDecription = tempChild.getTextContent();

                bDesc = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("capacity")) {

                tCapacity = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());

                bCap = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("power")) {

                tPower = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());

                bPower = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("cellx")) {

                tSpriteX = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());

                bSpriteX = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("celly")) {

                tSpriteY = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());

                bSpriteY = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("celldx")) {

                tSpriteDX = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());

                //bSpriteDX = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("celldy")) {

                tSpriteDY = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());

                //bSpriteDY = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("buildtime")) {

                tBuild = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());

                bBuild = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("mindepth")) {

                tMin = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());

                bMin = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("maxdepth")) {

                tMax = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());
                if (tMax < 0) {
                    tMax = Integer.MAX_VALUE;
                }

                bMax = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("tech")) {

                tTech = (short) SASLib.Util.Val.VAL(tempChild.getTextContent());
                

            } else if (tempChild.getNodeName().equalsIgnoreCase("sprite")) {
                //ask resorces for sprite set
                tSprite = Main.resources.getSprite(tempChild.getTextContent());

                bSprite = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("type")) {
                tempType = tempChild.getTextContent().split(",");

                for (int i = 0; i < tempType.length; i++) {
                    if (tempType[i].equalsIgnoreCase(TYPE_AGRICULTURE_S)) {
                        tType = (short) (tType | TYPE_AGRICULTURE);

                    } else if (tempType[i].equalsIgnoreCase(TYPE_CONSTRUCTION_S)) {
                        tType = (short) (tType | TYPE_CONSTRUCTION);

                    } else if (tempType[i].equalsIgnoreCase(TYPE_ENTERTAINMENT_S)) {
                        tType = (short) (tType | TYPE_ENTERTAINMENT);

                    } else if (tempType[i].equalsIgnoreCase(TYPE_FACTORY_S)) {
                        tType = (short) (tType | TYPE_FACTORY);

                    } else if (tempType[i].equalsIgnoreCase(TYPE_HOUSE_S)) {
                        tType = (short) (tType | TYPE_HOUSE);

                    } else if (tempType[i].equalsIgnoreCase(TYPE_LANDER_S)) {
                        tType = (short) (tType | TYPE_LANDER);

                    } else if (tempType[i].equalsIgnoreCase(TYPE_MEDICAL_S)) {
                        tType = (short) (tType | TYPE_MEDICAL);

                    } else if (tempType[i].equalsIgnoreCase(TYPE_POLITICAL_S)) {
                        tType = (short) (tType | TYPE_POLITICAL);

                    } else if (tempType[i].equalsIgnoreCase(TYPE_POWER_S)) {
                        tType = (short) (tType | TYPE_POWER);

                    } else if (tempType[i].equalsIgnoreCase(TYPE_SCIENCE_S)) {
                        tType = (short) (tType | TYPE_SCIENCE);

                    } else if (tempType[i].equalsIgnoreCase(TYPE_STORAGE_S)) {
                        tType = (short) (tType | TYPE_STORAGE);

                    } else if (tempType[i].equalsIgnoreCase(TYPE_TUBE_S)) {
                        tType = (short) (tType | TYPE_TUBE);

                    }

                }

                bType = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("#text")) {
                //ignore whitespace  
            } else {
                Logger.getLogger(BuildingType.class.getName()).log(
                        Level.WARNING,
                        "Current Node type unexpedcted for " + CHILD_NODE
                        + ", {0}", tempChild.getNodeName());
            }

        }

        //all done with attributes
        if (bid && bDesc && bPower && bCap && bSprite && bSpriteX
                && bSpriteY && bType && bBuild && bMin && bMax) {


            //add to resources
            Main.resources.addBuilding(tempId,
                    new BuildingType(tempId, tempDecription, tPower, tCapacity,
                    tBuild, tType, tSprite, tSpriteX, tSpriteY,tSpriteDX,
                    tSpriteDY, tMin, tMax, tTech));

            return true;

        } else {

            //uh oh
            Logger.getLogger(BuildingType.class.getName()).log(
                    Level.WARNING, "current " + CHILD_NODE + " node not well defined, "
                    + "missing attributes. {0}", pNode.getNodeValue());
        }

        //failed!
        return false;
    }

    public boolean isType(short check) {
        return (check & type) != 0;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getSpriteX() {
        return spriteX;
    }

    public int getSpriteY() {
        return spriteY;
    }

    public String getId() {
        return id;
    }

    public String getDecription() {
        return decription;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getBuildtime() {
        return buildtime;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public short getTech() {
        return tech;
    }
    
}
