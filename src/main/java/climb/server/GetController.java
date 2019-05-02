package climb.server;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;

@RestController
public class GetController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
@RestController
class getUsers {

		   @GetMapping("/allUsers")
    public String allUsers(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return getAllUsers();
    }    
	public String getAllUsers() {
		StringBuilder user = null;
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://mysql.dsv.su.se:3306/vady6245","vady6245","lie1NaWaeWai");
			Statement stmt=con.createStatement();
			String sql = "Select * from Users";
			ResultSet rs= stmt.executeQuery(sql);
			
			
			while(rs.next()) {
				user.append(rs.getString("Name") + ", " + rs.getString("Password") + "\n");
		}
		} catch (Exception e) {System.out.print(e);}
		return user.toString();
		}

}
