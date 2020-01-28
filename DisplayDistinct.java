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
 * Servlet implementation class DisplayDistinct
 */

public class DisplayDistinct extends HttpServlet {
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
			
			// Request all values from the primary table, store in rs.
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT Last_Name FROM primaryTableName");
			
			// Print headers for identifying query results. 
			String str = "<html><body>Last_Name</body></html>"; 
			out.println(str);
			
			// Iterate through the result set rs.
			while (rs.next()) {
				// Collect row information.
				String lName = rs.getString("Last_Name");
				
				// Output row information.
				out.println("<html><body><p>"+lName+"<br /><p></body></html>");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
