package climb.server;

import static org.junit.Assert.assertEquals;

import javax.validation.constraints.AssertTrue;
import java.sql.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SQLTests {

    GetController gC;
    PutController pC;
    DeleteController dC;

    @Before
    public void before() {
        gC = new GetController();
        pC = new PutController();
        dC = new DeleteController();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://mysql.dsv.su.se:3306/vady6245?useUnicode=true&serverTimezone=UTC", "vady6245",
                    "lie1NaWaeWai");
            Statement stmt = con.createStatement();
            stmt.executeQuery("DELETE FROM Route_Mock, Crag_Mock, FacebookUsers_Mock, Messages_Mock, Users_Mock");
            con.close();
        } catch (Exception e) {
        }
    }

    @Test
    public void loginTest() {
        assertEquals("{\"Name\":\"vahab\"}", gC.login("vahab", "123"));
    }

    @Test
    public void getUsersTest() {
        assertEquals("{\"Name\":[\"user\",\"vahab\"],\"Password\":[\"passw\",\"123\"]}", gC.getAllUsers());
    }

    // @Test
    // public void getMarkersTest(){
    // assertEquals("{\"Beskrivning\":[\"berg1beskr\",\"berg2beskr\"],\"Longitud\":[\"111.0\",\"222.0\"],\"Latitud\":[\"111.0\",\"222.0\"],\"CragName\":[\"berg1\",\"berg2\"]}",
    // gC.getMarkers());
    // }

    @Test
    public void getRoutesTest() {
        assertEquals(
                "{\"Svårighet\":[\"enkel\",\"omöjlig\"],\"Beskrivning\":[\"Detta klätterställe är för bebisar\",\"You will fall to your de4th\"],\"RouteName\":[\"route2\",\"route3\"],\"CragName\":[\"berg2\",\"berg2\"],\"Höjd\":[\"1.0\",\"55.0\"],\"Rep\":[\"1\",\"0\"]}",
                gC.getRoutes("berg2"));
    }

    // @Test
    // public void getCragTest(){
    // assertEquals("{\"Svårighet\":\"+16\",\"Beskrivning\":[\"berg1beskr\",\"Man
    // måste ha ett långt rep med hjälm. Glöm inte hjälmen när du klättrar, annars
    // kommer det göra ont när du ramlar, du kommer att ramla någon gång, så det
    // säkra före det osäkra som vi säger. Lycka
    // till!\"],\"Longitud\":\"18.063242\",\"RouteName\":\"route1\",\"Latitud\":\"59.364599\",\"CragName\":[\"berg1\",\"berg1\"],\"Höjd\":\"15.0\",\"Rep\":\"1\"}",
    // gC.getCrag("berg2"));
    // }

}
