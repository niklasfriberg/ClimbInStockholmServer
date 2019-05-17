package climb.server;

import org.springframework.http.converter.json.*;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.my.schema.external.net.opengis.kml.*;

public class Parser {
    
    public Parser(){
        try {
            JAXBContext jc = JAXBContext.newInstance(PlacemarkType.class);
            Unmarshaller u = jc.createUnmarshaller();
            Marshaller m = jc.createMarshaller();
            PlacemarkType pm = (PlacemarkType) u.unmarshal(new File("src/main/resources/Stockholm.kml"));
            System.out.println(pm.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
}