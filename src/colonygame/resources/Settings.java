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
 * @author WilCecil
 */
public class Settings {

    protected static final String ROOT_NODE = "initial";
    protected static final String CHILD_NODE_PEOPLE = "people";
    protected static final String CHILD_NODE_LANDERS = "landers";
    protected static final String CHILD_NODE_RATES = "rates";
    /**
     * <p>
     * Mortality Calculator for sample size = 50000
     * <br>Added Medical false
     * <p>
     * Average Age 4896.61444
     * <p>
     * Death By Age Group Block<br>
     * Infant = 8848<br>
     * Block Child = 369<br>
     * Block Teen = 235<br>
     * Block Adult = 1764<br>
     * Block Elder = 38784<br>
     * Block Ancient = 0
     * <p>
     *
     * Mortality Calculator for sample size = 50000<br>
     * Added Medical true
     * <p>
     * Average Age 5551.92698
     * <p>
     * Death By Age Group Block<br>
     * Infant = 4657<br>
     * Block Child = 194 <br>
     * Block Teen = 118<br>
     * Block Adult = 976<br>
     * Block Elder = 44055<br>
     * Block Ancient = 0
     *
     * <p>
     * Added Sick true
     * <p>
     * Average Age 2542.5799
     *
     */
    public static final double DEFAULT_MORTALITY_INFANT = 1.0 / 1000;
    public static final double DEFAULT_MORTALITY_CHILD = 1.0 / 100000;
    public static final double DEFAULT_MORTALITY_TEEN = 1.0 / 100000;
    public static final double DEFAULT_MORTALITY_ADULT = 1.0 / 100000;
    public static final double DEFAULT_MORTALITY_ELDER = 1.0 / 100;
    public static final double DEFAULT_MORTALITY_PREGNANT = 1.0 / 100;
    public static final double DEFAULT_MORTALITY_SICK_MEDICAL = 1.0 / 100000;
    public static final double DEFAULT_MORTALITY_PREGNANT_MEDICAL = 1.0 / 10000;
    public static final double DEFAULT_MEDICAL_MODIFIER = 1.0 / 2;
    public static final double DEFAULT_SICK_MODIFIER = 4.0;
    public static final double DEFAULT_HUNGRY_MODIFIER = 10.0;
    public static final double DEFAULT_STARVING_MODIFIER = 100.0;
    /*
     * Female 15-19 yr	2000-2005	 	Births per 1,000 women	43.7	 
     * Female 20-24 yr	2000-2005	 	Births per 1,000 women	104.0	 
     * Female 25-29 yr	2000-2005	 	Births per 1,000 women	114.5	 
     * Female 30-34 yr	2000-2005	 	Births per 1,000 women	93.5	 
     * Female 35-39 yr	2000-2005	 	Births per 1,000 women	42.8	 
     * Female 40-44 yr	2000-2005	 	Births per 1,000 women	8.5	 
     * Female 45-49 yr	2000-2005	 	Births per 1,000 women	0.5	 
     */
    
    public static final double DEFAULT_PREGNANCY_EVENT_TEEN = 42.7/1000 / (1900-1500);
    public static final double DEFAULT_PREGNANCY_EVENT_ADULT = 2040.0/1000 / (6500-1900);
    public static final double DEFAULT_PREGNANCY_MARRIAGE_MOD = 8.0;
    public static final double DEFAULT_PREGNANCY_FERTILE_MOD = 10;
    public static final double DEFAULT_CHANCE_FERTILE = 1.0/1000;
    
    public static final double DEFAULT_MARRIAGE_CHANCE = .5 / (3500-1900);
    
    
    
    /**
     * 3/4ths of a year
     */
    public static final int DEFAULT_PREGNANCY_LENGTH = 75;
    
    
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
            for (int i = 0; i < tQuantity; i++) {
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
