package climb.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PutControllerTests {
    PutController pC;
    DeleteController dC;

    @Before
    public void before() {
        pC = new PutController();
        dC = new DeleteController();
    }

    @Test
    public void putMessageTest() {
        pC.putMessage("user", "message");

    }

    @Test
    public void updateUserTest() {
        GetController gC = new GetController();
        pC.putUser("testp", "test");
        sleep();
        assertEquals("{\"Name\":\"testp\",\"Password\":\"test\"}", gC.getUser("testp"));
        dC.deleteUser("testp", "test");
        sleep();
        assertNotEquals("{\"Name\":\"testp\",\"Password\":\"test\"}", gC.getUser("testp"));
    }

    public void sleep(){
        try{
            Thread.sleep(500);
        }
        catch (InterruptedException e){

        }
    }
}
