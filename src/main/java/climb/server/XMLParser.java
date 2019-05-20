package climb.server;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class XMLParser {

    private Document document;

    public XMLParser(String filename) {
        try {
            File file = new File(filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            this.document = db.parse(file);

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public String get(int i) {
        if (isCrag(i)) {
            return getName(i);
        }
        if (isRoute(i)) {
            return getName(i) + "\ndescription: " + getDesc(i) + "\nlatlng: " + getLat(i) + " , " + getLng(i);
        }
        return "ingen crag";
    }

    public boolean isCrag(int i) {
        return document.getElementsByTagName("wpt").item(i).getChildNodes().getLength() <= 3
                && getName(i).contains("Crag");
    }

    public boolean isRoute(int i) {
        return document.getElementsByTagName("wpt").item(i).getChildNodes().getLength() >= 4;
    }

    private String getName(int i) {
        return document.getElementsByTagName("wpt").item(i).getChildNodes().item(1).getTextContent();
    }

    private String getDesc(int i) {
        return document.getElementsByTagName("wpt").item(i).getChildNodes().item(3).getTextContent().trim();
    }

    public double getLat(int i) {
        String str = document.getElementsByTagName("wpt").item(i).getAttributes().getNamedItem("lat").getTextContent();
        return str == null || str == "" ? 0d : Double.parseDouble(str);
    }

    public double getLng(int i) {
        String str = document.getElementsByTagName("wpt").item(i).getAttributes().getNamedItem("lon").getTextContent();
        return str == null || str == "" ? 0d : Double.parseDouble(str);
    }

    public int getLength() {
        return document.getElementsByTagName("wpt").getLength();
    }

    public boolean hasCoords(int i) {
        return getLat(i) != 0;
    }

}