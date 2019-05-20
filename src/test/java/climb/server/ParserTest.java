package climb.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParserTest {

    @Test
    public void testParse(){
        Parser p = new Parser();
        assertNotNull(p.parse());
        System.err.println("hej");
        System.out.println("hej");
    }
}