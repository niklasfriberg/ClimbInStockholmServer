package climb.server;

import java.sql.*;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
// import climb.server.User; //testa
//import climb.server.UserRepository; //testa

@RestController
public class GetController {

	@GetMapping("/greeting")
	public String greeting() {
		return "greeting";
	}

	@GetMapping("/allUsers")
	public String allUsers(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		return getAllUsers();
	}

	@GetMapping("/getUser")
	public String getUser(@RequestParam(name = "id", required = true, defaultValue = "0") String name) {
		return getUserSQL(name);
	}

	@GetMapping("/login")
	public String login(@RequestParam(name = "user", required = true) String user,
			@RequestParam(name = "pass", required = true) String passwd) {
		return checkCredentials(user, passwd);
	}

	public String checkCredentials(String id, String pass){
		String result = getFromDB(String.format("SELECT Name FROM Users WHERE Name='%s' AND Password='%s'", id, pass));
		return result;
		// + "\n" + String.format("Select Name from Users where Name='%s' and Password='%s'", id, pass);
	}

	public String getUserSQL(String id) {
		return getFromDB(String.format("SELECT * FROM Users WHERE Name='%s'", id));
	}

	public String getAllUsers() {
			return getFromDB("SELECT * FROM Users");
	}

	public String getFromDB(String query) {
		StringBuilder result = new StringBuilder();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://mysql.dsv.su.se:3306/vady6245?useUnicode=true&serverTimezone=UTC", "vady6245",
					"lie1NaWaeWai");
			Statement stmt = con.createStatement();
			// String sql = "Select * from Users";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				result.append(rs.getString("Name") + ", " + rs.getString("Password") + "\n");
			}
			con.close();
		} catch (Exception e) {
			result.append(e);
		}
		return result.toString();
	}
}
