package climb.server;

import java.sql.*;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import climb.server.User; //testa
//import climb.server.UserRepository; //testa

@RestController
public class GetVahab {

	@GetMapping("/allUser")
	public String getVahab(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "vahab kom igen";
	}
}