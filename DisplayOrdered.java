package pkg_servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DisplayOrdered
 */
 
public class DisplayOrdered extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
  String dbstring = "jdbc:sqlserver://serverName\\SQLEXPRESS:dynamicPort;database=dbName;user=sa;password=saPW";
	
	Connection conn;
	Statement stmt;

	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(dbstring);
			stmt=conn.createStatement();
			PrintWriter out = res.getWriter();
			
			// Request all ordered values from the primary table, store in rs.
			ResultSet rs = stmt.executeQuery("SELECT * "
											+"FROM primaryTableName "
											+"ORDER BY ID DESC;");
			
			// Print headers for identifying query results. 0-99 for ID, 15 char length for first & last name. 
			String str = "<html><body>ID     First_Name     Last_Name</body></html>"; 
			out.println(str);
			
			// Iterate through the result set rs.
			while (rs.next()) {
				// Collect row information.
				int id = rs.getInt("ID");
				String fName = rs.getString("First_Name");
				String lName = rs.getString("Last_Name");
				
				// Output row information.
				out.println("<html><body><p>"+id+"   "+fName+"   "+lName+"<br /><p></body></html>");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
