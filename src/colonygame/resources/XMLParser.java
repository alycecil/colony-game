/*
 * shamelessly an implementation of information available on 
 * http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 */
package colonygame.resources;

import java.io.File;
import java.io.IOException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author TOM
 */
public abstract class XMLParser {

    public abstract boolean setXMLSource(File xmlFile);

    public abstract boolean parse() throws IOException;

    protected boolean parseNode(NodeList nodeList) {

        for (int count = 0; count < nodeList.getLength(); count++) {

            Node tempNode = nodeList.item(count);
            
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                
                //tempNode.getNodeName()
                
                if(tempNode.getNodeName().equalsIgnoreCase("worlds")){
                    
                }else if(tempNode.getNodeName().equalsIgnoreCase("sprites")){
                    
                }else if(tempNode.getNodeName().equalsIgnoreCase("maps")){
                    
                }
                
            }
            

        }


        return true;

    }
}
