package baratora10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            //-----------------
            // 接続
            //-----------------

        	String url = "jdbc:postgresql://localhost/sample";// "jdbc:postgresql://[場所(Domain)]:[ポート番号]/[DB名]"
        	String user = "postgres";// ログインロール
        	String password = "pass@490";// パスワード

            connection = DriverManager.getConnection(url,user,password);
            statement = connection.createStatement();

            //-----------------
            // SQLの発行
            //-----------------
            //ユーザー情報のテーブル
            resultSet = statement.executeQuery("SELECT itemid as アイテム FROM sales_results");

            //-----------------
            // 値の取得
            //-----------------
            // フィールド一覧を取得
            List<String> fields = new ArrayList<String>();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                fields.add(rsmd.getColumnName(i));
            }

            //結果の出力
            int rowCount = 0;
            while (resultSet.next()) {
                rowCount++;

                System.out.println("---------------------------------------------------");
                System.out.println("--- Rows:" + rowCount);
                System.out.println("---------------------------------------------------");

                //値は、「resultSet.getString(<フィールド名>)」で取得する。
                for (String field : fields) {
                    System.out.println(field + ":" + resultSet.getString(field));
                }
            }


        } finally {
            //接続を切断する
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

}

