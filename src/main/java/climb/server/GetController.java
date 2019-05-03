package climb.server;

import java.sql.*;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import climb.server.User; //testa
import climb.server.UserRepository; //testa

@RestController
public class GetController {

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

//	@GetMapping("/allUsers")
//	public String allUsers(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
//			Model model) {
//		model.addAttribute("name", name);
//		return getAllUsers();
//	}
//
//	public String getAllUsers() {
//		StringBuilder user = null;
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection con = DriverManager.getConnection("jdbc:mysql://mysql.dsv.su.se:3306/vady6245", "vady6245",
//					"lie1NaWaeWai");
//			Statement stmt = con.createStatement();
//			String sql = "Select * from Users";
//			ResultSet rs = stmt.executeQuery(sql);
//			while (rs.next()) {
//				user.append(rs.getString("Name") + ", " + rs.getString("Password") + "\n");
//			}
//		} catch (Exception e) {
//			System.out.print(e);
//		}
//		return user.toString();
//	}

	@Autowired // This means to get the bean called userRepository
// Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;

	@GetMapping(path = "/add") // Map ONLY GET Requests
	public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {
// @ResponseBody means the returned String is the response, not a view name
// @RequestParam means it is a parameter from the GET or POST request

		User n = new User();
		n.setName(name);
		n.setEmail(password);
		userRepository.save(n);
		return "Saved";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<User> getAllUsers() {
// This returns a JSON or XML with the users
		return userRepository.findAll();
	}

}
