/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.resources;

import colonygame.game.Building;
import colonygame.Main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
public class WorldMap {

    protected static final String ROOT_NODE = "maps";
    protected static final String CHILD_NODE = "map";
    World parent;
    public static final char CLEAR = 1;
    public static final char DOZED = 0;
    public static final char ROUGH = 2;
    public static final char DIFFICULT = 3;
    public static final char IMPASSIBLE = 4;
    public static char DEFAULT_VALUE = CLEAR;
    public static final char STANDARD_DEPTH = 5;
    public static final char UNDERGROUND_DOZED = 6;
    public static final char DISCOVERED = 8;
    public static final char UNDISCOVERED = 9;
    char[][][] map;
    Image image;
    Building[][][] buildings;

    public WorldMap(char[][][] map, World pWorld) {
        this(map, pWorld, null);

        image = renderMap(map, 0);
    }

    public WorldMap(char[][][] map, World pWorld, Image img) {
        this.map = map;
        image = img;

        parent = pWorld;

        buildings = new Building[map.length][map[0].length][map[0][0].length];
    }

    public static Image renderMap(char[][][] map, int depth) {
        //get the sub image
        char[][] squares = map[depth];

        //make space
        BufferedImage img = new BufferedImage(squares.length,
                squares[0].length, BufferedImage.TYPE_INT_RGB);


        //\/\/\/\/\/\/\/\
        //--  render!  --
        //\/\/\/\/\/\/\/\

        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares.length; j++) {
                Color c = Color.pink;

                if (squares[i][j] == DOZED) {
                    c = Color.GREEN;

                } else if (squares[i][j] == CLEAR) {
                    c = Color.YELLOW;

                } else if (squares[i][j] == ROUGH) {
                    c = Color.RED;

                } else if (squares[i][j] == DIFFICULT) {
                    c = Color.GREEN;

                } else if (squares[i][j] == IMPASSIBLE) {
                    c = Color.WHITE;

                } else if (squares[i][j] == STANDARD_DEPTH) {
                    c = Color.DARK_GRAY;

                } else if (squares[i][j] == UNDERGROUND_DOZED) {
                    c = Color.GREEN;

                } else if (squares[i][j] == DISCOVERED) {
                    c = Color.LIGHT_GRAY;

                } else if (squares[i][j] == UNDISCOVERED) {
                    c = Color.BLACK;

                }

                img.setRGB(i, j, c.getRGB());
            }
        }

        //return image
        return img;
    }

    public static boolean readXML(File pfSource) {
        try {
            //parse as DOM

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(pfSource);

            //do norm, as its the norm!
            doc.getDocumentElement().normalize();

            if (!doc.getDocumentElement().getNodeName().equalsIgnoreCase(
                    ROOT_NODE)) {
                throw new ParserConfigurationException("Root Node of Type "
                        + doc.getDocumentElement().getNodeName()
                        + " unexpected, expected " + ROOT_NODE + ".");
            }

            //add children worlds
            if (doc.hasChildNodes()) {
                //Finds the right root
                int index;
                NodeList roots = doc.getChildNodes();

                for (index = 0; index < roots.getLength(); index++) {
                    if (roots.item(index).getNodeName().equalsIgnoreCase(
                            ROOT_NODE)) {
                        break;
                    }
                }

                /////
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

                Logger.getLogger(WorldMap.class.getName()).log(
                        Level.INFO, "Added {0} maps to resources.", added);
            }


            //we were successful
            return true;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(WorldMap.class.getName()).log(
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
        String tempImage = null;
        String tempTerrain = null;
        World tempWorld = null;



        Color clear, rough, difficult, impassible;

        //init to null
        clear = null;
        rough = null;
        difficult = null;
        impassible = null;

        boolean bid, bTerrain, bImage, bClear, bRough, bDifficult,
                bImpassible, bWorld;

        //setall to false
        bid = false;
        bTerrain = false;
        bImage = false;
        bClear = false;
        bRough = false;
        bDifficult = false;
        bImpassible = false;
        bWorld = false;

        /*
         * Example
         * <map>
         <id>500</id>
         <image>BITMAPS\500.bmp</image>
         <terrain>BITMAPS\500A.bmp</terrain>
         <clear>0,0,0</clear>
         <rough>128,0,0</rough>
         <difficult>0,128,0</difficult>
         <impassible>128,128,0</impassible>
         </map>
         */
        NodeList children2 = pNode.getChildNodes();

        for (int count2 = 0; count2 < children2.getLength(); count2++) {

            Node tempChild = children2.item(count2);

            if (tempChild.getNodeName().equalsIgnoreCase("id")) {
                //getstring and set
                tempId = tempChild.getTextContent();

                bid = true;
            } else if (tempChild.getNodeName().equalsIgnoreCase("image")) {
                tempImage = tempChild.getTextContent();

                bImage = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("terrain")) {
                tempTerrain = tempChild.getTextContent();

                bTerrain = true;
            } else if (tempChild.getNodeName().equalsIgnoreCase("clear")) {


                clear = getColor(tempChild.getTextContent());

                bClear = true;
            } else if (tempChild.getNodeName().equalsIgnoreCase("difficult")) {

                difficult = getColor(tempChild.getTextContent());

                bDifficult = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("rough")) {

                rough = getColor(tempChild.getTextContent());

                bRough = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("difficult")) {

                difficult = getColor(tempChild.getTextContent());

                bDifficult = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("impassible")) {

                impassible = getColor(tempChild.getTextContent());

                bImpassible = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("world")) {

                tempWorld = Main.resources.getWorld(tempChild.getTextContent());

                if (tempWorld != null) {
                    bWorld = true;
                }

            } else if (tempChild.getNodeName().equalsIgnoreCase("#text")) {
                //ignore whitespace  
            } else {
                Logger.getLogger(World.class.getName()).log(
                        Level.WARNING,
                        "Current Node type unexpedcted for " + CHILD_NODE
                        + ", {0}", tempChild.getNodeName());
            }

        }

        //all done with attributes
        if (bid && bClear && bDifficult && bImage && bWorld
                && bImpassible && bRough && bTerrain) {

            //read image


            try {
                File imageTerrain = new File(tempTerrain);
                BufferedImage imgTerrain = ImageIO.read(imageTerrain);

                File imageMap = new File(tempImage);
                BufferedImage imgMap = ImageIO.read(imageMap);

                //add to resources
                Main.resources.addMap(tempId, mapFromImage(imgTerrain, imgMap,
                        tempWorld, clear, rough, difficult, impassible));

                return true;
            } catch (IOException ex) {
                Logger.getLogger(WorldMap.class.getName()).log(Level.SEVERE,
                        null, ex);
                Logger.getLogger(WorldMap.class.getName()).log(Level.SEVERE,
                        "File names:{0}, and {1}",
                        new Object[]{tempTerrain, tempImage});
            }
        } else {

            //uh oh
            Logger.getLogger(WorldMap.class.getName()).log(
                    Level.WARNING,
                    "current world node not well defined, missing attributes. "
                    + "{0}", pNode.getTextContent());
        }

        //failed!
        return false;
    }

    /**
     * Creates RGB Color from text of form ###, ###, ###
     *
     * @param textContent
     * @return java.awt.Color
     */
    private static Color getColor(String textContent) {
        //for creating color
        String[] tempString;
        int r, g, b;

        tempString = textContent.split(",");

        r = (int) SASLib.Util.Val.VAL(tempString[0]);
        g = (int) SASLib.Util.Val.VAL(tempString[1]);
        b = (int) SASLib.Util.Val.VAL(tempString[2]);

        return new Color(r, g, b);
    }

    /**
     * Given the colors for terrain types and a terrain image, create a map
     *
     * @param imgTerrain
     * @param clear
     * @param rough
     * @param difficult
     * @param impassible
     * @return
     */
    private static WorldMap mapFromImage(BufferedImage imgTerrain,
            BufferedImage imgMap, World pWorld,
            Color clear, Color rough, Color difficult, Color impassible) {

        char[][][] map =
                new char[STANDARD_DEPTH][imgTerrain.getWidth()][imgTerrain.getHeight()];


        //set underground to darkness
        for (int z = 0; z < 5; z++) {
            for (int i = 0; i < imgTerrain.getWidth(); i++) {
                for (int j = 0; j < imgTerrain.getHeight(); j++) {
                    map[z][i][j] = UNDISCOVERED;
                }
            }
        }

        //read image and set ground
        Color temp;
        for (int i = 0; i < imgTerrain.getWidth(); i++) {
            for (int j = 0; j < imgTerrain.getHeight(); j++) {
                temp = new Color(imgTerrain.getRGB(i, j));

                if (temp.equals(clear)) {
                    map[0][i][j] = CLEAR;
                } else if (temp.equals(rough)) {
                    map[0][i][j] = ROUGH;
                } else if (temp.equals(difficult)) {
                    map[0][i][j] = DIFFICULT;
                } else if (temp.equals(impassible)) {
                    map[0][i][j] = IMPASSIBLE;
                } else {
                    Logger.getLogger(World.class.getName()).log(
                            Level.WARNING,
                            "Unknown color ({0},{1},{2}) at ({3},{4})\n",
                            new Object[]{temp.getRed(), temp.getGreen(),
                        temp.getBlue(), i, j});

                    map[0][i][j] = DEFAULT_VALUE;
                }
            }
        }

        return new WorldMap(map, pWorld, imgMap);

    }

    public int getDepth() {
        return map.length;

    }

    public int getWidth() {
        return map[0].length;

    }

    public int getHeight() {
        return map[0][0].length;

    }

    public char[][] getBlock(int x, int y, int z, int width, int height) {
        char[][] block = new char[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //
                //\/\/\/\/\/\/\/\/\/\/\/\/\//
                //   copy subset of map    //
                //\/\/\/\/\/\/\/\/\/\/\/\/\//
                //
                try {
                    block[i][j] = map[z][x + i][y + j];
                } catch (Exception e) {
                    block[i][j] = DEFAULT_VALUE;
                }
            }
        }

        return block;
    }

    public World getWorld() {
        return parent;
    }

    public Image getImage() {

        //make space
        BufferedImage img = new BufferedImage(map[0].length,
                map[0][0].length, BufferedImage.TYPE_INT_RGB);


        Graphics g = img.createGraphics();

        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }

        //\/\/\/\/\/\/\/\
        //--  render!  --
        //\/\/\/\/\/\/\/\

        for (int i = 0; i < buildings[0].length; i++) {
            for (int j = 0; j < buildings[0][0].length; j++) {

                if (buildings[0][i][j] != null) {
                    g.setColor(Color.GREEN);
                    g.fillRect(i, j, 1, 1);
                }
                else if (map[0][i][j] == DOZED) {
                    g.setColor(Color.white);
                    g.fillRect(i, j, 1, 1);
                }
            }
        }


        return img;
    }

    public char getTile(int x, int y, int z) {
        try {
            return map[z][x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return DEFAULT_VALUE;
        }
    }

    public boolean setTile(int x, int y, int z, char value) {
        try {
            map[z][x][y] = value;
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

    }

    public Building[][][] getBuildings() {
        return buildings;
    }

    public Building[][] getBlockBuildings(int x, int y, int z, int width, int height) {
        Building[][] block = new Building[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //
                //\/\/\/\/\/\/\/\/\/\/\/\/\//
                //   copy subset of map    //
                //\/\/\/\/\/\/\/\/\/\/\/\/\//
                //
                try {
                    block[i][j] = buildings[z][x + i][y + j];
                } catch (Exception e) {
                    block[i][j] = null;
                }
            }
        }

        return block;
    }

    public Building getBuilding(int x, int y, int z) {
        try {
            return buildings[z][x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

    }

    public boolean setBuilding(int x, int y, int z, Building value) {
        try {
            buildings[z][x][y] = value;
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

    }

    public String toString(int x, int y, int z) {
        String s = "Tile::";

        if (map[z][x][y] == CLEAR) {
            s += "Clear";
        } else if (map[z][x][y] == DIFFICULT) {
            s += "Difficult";
        } else if (map[z][x][y] == DISCOVERED) {
            s += "Discovered";
        } else if (map[z][x][y] == DOZED) {
            s += "Bulldozed";
        } else if (map[z][x][y] == IMPASSIBLE) {
            s += "Impassible";
        } else if (map[z][x][y] == ROUGH) {
            s += "Rough";
        } else if (map[z][x][y] == UNDERGROUND_DOZED) {
            s += "Dozed";
        } else if (map[z][x][y] == UNDISCOVERED) {
            s += "Unknown";
        }



        if (buildings[z][x][y] != null) {
            s += "\nBuilding::" + buildings[z][x][y].getType().getId();
        }


        return s;
    }
}
