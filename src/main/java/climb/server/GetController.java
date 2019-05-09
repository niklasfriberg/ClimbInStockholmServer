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

	@GetMapping("/getMarkers")
	public String getMarkers() {
		return getFromDB("SELECT * FROM Crag");
	}

	@GetMapping("/getRoutes")
	public String getRoutes(@RequestParam(name = "crag", required = true) String crag) {
		return getFromDB(String.format("SELECT * FROM Route WHERE CragName = '%s'", crag));
	}

	@GetMapping("/allUsers")
	public String getAllUsers() {
		return getFromDB("SELECT * FROM Users");
	}

	@GetMapping("/getUser")
	public String getUser(@RequestParam(name = "id", required = true, defaultValue = "0") String id) {
		return getFromDB(String.format("SELECT * FROM Users WHERE Name='%s'", id));
	}

	@GetMapping("/login")
	public String login(@RequestParam(name = "user", required = true) String user,
			@RequestParam(name = "pass", required = true) String passwd) {
			return getFromDB(String.format("SELECT Name FROM Users WHERE Name='%s' AND Password='%s'", user, passwd));
	}


	public String getFromDB(String query) {
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
					json.accumulate(rsmd.getColumnName(i), rs.getString(i));
				}
			}
			con.close();
		} catch (Exception e) {
		}
		return json.toString();
		// return result.toString();
	}
}
