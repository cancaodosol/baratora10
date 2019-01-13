package baratora10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SampleQueryDAO {
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://localhost/sample";
	private static final String USER = "postgres";
	private static final String PASS = "pass@490";

	public List<List<String>> getListMetaDate(String sql) throws SQLException,ClassNotFoundException{
		Class.forName(DRIVER);
		Connection conn = DriverManager.getConnection(URL, USER, PASS);

		List<String> fields = new ArrayList<String>();
		List<List<String>> Results = new ArrayList<List<String>>();

		PreparedStatement state = conn.prepareStatement(sql);

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

	public List<List<String>> getListMetaDate(String sql,int itemid) throws SQLException,ClassNotFoundException{
		Class.forName(DRIVER);
		Connection conn = DriverManager.getConnection(URL, USER, PASS);

		List<String> fields = new ArrayList<String>();
		List<List<String>> Results = new ArrayList<List<String>>();

		PreparedStatement state = conn.prepareStatement(sql);
		state.setInt(1, itemid);

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
