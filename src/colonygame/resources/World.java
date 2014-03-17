/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class World {

    protected static final String ROOT_NODE = "worlds";
    protected static final String CHILD_NODE = "world";
    
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
                        + " unexpected, expected "+ROOT_NODE+".");
            }

            //add children worlds
            if (doc.hasChildNodes()) {
                //Finds the right root
                int index;
                NodeList roots = doc.getChildNodes();
                
                for(index = 0; index < roots.getLength(); index++){
                    if(roots.item(index).getNodeName().equalsIgnoreCase(ROOT_NODE)){
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
                
                Logger.getLogger(World.class.getName()).log(
                    Level.INFO, "Added {0} worlds to resources.", added);
            }


            //we were successful
            return true;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(World.class.getName()).log(
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

        Sprite tempSprite = null;
        String tempId = null;



        NodeList children2 = pNode.getChildNodes();

        for (int count2 = 0; count2 < children2.getLength(); count2++) {

            Node tempChild = children2.item(count2);

            if (tempChild.getNodeName().equalsIgnoreCase("id")) {
                //getstring and set
                tempId = tempChild.getTextContent();
            } else if (tempChild.getNodeName().equalsIgnoreCase("sprite")) {
                //get by refference id


                //ask resorces for sprite set
                tempSprite = Main.resources.getSprite(tempChild.getTextContent());

            }else if(tempChild.getNodeName().equalsIgnoreCase("#text")){
              //ignore whitespace  
            } else {
                Logger.getLogger(World.class.getName()).log(
                        Level.WARNING, "Current Node type unexpedcted "
                        + "for world, {0}", tempChild.getNodeName());
            }

        }

        //all done with attributes
        if (tempSprite == null || tempId == null) {
            //uh oh
            Logger.getLogger(World.class.getName()).log(
                    Level.INFO, "current world node not well defined, "
                    + "missing attributes. {0}", pNode.getTextContent());

            return false;

        } else {
            
            //add to resources
            Main.resources.addWorld(tempId, new World(tempSprite, tempId));


            //return success
            return true;
        }
    }
}
