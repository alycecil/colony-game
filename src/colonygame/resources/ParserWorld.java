/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.resources;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author WilCecil
 */
public class ParserWorld extends XMLParser {

    File lfSource;

    public ParserWorld() {
    }

    @Override
    public boolean parse() throws IOException {
        try {
            //parse as DOM

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(lfSource);

            //do norm, as its the norm!
            doc.getDocumentElement().normalize();

            if (!doc.getDocumentElement().getNodeName().equalsIgnoreCase("worlds")) {
                throw new ParserConfigurationException("Root Node of Type "
                        + doc.getDocumentElement().getNodeName()
                        + " unexpected, expected world.");
            }

            if (doc.hasChildNodes()) {
                parseNode(doc.getChildNodes());
            }

            //we were successful
            return true;

        } catch (ParserConfigurationException | SAXException ex) {
            Logger.getLogger(ParserWorld.class.getName()).log(
                    Level.SEVERE, null, ex);

            return false;
        }
    }

    @Override
    public boolean setXMLSource(File xmlFile) {

        lfSource = xmlFile;

        return true;

    }
}
