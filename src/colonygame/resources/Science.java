/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.resources;

import colonygame.Main;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
public class Science implements Comparable<Science>{

    protected static final String ROOT_NODE = "science";
    protected static final String CHILD_NODE = "research";
    public static final int NO_UNLOCK = -1;
    int tech, cost;
    String id, shortDesc, longDesc;
    ArrayList<String> requires;
    boolean available = false;
    Image img;
    double paid;

    public Science(String id, int tech, int cost, String shortDesc, String longDesc, ArrayList<String> requires, Image i) {
        this.tech = tech;
        this.cost = cost;
        this.id = id;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.requires = requires;
        img = i;
        paid = 0;

    }

    public String getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public ArrayList<String> getRequires() {
        return requires;
    }

    public int getTech() {
        return tech;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public Image getImage() {
        return img;
    }
    
    public boolean pay(double pay){
        paid+=pay;
        
        return paid>=cost;
    }

    public boolean isPaid() {
        return paid>=cost;
    }
    
    

    public boolean isAvailable(ArrayList<Science> researched) {

        if (available) {
            return true;
        }
        
        if(isPaid()){
            available=true;
        }

        int i = 0;

        Iterator<Science> iter = researched.iterator();

        while (iter.hasNext()) {
            if (requires.contains(iter.next().getId())) {
                i++;
            }
        }

        available = i == requires.size();

        return available;
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

            //add children worlds
            if (doc.hasChildNodes()) {
                //Finds the right root
                int index;
                NodeList roots = doc.getChildNodes();

                for (index = 0; index < roots.getLength(); index++) {
                    if (roots.item(index).getNodeName().equalsIgnoreCase(ROOT_NODE)) {
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

                Logger.getLogger(Science.class.getName()).log(
                        Level.INFO, "Added {0} " + ROOT_NODE + " to resources.", added);
            }


            //we were successful
            return true;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Science.class.getName()).log(
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
        int tech = NO_UNLOCK, cost = 0;
        String shortDesc = null, longDesc = null;
        ArrayList<String> requires = new ArrayList<>();
        String imgName = null;
        String[] temp;



        NodeList children2 = pNode.getChildNodes();

        for (int count2 = 0; count2 < children2.getLength(); count2++) {

            Node tempChild = children2.item(count2);

            if (tempChild.getNodeName().equalsIgnoreCase("id")) {
                //getstring and set
                tempId = tempChild.getTextContent();

            } else if (tempChild.getNodeName().equalsIgnoreCase("int")) {
                //getstring and set
                shortDesc = tempChild.getTextContent();

            } else if (tempChild.getNodeName().equalsIgnoreCase("long")) {
                //getstring and set
                longDesc = tempChild.getTextContent();

            } else if (tempChild.getNodeName().equalsIgnoreCase("tech")) {
                tech = (int) SASLib.Util.Val.VAL(
                        tempChild.getTextContent());

            } else if (tempChild.getNodeName().equalsIgnoreCase("requires")) {
                //
                temp = tempChild.getTextContent().split(",");
                requires.addAll(Arrays.asList(temp));

            } else if (tempChild.getNodeName().equalsIgnoreCase("cost")) {
                cost = (int) SASLib.Util.Val.VAL(
                        tempChild.getTextContent());

            } else if (tempChild.getNodeName().equalsIgnoreCase("image")) {
                imgName = tempChild.getTextContent();

            } else if (tempChild.getNodeName().equalsIgnoreCase("#text")) {
                //ignore whitespace  
            } else {
                Logger.getLogger(Science.class.getName()).log(
                        Level.WARNING, "Current Node type unexpedcted "
                        + "for " + CHILD_NODE + ", {0}", tempChild.getNodeName());
            }

        }

        //all done with attributes
        if (longDesc == null || shortDesc == null || tempId == null) {
            //uh oh
            Logger.getLogger(Science.class.getName()).log(
                    Level.INFO, "current " + CHILD_NODE + " node not well defined, "
                    + "missing attributes. {0}", pNode.getTextContent());

            return false;

        } else {
            BufferedImage img = null;
            try {
                File imagefile = new File(imgName);
                img = ImageIO.read(imagefile);

            } catch (IOException ex) {
                Logger.getLogger(Science.class.getName()).log(Level.SEVERE, "Filename : {0}", imgName);
                Logger.getLogger(Science.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //add to resources
            Main.resources.addScience(tempId, new Science(tempId, tech, cost,
                    shortDesc, longDesc, requires, img));


            //return success
            return true;
        }
    }

    @Override
    public int compareTo(Science o) {
        return getCost()-o.getCost();
    }
}
