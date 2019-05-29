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
            URL url = new URL(
                    "https://raw.githubusercontent.com/niklasfriberg/ClimbInStockholmServer/master/src/main/resources/Stockholm.gpx");
            URLConnection connection = url.openConnection();
            InputStream in = connection.getInputStream();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            this.document = db.parse(in);
            in.close();

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public File downloadFile() throws Exception {
        URL url = new URL(
                "https://raw.githubusercontent.com/niklasfriberg/ClimbInStockholmServer/master/src/main/resources/Stockholm.gpx");
        URLConnection connection = url.openConnection();
        InputStream in = connection.getInputStream();
        File f = new File("data0" + File.separator + "Group75" + File.separator + "download.gpx");
        FileOutputStream fos = new FileOutputStream(f);
        byte[] buf = new byte[512];
        while (true) {
            int len = in.read(buf);
            if (len == -1) {
                break;
            }
            fos.write(buf, 0, len);
        }
        in.close();
        fos.flush();
        fos.close();
        return f;
    }

    // testkod
    public String getDocument() {
        if (document == null) {
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
        // return 2;
    }

    public double getLng(int i) {
        String str = document.getElementsByTagName("wpt").item(i).getAttributes().getNamedItem("lon").getTextContent();
        return str == null || str == "" ? 0d : Double.parseDouble(str);
        // return 1;
    }

    public int getLength() {
        if (document == null)
            return 0;
        return document.getElementsByTagName("wpt").getLength();
    }

    public String getGrade(int index) {
        String trimmedString = getName(index).trim();
        trimmedString = trimmedString.replaceAll("\\*", "");
        String[] tokens = trimmedString.split("\\s", 0);
        String grade = "";
        try {
            for (int i = 0; i < tokens.length; i++) {
                if (Character.isUpperCase(tokens[i].charAt(0))) {
                    break;
                }
                grade += tokens[i] + " ";
            }
        } catch (Exception e) {
            return "No grade";
        }
        return grade;
    }

    public String getHeigth(int index) {
        String trimmedString = getName(index).trim();
        trimmedString.replaceAll("\\*", "");
        String[] tokens = getName(index).split("\\s", 0);
        String heigth = "";
        int j = 0;
        try {
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].startsWith("(")) {
                    heigth += tokens[i];
                    j = i + 1;
                    break;
                }
            }
            if (j != 0) {
                for (int i = j; i < tokens.length; i++) {
                    heigth += " " + tokens[i];
                }
            }
        } catch (Exception e) {
            return "No height found";
        }
        return heigth;
    }

    public String getName(String str) {
        String trimmedString = str.replaceAll("\\*", "").trim();
        String[] tokens = trimmedString.split("\\s", 0);
        String name = "";
        int j = 0;
        for (int i = 0; i < tokens.length; i++) {
            if (Character.isUpperCase(tokens[i].charAt(0)) && name == "") {
                name += tokens[i];
                // j = i + 1;
                break;
            }

        }
        // for (int i = j; i < tokens.length; i++) {
        //     if (tokens[i].startsWith("(")) {
        //         break;
        //     }
            //name += " " + tokens[i];
            return name;
        }
        

    public boolean hasCoords(int i) {
        return getLat(i) != 0;
    }

}