package climb.server;

import java.sql.*;
import org.json.*;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutController {

	@PutMapping("/updateUser")
	public void putUser(@RequestParam(name = "name", required = true) String name,
			@RequestParam(name = "passwd", required = true) String password) {
		updateDB(String.format("INSERT INTO Users VALUES ('%s', '%s')", name, password));
	}

	@PutMapping("/putMessage")
	public String putMessage(@RequestParam(name = "user", required = true) String user,
	@RequestParam(name = "message", required = true) String message) {
		try {updateDB(String.format("INSERT INTO Messages (Username, Message) VALUES ('%s', '%s')", user, message));}
		catch (Exception e) {
			return e.toString();
		}
		return "nothing";
	}

	public boolean updateDB(String query) {
		int success = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://mysql.dsv.su.se:3306/vady6245?useUnicode=true&serverTimezone=UTC", "vady6245",
					"lie1NaWaeWai");
			Statement stmt = con.createStatement();
			// String sql = "Select * from Users";
			success = stmt.executeUpdate(query);
			con.close();
		} catch (Exception e) {
			return false;
		}
		if (success != 0)
			return true;
		return false;
		// return result.toString();
	}

}
