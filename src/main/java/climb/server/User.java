package climb.server;


import java.sql.*;

public class User {
public static String getAllUsers() {
	StringBuilder person = null;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://mysql.dsv.su.se:3306/vady6245", "vady6245",
				"lie1NaWaeWai");
		Statement stmt = con.createStatement();
		String sql = "Select * from Users";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			person.append(rs.getString("Name") + ", " + rs.getString("Password") + "\n");
		}
		con.close();
	} catch (Exception e) {
		System.out.print(e);
	}
	return person.toString();
}
}


//	import javax.persistence.Entity;
//	import javax.persistence.GeneratedValue;
//	import javax.persistence.GenerationType;
//	import javax.persistence.Id;
//
//	@Entity // This tells Hibernate to make a table out of this class
//	public class User {
//	    @Id
//	    @GeneratedValue(strategy=GenerationType.AUTO)
//	    private Integer id;
//
//	    private String name;
//
//	    private String email;
//
//		public Integer getId() {
//			return id;
//		}
//
//		public void setId(Integer id) {
//			this.id = id;
//		}
//
//		public String getName() {
//			return name;
//		}
//
//		public void setName(String name) {
//			this.name = name;
//		}
//
//		public String getEmail() {
//			return email;
//		}
//
//		public void setEmail(String email) {
//			this.email = email;
//		}
//
//
//	}
//	
//
