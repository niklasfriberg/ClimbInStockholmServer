package climb.server;

import java.io.File;
import java.sql.*;
import org.json.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.XMLFormatter;

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

	// Behöver lagas, fixa query
	@GetMapping("/getCrag")
	public String getCrag(@RequestParam(name = "crag", required = true) String crag) {
		return getCragFromDB(String.format("SELECT * FROM Crag WHERE CragName = '%s'", crag), String
				.format("SELECT RouteName, Höjd, Svårighet, Rep, Beskrivning FROM Route WHERE CragName = '%s'", crag));
	}

	@GetMapping("/getMessages")
	public String getAllMessages() {
		return getFromDB("SELECT * FROM Messages");
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

	@GetMapping("/facebookLogin")
	public String facebookLogin(@RequestParam(name = "user", required = true) String id) {
		return getFromDB(String.format("SELECT Username FROM FacebookUsers WHERE ID='%s'", id));
	}

	@GetMapping("/getCragsFromAPI")
	public String getCragsFromAPI() throws JSONException {
		XMLParser xml = new XMLParser("."+File.separator+"resources"+File.separator+"Stockholm.gpx");
		JSONObject crag = null;
		JSONObject route;
		StringBuilder sb = new StringBuilder();
		File test = new File("."+File.pathSeparator+"resources"+File.pathSeparator+"Stockholm.gpx");
		sb.append(test.exists());
		// for (int i = 0; i < xml.getLength();) {
		// 	if (xml.isCrag(i)) {
		// 		crag = new JSONObject();
		// 		crag.put("CragName", xml.get(i));
		// 		while (!xml.isRoute(i)) {
		// 			i++;
		// 		}
		// 		boolean hasRoutes = false;
		// 		for (int j = 0; j < 3; j++) {

		// 			if (xml.isRoute(i)) {
		// 				if (xml.hasCoords(i)) {
		// 					route = new JSONObject();
		// 					hasRoutes = true;
		// 					route.put("Svårighet", "6b+");
		// 					route.put("RouteName", xml.getName(i));
		// 					route.put("Beskrivning", xml.getDesc(i));
		// 					route.put("Höjd", "7");
		// 					crag.put("Route" , route);
		// 					crag.put("Longitud", xml.getLng(i));
		// 					crag.put("Latitud", xml.getLat(i));
		// 					System.out.println(xml.get(i));
		// 				}
		// 			}
		// 			i++;
		// 		}
		// 		if(hasRoutes){
		// 			sb.append(crag);
		// 		}
		// 	}
		// 	i++;
		// }
		return sb.toString();
	}

	public String getCragFromDB(String queryCrag, String queryRoute) {
		JSONObject jsonCrag = new JSONObject();
		JSONObject jsonRoute = new JSONObject();
		JSONArray jsonRouteArray = new JSONArray();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://mysql.dsv.su.se:3306/vady6245?useUnicode=true&serverTimezone=UTC", "vady6245",
					"lie1NaWaeWai");
			Statement stmtCrag = con.createStatement();
			// String sql = "Select * from Users";
			ResultSet rsCrag = stmtCrag.executeQuery(queryCrag);
			ResultSetMetaData rsmdCrag = rsCrag.getMetaData();
			int col = rsmdCrag.getColumnCount();
			while (rsCrag.next()) {
				for (int i = 1; i < col + 1; i++) {
					jsonCrag.accumulate(rsmdCrag.getColumnName(i), rsCrag.getString(i));
				}
			}
			Statement stmtRoute = con.createStatement();
			ResultSet rsRoute = stmtRoute.executeQuery(queryRoute);
			ResultSetMetaData rsmdRoute = rsRoute.getMetaData();
			col = rsmdRoute.getColumnCount();
			while (rsRoute.next()) {
				for (int i = 1; i < col + 1; i++) {
					jsonRoute.put(rsmdRoute.getColumnName(i), rsRoute.getString(i));
				}

				jsonCrag.accumulate("Route", jsonRoute);
				jsonRoute = new JSONObject();
			}
			// IF single object in ROUTE get object and put to array
			if (!jsonCrag.get("Route").getClass().equals(JSONArray.class)) {
				JSONObject temp = jsonCrag.getJSONObject("Route");
				jsonRouteArray.put(temp);
				jsonCrag.put("Route", jsonRouteArray);
			}
			con.close();
		} catch (Exception e) {
		}
		return jsonCrag.toString();
		// return result.toString();
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
