package sprint1;

import java.sql.*;

public class pruebaBaseDatos {

	public static String URL = "C:\\Users\\Dani\\Desktop\\Clase\\TERCERO\\IPS\\PROYECTO\\WORKSPACEspr1\\sprint1\\resources\\bdProject.db";
	
	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + URL);
		
		Statement st = con.createStatement();
		String query = "SELECT * FROM juanelos";
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()) {
			System.out.println(rs.getInt(1));
		}
		
		rs.close();
		st.close();
		con.close();
	}
	
}
