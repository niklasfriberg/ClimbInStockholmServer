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
public class DeleteController {

	@PutMapping("/deleteUser")
	public void deleteUser(@RequestParam(name = "name", required = true) String name){
		updateDB(String.format("DELETE FROM Users WHERE Name = '%s'", name));
		updateDB(String.format("DELETE FROM FacebookUsers WHERE Name = '%s'", name));
	}

	public void deleteUserMock(@RequestParam(name = "name", required = true) String name){
		updateDB(String.format("DELETE FROM Users_Mock WHERE Name = '%s'", name));
		updateDB(String.format("DELETE FROM FacebookUsers_Mock WHERE Name = '%s'", name));
	}

	@PutMapping("/deleteCrag")
	public void deleteCrag(@RequestParam(name = "crag", required = true) String crag){
		updateDB(String.format("DELETE FROM Crag WHERE CragName = '%s'", crag));
		updateDB(String.format("DELETE FROM Crag_API WHERE CragName = '%s'", crag));
	}

	public void deleteCragMock(@RequestParam(name = "crag", required = true) String crag){
		updateDB(String.format("DELETE FROM Crag_Mock WHERE CragName = '%s'", crag));
	}

	@PutMapping("/deleteRoute")
	public void deleteRoute(@RequestParam(name = "route", required = true) String route){
		updateDB(String.format("DELETE FROM Route WHERE RouteName = '%s'", route));
		updateDB(String.format("DELETE FROM Route_API WHERE RouteName = '%s'", route));
	}

	public void deleteRouteMock(@RequestParam(name = "route", required = true) String route){
		updateDB(String.format("DELETE FROM Route_Mock WHERE RouteName = '%s'", route));
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
