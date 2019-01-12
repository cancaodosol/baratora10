package baratora10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Rank {
	public HashMap<Integer,String> rankMap;

	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private static final String URL = "jdbc:postgresql://localhost/sample";
	private static final String USER = "postgres";
	private static final String PASS = "pass@490";

	Rank()throws  SQLException, ClassNotFoundException{
		Class.forName(DRIVER);
		Connection conn = DriverManager.getConnection(URL, USER, PASS);
		String sql = "SELECT * FROM t_scire ORDER BY id";

		PreparedStatement state = conn.prepareStatement(sql);
		ResultSet result = state.executeQuery();

		while(result.next()) {
			rankMap.put(result.getInt("id"),result.getString("name"));
		}

		result.close();
		state.close();
		conn.close();
	}
}
