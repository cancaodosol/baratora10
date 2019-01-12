package baratora10;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesResultsQueryDAO {
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://localhost/sample";
	private static final String USER = "postgres";
	private static final String PASS = "pass@490";


	//◇◇指定出荷日のレコードの取得◇◇
	//11個のSalesResaultを内包したリストを受け取り、指定出荷日のレコードを順次にランクが等しいSalesReasultクラスに追記する。
	public List<SalesResult> getSalesResult(List<SalesResult> LSR, Date ship_date) throws SQLException, ClassNotFoundException {

		Class.forName(DRIVER);
		Connection conn = DriverManager.getConnection(URL, USER, PASS);
		String sql = "SELECT * FROM sales_results "
				+ "WHERE itemid = ? AND ship_date = ?"
				+ "ORDER BY rank";

		SalesResult sales = LSR.get(0);

		PreparedStatement state = conn.prepareStatement(sql);
		state.setInt(1, sales.getItemId());
		state.setDate(2, ship_date);
		ResultSet result = state.executeQuery();

		int i=0;

		while(result.next()) {
			while(i<11) {
				sales = LSR.get(i);
				i++;
				sales.setShip_date(result.getDate("ship_date"));
				sales.setArea(result.getInt("area"));

				if(sales.getRank() == result.getInt("rank")) {
					sales.setUnit_case(result.getInt("unit_case"));
					sales.setNum_case(result.getInt("num_case"));
					sales.setSales_amount(result.getInt("sales_amount"));
					break;
				}
			}
		}

		result.close();
		state.close();
		conn.close();

		return LSR;
	}

	//◇◇指定期間内の累計金額・累計ケース数・累計本数の取得◇◇
	//
	public List<SalesResult> getTotalSalesResult(List<SalesResult> LSR, Date start_date) throws SQLException, ClassNotFoundException {

		Class.forName(DRIVER);
		Connection conn = DriverManager.getConnection(URL, USER, PASS);
		String sql = "SELECT itemid, rank, "
				+ "sum(num_case) AS sumCASE ,sum(num_case * sales_amount) AS sumSLR "
				+ "FROM public.sales_results "
				+ "WHERE itemid = ? and rank <> 99 and ?<=ship_date "
				+ "GROUP BY itemid,rank "
				+ "ORDER BY itemid,rank ";

		SalesResult sales = LSR.get(0);

		PreparedStatement state = conn.prepareStatement(sql);
		state.setInt(1, sales.getItemId());
		state.setDate(2, start_date);
		ResultSet result = state.executeQuery();

		int i=0;

		//年度累計ケース数・金額を各クラスに追記する。
		while(result.next()) {
			while(i<11) {
				sales = LSR.get(i);
				i++;
				if(sales.getRank() == result.getInt("rank")) {
					sales.setTotalCase(result.getInt("sumCASE"));
					sales.setTotalSales(result.getInt("sumSLR"));
					break;
				}
			}
		}

		result.close();
		state.close();


		//年度累計本数は控除金額行に追記することにする。
		sql = "SELECT sum(num_case * unit_case) AS sumQTY "
				+ "FROM public.sales_results "
				+ "WHERE itemid = ? and rank <> 99 and ?<=ship_date "
				+ "GROUP BY itemid";
		state = conn.prepareStatement(sql);
		state.setInt(1, sales.getItemId());
		state.setDate(2, start_date);

		result = state.executeQuery();

		if(result.next()) LSR.get(11).setUnit_case(result.getInt("sumQTY"));

		result.close();
		state.close();
		conn.close();

		return LSR;
	}

	//◇◇指定品目の売上結果の値を返す◇◇
	//
	public Date getLastShipDate(int itemid) throws SQLException,ClassNotFoundException{
		Class.forName(DRIVER);
		Connection conn = DriverManager.getConnection(URL, USER, PASS);
		String sql = "SELECT ship_date FROM sales_results WHERE itemid = ? ORDER BY ship_date DESC";

		PreparedStatement state = conn.prepareStatement(sql);
		state.setInt(1, itemid);
		ResultSet result = state.executeQuery();

		Date ship_date = null;
		if(result.next()) ship_date = result.getDate("ship_date");

		result.close();
		state.close();
		conn.close();

		return ship_date;
	}

	//◇◇◇◇
	//
	public List<List<String>> getListMetaDate(int itemid) throws SQLException,ClassNotFoundException{
		Class.forName(DRIVER);
		Connection conn = DriverManager.getConnection(URL, USER, PASS);

		List<String> fields = new ArrayList<String>();
		List<List<String>> Results = new ArrayList<List<String>>();

		String sql = "SELECT I.name AS 品目名, ship_date AS 出荷日, S.name AS 等階級, unit_case AS ケース本数, num_case AS 箱数, to_char(sales_amount, '99,999,990') AS 箱単価, area AS 坪数 " +
				"	FROM public.sales_results " +
				"   	LEFT JOIN m_item AS I ON itemid = I.id " +
				"    LEFT JOIN t_score AS S ON S.id = rank " +
				"    WHERE itemid = ? " +
				"    ORDER BY ship_date,rank;";

		PreparedStatement state = conn.prepareStatement(sql);
		state.setInt(1,itemid);

		ResultSet result = state.executeQuery();
		ResultSetMetaData rsmd = result.getMetaData();

		for(int i=1;i<=rsmd.getColumnCount();i++) {
			fields.add(rsmd.getColumnName(i));
		}

		Results.add(fields);

		while(result.next()) {
			List<String> records = new ArrayList<String>();
			for(String field : fields) {
				records.add(result.getString(field));
			}
			Results.add(records);
		}

		for(List<String> Result : Results) {
			List<String> records = Result;
			for(String record: records) {
				System.out.print(record + ",");
			}
			System.out.println("/");
		}

		return Results;
	}

}
