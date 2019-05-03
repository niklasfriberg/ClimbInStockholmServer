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

	public String getUserSQL(String id) {
		StringBuilder user = new StringBuilder();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://mysql.dsv.su.se:3306/vady6245?useUnicode&serverTimezone=UTC", "vady6245",
					"lie1NaWaeWai");
			Statement stmt = con.createStatement();
			String sql = "Select * from Users where Name='"+id+"'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				user.append(rs.getString("Name") + ", " + rs.getString("Password") + "\n");
			}
			con.close();
		} catch (Exception e) {
			user.append(e);
		}
		return user.toString();
	}

	public String getAllUsers() {
		StringBuilder user = new StringBuilder();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://mysql.dsv.su.se:3306/vady6245?useUnicode&serverTimezone=UTC", "vady6245",
					"lie1NaWaeWai");
			Statement stmt = con.createStatement();
			String sql = "Select * from Users";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				user.append(rs.getString("Name") + ", " + rs.getString("Password") + "\n");
			}
			con.close();
		} catch (Exception e) {
			user.append(e);
		}
		return user.toString();
	}
}
