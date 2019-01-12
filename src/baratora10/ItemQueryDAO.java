package baratora10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemQueryDAO {
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://localhost/sample";
	private static final String USER = "postgres";
	private static final String PASS = "pass@490";



	public String getItemName(int itemid) throws SQLException, ClassNotFoundException {

		Class.forName(DRIVER);
		Connection conn = DriverManager.getConnection(URL, USER, PASS);
		String sql = "SELECT * FROM m_item WHERE id = ?";

		PreparedStatement state = conn.prepareStatement(sql);
		state.setInt(1,itemid);
		ResultSet result = state.executeQuery();

		result.next();
		String itemName = result.getString("name");

		result.close();
		state.close();
		conn.close();

		return itemName;
	}

}
