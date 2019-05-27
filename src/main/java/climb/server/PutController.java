package climb.server;

import java.sql.*;

import org.apache.tomcat.util.json.JSONParser;
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

	@GetMapping("/getMessage")
	public String putMessage(@RequestParam(name = "user", required = false) String user,
			@RequestParam(name = "message", required = false) String message) {
		try {
			updateDB(String.format("INSERT INTO Messages (Username, Message) VALUES ('%s', '%s')", user, message));
		} catch (Exception e) {
			return e.toString();
		}
		return "nothing";
	}

	@GetMapping("/putFacebookUser")
	public String putFacebookUser(@RequestParam(name = "id", required = true) String id,
			@RequestParam(name = "user", required = true) String user) {
		try {
			updateDB(String.format("INSERT INTO FacebookUsers VALUES ('%s', '%s')", id, user));
		} catch (Exception e) {
			return e.toString();
		}
		return "nothing";
	}

	@PutMapping("/putMessage")
	public String putTheMessage(@RequestParam(name = "user", required = false) String user,
			@RequestParam(name = "message", required = false) String message) {
		try {
			updateDB(String.format("INSERT INTO Messages (Username, Message) VALUES ('%s', '%s')", user, message));
		} catch (Exception e) {
			return e.toString();
		}
		return "nothing";
	}

	@GetMapping("/updateCragsFromAPI")
	public String updateCragsFromAPI() {
		try {
			JSONArray apiResult = getCragsFromAPI();
			for(int i = 0; i < apiResult.length(); i++){
				updateDB(String.format("INSERT INTO Crag_API VALUES ('%f', '%f', '%s', '%s')", 
				apiResult.getJSONObject(i).getDouble("Longitud"), 
				apiResult.getJSONObject(i).getDouble("Latitud"),
				apiResult.getJSONObject(i).getString("CragName").substring(6).trim(),
				"Innehåller 3 routes"
				));
				for (int j = 0; j < apiResult.getJSONObject(i).getJSONArray("Route").length(); j++){
					updateDB(String.format("INSERT INTO Route_API VALUES ('%s', '%s', '%s', '%s', '%d', '%s')", 
					apiResult.getJSONObject(i).getJSONArray("Route").getJSONObject(j).getString("RouteName"),
					apiResult.getJSONObject(i).getString("CragName").substring(6).trim(),
					apiResult.getJSONObject(i).getJSONArray("Route").getJSONObject(j).getString("Höjd"),
					apiResult.getJSONObject(i).getJSONArray("Route").getJSONObject(j).getString("Svårighet"),
					0,
					apiResult.getJSONObject(i).getJSONArray("Route").getJSONObject(j).getString("Beskrivning")
					));
				}
			}
		} catch (Exception e) {
			return e.toString();
		}
		return "success!";
	}

	public JSONArray getCragsFromAPI() throws Exception {
		XMLParser xml = new XMLParser("https://raw.githubusercontent.com/niklasfriberg/ClimbInStockholmServer/master/src/main/resources/Stockholm.gpx");
		JSONObject crag = null;
		JSONObject route;
		JSONArray crags = new JSONArray();
		for (int i = 0; i < xml.getLength();) {
			if (xml.isCrag(i)) {
				crag = new JSONObject();
				crag.put("CragName", xml.get(i));
				while (!xml.isRoute(i)) {
					i++;
				}
				boolean hasRoutes = false;
				for (int j = 0; j < 3; j++) {

					if (xml.isRoute(i)) {
						if (xml.hasCoords(i)) {
							route = new JSONObject();
							hasRoutes = true;
							route.accumulate("Svårighet", "6b+");
							route.accumulate("RouteName", xml.getName(i));
							route.accumulate("Beskrivning", xml.getDesc(i));
							route.accumulate("Höjd", "7");
							crag.accumulate("Route" , route);
							crag.put("Longitud", xml.getLng(i));
							crag.put("Latitud", xml.getLat(i));
							System.out.println(xml.get(i));
						}
					}
					i++;
				}
				if(hasRoutes){
					crags.put(crag);
				}
			}
			i++;
		}
		return crags;
	}



	public boolean updateCrag(String query){

		return false;
	}

	public boolean updateRoute(String query){

		return false;
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
