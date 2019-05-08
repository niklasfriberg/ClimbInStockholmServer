package climb.server;

import java.sql.*;
import org.json.*;
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
		return getFromDB(String.format("SELECT Name FROM Users WHERE Name='%s' AND Password='%s'", id, pass));
	}

	public String getUserSQL(String id) {
		return getFromDB(String.format("SELECT * FROM Users WHERE Name='%s'", id));
	}

	public String getAllUsers() {
			return getFromDB("SELECT * FROM Users");
	}

	public String getFromDB(String query) {
		StringBuilder result = new StringBuilder();
		JSONObject json = new JSONObject();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://mysql.dsv.su.se:3306/vady6245?useUnicode=true&serverTimezone=UTC", "vady6245",
					"lie1NaWaeWai");
			Statement stmt = con.createStatement();
			// String sql = "Select * from Users";
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int col = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i < col + 1; i++) {
					json.put(rsmd.getColumnName(i), rs.getString(i));
					result.append(rs.getString(i));
					
					if (i < col)

						result.append(", ");
				}
				result.append("\n");
			}
			con.close();
		} catch (Exception e) {
			result.append(e);
		}
		return json.toString();
		// return result.toString();
	}
}
