package cmei.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleTest {
	 public static void main(String[] args) throws ClassNotFoundException, SQLException {
		  String url = "jdbc:oracle:thin:@localhost:1521:alerttest";
		  Class.forName("oracle.jdbc.driver.OracleDriver");
		  String userName = "alert";
		  String pass = "alert37";

		  Connection conn = DriverManager.getConnection(url, userName, pass);
		  Statement stm = conn.createStatement();

		  ResultSet rs = stm.executeQuery("Select * from catalog");

		  while (rs.next()) {
		   System.out.println(rs.getString("id"));
		   System.out.println(rs.getString("name"));
		  }
		  conn.close();
		 }
}
