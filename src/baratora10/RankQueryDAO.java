package baratora10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class RankQueryDAO {

	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://localhost/sample";
	private static final String USER = "postgres";
	private static final String PASS = "pass@490";

	public HashMap<Integer,String> getRankMap()throws  SQLException, ClassNotFoundException{

		HashMap<Integer,String> rankMap = new HashMap<Integer,String>();

		Class.forName(DRIVER);
		Connection conn = DriverManager.getConnection(URL, USER, PASS);
		String sql = "SELECT * FROM t_score ORDER BY id";

		PreparedStatement state = conn.prepareStatement(sql);
		ResultSet result = state.executeQuery();

		while(result.next()) {
			rankMap.put(result.getInt("id"),result.getString("name"));
		}

		result.close();
		state.close();
		conn.close();

		return rankMap;
	}
}
