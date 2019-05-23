package climb.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class XMLParser {

    private Document document;

    public XMLParser(String filename) {
        try {
            downloadFile();
            File file = new File("Stockholm.gpx");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            this.document = db.parse(file);

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void downloadFile() throws Exception{
        URL url = new URL("https://raw.githubusercontent.com/niklasfriberg/ClimbInStockholmServer/master/src/main/resources/Stockholm.gpx");
        URLConnection connection = url.openConnection();
        InputStream in = connection.getInputStream();
        FileOutputStream fos = new FileOutputStream(new File("Stockholm.gpx"));
        byte[] buf = new byte[512];
        while(true){
            int len = in.read(buf);
            if (len == -1) {
                break;
            }
            fos.write(buf, 0, len);
        }
        in.close();
        fos.flush();
        fos.close();
    }
    //testkod
    public String getDocument(){
        if (document == null){
            return "hej jag är null";
        }
        return document.getXmlEncoding();
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

    public String getName(int i) {
        return document.getElementsByTagName("wpt").item(i).getChildNodes().item(1).getTextContent();
    }

    public String getDesc(int i) {
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
        if(document == null)
            return 0;
        return document.getElementsByTagName("wpt").getLength();
    }

    public boolean hasCoords(int i) {
        return getLat(i) != 0;
    }

}