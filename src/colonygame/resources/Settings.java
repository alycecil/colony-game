/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.resources;

import colonygame.game.Person;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
public class Settings {

    protected static final String ROOT_NODE = "initial";
    protected static final String CHILD_NODE_PEOPLE = "people";
    protected static final String CHILD_NODE_LANDERS = "landers";
    int landers;
    ArrayList<Person> people;
    public static final int DEFAULT_LANDERS = 1;

    public Settings() {
        people = new ArrayList<>();
    }

    public boolean readXML(File pfSource) {
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
                        Level.INFO, "Laoded {0} settings.", added);
            }



            //we were successful
            return true;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(BuildingType.class.getName()).log(
                    Level.SEVERE, null, ex);

            return false;
        }
    }

    public boolean readXML(Node pNode) {
        //ensure type
        if (pNode.getNodeName().equalsIgnoreCase(CHILD_NODE_LANDERS)) {
            //read landers information
            //should be a child node of type quantity
            /*
             * <landers>
             <quantity>1</quantity>
             </building>
             */
            int qnty = DEFAULT_LANDERS;
            Node tempChild;

            NodeList children2 = pNode.getChildNodes();

            for (int count2 = 0; count2 < children2.getLength(); count2++) {

                tempChild = children2.item(count2);

                if (tempChild.getNodeName().equalsIgnoreCase("quantity")) {
                    //getstring and set
                    qnty = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());
                } else if (tempChild.getNodeName().equalsIgnoreCase("#text")) {
                    //ignore whitespace  
                } else {
                    Logger.getLogger(BuildingType.class.getName()).log(
                            Level.WARNING,
                            "Current Node type unexpedcted for " + CHILD_NODE_LANDERS
                            + ", {0}", tempChild.getNodeName());
                }
            }

            setLanders(qnty);

            return true;



        } else if (pNode.getNodeName().equalsIgnoreCase(CHILD_NODE_PEOPLE)) {
            /* node of form
             * <people>
             <gender>Female</gender>
             <educated>true</educated>
             <age>2500</age>
             <quantity>10</quantity>
             </people>   
             */
            //

            boolean tGender, tEducated;
            int tAge = Person.MIN_ADULT, tQuantity = 1;
            Person tPerson;
            tGender = true;
            tEducated = false;
            Node tempChild;


            NodeList children2 = pNode.getChildNodes();

            //read the node
            for (int count2 = 0; count2 < children2.getLength(); count2++) {

                tempChild = children2.item(count2);

                if (tempChild.getNodeName().equalsIgnoreCase("age")) {
                    //getstring and set
                    tAge = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());
                } else if (tempChild.getNodeName().equalsIgnoreCase("quantity")) {
                    //getstring and set
                    tQuantity = (int) SASLib.Util.Val.VAL(tempChild.getTextContent());
                } else if (tempChild.getNodeName().equalsIgnoreCase("gender")) {
                    //getstring and set
                    tGender = tempChild.getTextContent().equalsIgnoreCase("male");
                } else if (tempChild.getNodeName().equalsIgnoreCase("educated")) {
                    //getstring and set
                    tEducated = tempChild.getTextContent().equalsIgnoreCase("true");
                } else if (tempChild.getNodeName().equalsIgnoreCase("#text")) {
                    //ignore whitespace  
                } else {
                    Logger.getLogger(BuildingType.class.getName()).log(
                            Level.WARNING,
                            "Current Node type unexpedcted for " + CHILD_NODE_LANDERS
                            + ", {0}", tempChild.getNodeName());
                }
            }
            
            
            //make the people
            for(int i = 0; i < tQuantity; i++){
                tPerson = new Person(Person.getState(tAge), 
                        -tAge, tGender, tEducated, null, 
                        null, null);
                
                people.add(tPerson);
            }
            
            return true;

        }
        return false;

    }

    public void setLanders(int landers) {
        this.landers = landers;
    }

    public int getLanders() {
        return landers;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public boolean addPerson(Person p) {
        return people.add(p);
    }
}
