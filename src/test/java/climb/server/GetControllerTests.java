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
        assertEquals("{\"Name\":[\"vahab\",\"user\"],\"Password\":[\"123\",\"passw\"]}", gC.allUsers());
    }
    

}
