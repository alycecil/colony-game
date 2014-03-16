/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.resources;

import colonygame.Main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
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
public class Sprite {

    protected static final String ROOT_NODE = "sprites";
    protected static final String CHILD_NODE = "sprite";
    BufferedImage tileSet;
    BufferedImage tileSetTrans;
    int width, height, startX, startY, deltaX, deltaY;
    int cellsWidth, cellsHeight;

    public Sprite(BufferedImage tileSet, int width, int height,
            int startX, int startY, int deltaX, int deltaY, int cellsWidth, int cellsHeight) {
        this.tileSet = tileSet;
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.cellsHeight = cellsHeight;
        this.cellsWidth = cellsWidth;


        //get trans. color
        Color c;
        c = new Color(tileSet.getRGB(startX, startY));


        //add transparency layer
        tileSetTrans = new BufferedImage(
                tileSet.getWidth(), tileSet.getTileWidth(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = tileSetTrans.createGraphics();
        g2.drawImage(Sprite.makeColorTransparent(tileSet, c), 0, 0, null);
        g2.dispose();

    }

    public BufferedImage getCell(int cell){
        
        if(cell > cellsHeight*cellsHeight){
            //what the hell guy?
            
            Logger.getLogger(Sprite.class.getName()).log(Level.SEVERE, 
                    "Trying to request sprite cell outside valid #{0}",cell);
            
            cell = 0;
        }
        
        int i,j;
        
        i = cell%width;
        j = cell/width;
        
        i=i*(width+deltaX)+startX;
        j=j*(height+deltaY)+startY;
        
        return tileSetTrans.getSubimage(i, j, width, height);
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

                Logger.getLogger(Sprite.class.getName()).log(
                        Level.INFO, "Added {0} sprites to resources.", added);
            }



            //we were successful
            return true;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Sprite.class.getName()).log(
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
        String tempFile = null;
        int x = 0, y = 0, xOff = 0, yOff = 0, xDelta = 0, yDelta = 0, width = 0, height = 0;
        boolean bid, bfile, bx, by, bxOff, byOff, bxDelta, byDelta, bwidth, bheight;

        //setall to false
        bid = false;
        bfile = false;
        bx = false;
        by = false;
        bxOff = false;
        byOff = false;
        bxDelta = false;
        byDelta = false;
        bwidth = false;
        bheight = false;

        /*
         * Example
         * <world>
         * <id>red</id>
         * <file>BITMAPS\260.bmp</file>
         * <x>108</x>
         * <y>46</y>
         * <xOff>37</xOff>
         * <yOff>163</yOff>
         * <xDelta>7</xDelta>
         * <yDelta>7</yDelta>
         * <width>5</width>
         * <height>2</height>
         * </world>
         */
        NodeList children2 = pNode.getChildNodes();

        for (int count2 = 0; count2 < children2.getLength(); count2++) {

            Node tempChild = children2.item(count2);

            if (tempChild.getNodeName().equalsIgnoreCase("id")) {
                //getstring and set
                tempId = tempChild.getTextContent();

                bid = true;
            } else if (tempChild.getNodeName().equalsIgnoreCase("file")) {
                tempFile = tempChild.getTextContent();

                bfile = true;

            } else if (tempChild.getNodeName().equalsIgnoreCase("x")) {
                x = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());

                bx = true;
            } else if (tempChild.getNodeName().equalsIgnoreCase("y")) {
                y = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());

                by = true;
            } else if (tempChild.getNodeName().equalsIgnoreCase("xOff")) {
                xOff = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());

                bxOff = true;
            } else if (tempChild.getNodeName().equalsIgnoreCase("yOff")) {
                yOff = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());

                byOff = true;
            } else if (tempChild.getNodeName().equalsIgnoreCase("xDelta")) {
                xDelta = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());

                bxDelta = true;
            } else if (tempChild.getNodeName().equalsIgnoreCase("yDelta")) {
                yDelta = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());

                byDelta = true;
            } else if (tempChild.getNodeName().equalsIgnoreCase("width")) {
                width = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());

                bwidth = true;
            } else if (tempChild.getNodeName().equalsIgnoreCase("height")) {
                height = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());

                bheight = true;
            } else if (tempChild.getNodeName().equalsIgnoreCase("#text")) {
                //ignore whitespace  
            } else {
                Logger.getLogger(Sprite.class.getName()).log(
                        Level.WARNING,
                        "Current Node type unexpedcted for " + CHILD_NODE
                        + ", {0}", tempChild.getNodeName());
            }

        }

        //all done with attributes
        if (bid && bfile && bx && by && bxOff && byOff && bxDelta
                && byDelta && bwidth && bheight) {

            //read image
            try {
                File imagefile = new File(tempFile);
                BufferedImage img = ImageIO.read(imagefile);

                //add to resources
                Main.resources.addSprite(tempId, new Sprite(img, x, y, xOff, yOff, xDelta, yDelta, width, height));

                return true;
            } catch (IOException ex) {
                Logger.getLogger(Sprite.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            //uh oh
            Logger.getLogger(Sprite.class.getName()).log(
                    Level.WARNING, "current world node not well defined, missing attributes. {0}", pNode.getNodeValue());
        }

        //failed!
        return false;
    }

    /**
     * Takes an image and creates transparency/alpha mapped from a color
     *
     * from
     * http://www.javaworld.com/article/2074105/core-java/making-white-image-backgrounds-transparent-with-java-2d-groovy.html
     *
     * @param im
     * @param color
     * @return
     */
    public static Image makeColorTransparent(final BufferedImage im, final Color color) {
        final ImageFilter filter = new RGBImageFilter() {
            // the color we are looking for (white)... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFFFFFFFF;

            @Override
            public final int filterRGB(final int x, final int y, final int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        final ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    public int getCellWidth() {
        return width;
    }
    public int getCellHeight() {
        return height;
    }
}
