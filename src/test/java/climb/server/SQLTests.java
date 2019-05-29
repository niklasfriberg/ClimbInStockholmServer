package climb.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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

    public void sleep(){
        try{
            Thread.sleep(50);
        }
        catch (InterruptedException e){

        }
    }

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
            // stmt.execute("DELETE FROM Route_Mock; DELETE FROM Crag_Mock; DELETE FROM FacebookUsers_Mock; DELETE FROM Messages_Mock; DELETE FROM Users_Mock");
            stmt.execute("DELETE FROM Route_Mock");
            stmt.execute("DELETE FROM Crag_Mock");
            stmt.execute("DELETE FROM Messages_Mock");
            stmt.execute("DELETE FROM FacebookUsers_Mock");
            stmt.execute("DELETE FROM Users_Mock");
            con.close();
        } catch (Exception e) {
        }
    }

    @Test
    public void userTest(){
        sleep();
        assertEquals("Kolla att databasen för användare är tom", "{}", gC.getUserMock("user"));
        pC.putUserMock("user", "123");
        pC.putFacebookUserMock("456", "facebook");
        sleep();
        assertEquals("Hämta användare", "{\"Name\":\"user\"}", gC.getUserMock("user"));
        assertEquals("Hämta facebookanvändare", "{\"Name\":\"facebook\"}", gC.getUserMock("facebook"));
        assertEquals("Testa login", "{\"Name\":\"user\"}", gC.loginMock("user", "123"));
        dC.deleteUserMock("user");
        dC.deleteUserMock("facebook");
        sleep();
        assertEquals("Kolla att databasen för användare är tom efter deleteUser", "{}", gC.getUserMock("user"));
    }

    @Test
    public void messageTest(){
        assertEquals("Kolla att databasen för meddelanden är tom", "{}", gC.getAllMessagesMock());
        pC.putUserMock("user", "123");
        pC.putFacebookUserMock("456", "facebook");
        sleep();
        pC.putTheMessageMock("user", "usermessage");
        pC.putTheMessageMock("facebook", "facebookmessage");
        // pC.putTheMessageMock("nonexistent", "shouldn't enter the chat wall"); ska inte komma med.
        assertEquals("finns meddelanden?", 
        "{\"Message\":[\"usermessage\",\"facebookmessage\"],\"Username\":[\"user\",\"facebook\"]}",
        gC.getAllMessagesMock());
    }

    @Test
    public void cragTest(){
        assertEquals("getMarkers ska vara tom", "{}", gC.getMarkersMock());
        pC.updateCragsFromAPIMock();
        sleep();
        assertNotEquals("Markers ska finnas", "{}", gC.getMarkersMock());
        // assertEquals("getCrag Flaten", "{}", gC.getCragMock("Flaten"));Vänta tills parsern är fullt implementerad
        dC.deleteCragMock("Flaten");
        sleep();
        // assertEquals("är Flaten borta?", "{}", gC.getCragMock("Flaten");Vänta på att databasen är komplett
    }

}
