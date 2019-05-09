package climb.server;

import static org.junit.Assert.assertEquals;

import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetControllerTests {
    GetController gC;
    @Before
    public void before (){
    gC = new GetController();
    }

    @Test
    public void loginTest() {
        assertEquals("{\"Name\":\"vahab\"}", gC.login("vahab", "123"));
    }

    @Test
    public void getUsersTest() {
        assertEquals("{\"Name\":[\"user\",\"vahab\"],\"Password\":[\"passw\",\"123\"]}", gC.getAllUsers());
    }

    @Test
    public void getMarkersTest(){
        assertEquals("{\"Beskrivning\":[\"berg1beskr\",\"berg2beskr\"],\"Longitud\":[\"111.0\",\"222.0\"],\"Latitud\":[\"111.0\",\"222.0\"],\"CragName\":[\"berg1\",\"berg2\"]}", gC.getMarkers());
    }

    @Test
    public void getRoutesTest(){
        assertEquals("{\"Svårighet\":[\"enkel\",\"omöjlig\"],\"Beskrivning\":[\"Detta klätterställe är för bebisar\",\"You will fall to your de4th\"],\"RouteName\":[\"route2\",\"route3\"],\"CragName\":[\"berg2\",\"berg2\"],\"Höjd\":[\"1.0\",\"55.0\"],\"Rep\":[\"1\",\"0\"]}", gC.getRoutes("berg2"));
    }

    

}
