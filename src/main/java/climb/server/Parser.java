package climb.server;

import org.springframework.http.converter.json.*;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {

    public String parse() {
        StringBuilder sb = new StringBuilder();
        System.out.println("hej");
        try {
            File stocks = new File("Stockholm.kml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(stocks);
            doc.getDocumentElement().normalize();

            System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
            NodeList nodes = doc.getElementsByTagName("*");
            System.out.println("==========================");

            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    sb.append(element.toString() + " : " + getValue("*", element));
                    listAllAttributes(element);
                }
            }
            System.out.println("hej");
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    private String getValue(String tag, Element element) {
        try {

            NodeList nodes = element.getElementsByTagName("*").item(0).getChildNodes();
            Node node = (Node) nodes.item(0);
            return node.getNodeValue().toString();
        } catch (Exception e) {
            return "inga koordinater";
        }
    }
    public void listAllAttributes(Element element) {

        System.out.println("List attributes for node: " + element.getNodeName());

        // get a map containing the attributes of this node
            NamedNodeMap attributes = element.getAttributes();

        // get the number of nodes in this map

        int numAttrs = attributes.getLength();


        for (int i = 0; i < numAttrs; i++) {
       Attr attr = (Attr) attributes.item(i);
       String attrName = attr.getNodeName();

String attrValue = attr.getNodeValue();
System.out.println("Found attribute: " + attrName + " with value: " + attrValue);
        }
    }
}