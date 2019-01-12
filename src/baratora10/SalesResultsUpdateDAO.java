package baratora10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalesResultsUpdateDAO {
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://localhost/sample";
	private static final String USER = "postgres";
	private static final String PASS = "pass@490";

	public void insertSalesResult(SalesResult sales) throws  SQLException, ClassNotFoundException{
		Class.forName(DRIVER);
		Connection conn = DriverManager.getConnection(URL, USER, PASS);

		String sql ="INSERT INTO sales_results "
				+"(itemid, ship_date, rank, unit_case, num_case, sales_amount,area) "
				+"VALUES (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement state = conn.prepareStatement(sql);

		state.setInt(1, sales.getItemId());
		state.setDate(2, sales.getShip_date());
		state.setInt(3, sales.getRank());
		state.setInt(4, sales.getUnit_case());
		state.setInt(5, sales.getNum_case());
		state.setDouble(6, sales.getSales_amount());
		state.setInt(7, sales.getArea());

		state.executeUpdate();

		state.close();
		conn.close();
	}
}
