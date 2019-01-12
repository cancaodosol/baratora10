package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import baratora10.SampleQueryDAO;

/**
 * Servlet implementation class SalesResultListServlet
 */
@WebServlet("/SalesResultListServlet")
public class SalesResultListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SalesResultListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = (String)request.getParameter("mode");
		String itemid = (String)request.getParameter("itemid");

		SampleQueryDAO dao = new SampleQueryDAO();
		List<List<String>> SqlResults = new ArrayList<List<String>>();
		String sql = null;
		String title = null;

		switch (mode) {
		case "listall":
			if(itemid.equals("0")) {
				title = "売上情報一覧";
				sql = "SELECT I.name AS 品目名, to_char(ship_date,'YYYY/MM/DD(DY)') AS 出荷日," +
						" S.name AS 等階級, unit_case AS ケース本数, num_case AS 箱数," +
						" to_char(sales_amount, '99,999,990.00') AS 箱単価, area AS 坪数 " +
						" FROM public.sales_results " +
						" LEFT JOIN m_item AS I ON itemid = I.id " +
						" LEFT JOIN t_score AS S ON S.id = rank " +
						" ORDER BY ship_date,itemid,rank; ";
			}else {
				title = "品目別売上情報一覧";
				sql = "SELECT I.name AS 品目名,area AS 坪数, to_char(ship_date,'YYYY/MM/DD(DY)') AS 出荷日," +
						" S.name AS 等階級, unit_case AS ケース本数, num_case AS 箱数, to_char(sales_amount, '99,999,990.00') AS 箱単価," +
						" to_char(sales_amount*num_case,'999,999,990.00') AS 金額（税別）," +
						" to_char(sales_amount*num_case * 1.08,'999,999,990.00') AS 金額（税込）," +
						" to_char(sales_amount/(unit_case*num_case),'999,999,990.00') AS 単価（税別）," +
						" to_char(sales_amount/(unit_case*num_case) * 1.08,'999,999,990.00') AS 単価（税込） " +
						"FROM public.sales_results " +
						"LEFT JOIN m_item AS I ON itemid = I.id " +
						"LEFT JOIN t_score AS S ON S.id = rank " +
						"WHERE itemid = " + itemid +" and unit_case <> 0 " +
						"ORDER BY ship_date,rank;";
			}
			break;
		case "listmonth":
			//年月でグループ化し集計をとる。
			if(itemid.equals("0")) {
				title = "月次集計一覧";
				sql = "SELECT to_char(ship_date,'YYYY年MM月') AS 集計月," +
					"sum(unit_case*num_case) AS 本数, sum(num_case) AS 箱数," +
					"to_char(sum(sales_amount*num_case), '99,999,999,990') AS 売上（税別）," +
					"to_char(sum(sales_amount*num_case)*1.08, '99,999,999,990') AS 売上（税込） ," +
					" to_char(sum(sales_amount*num_case)/sum(unit_case*num_case),'999,999,990.00') AS 単価（税別）," +
					" to_char(sum(sales_amount*num_case)/sum(unit_case*num_case) * 1.08,'999,999,990.00') AS 単価（税込） "+
					"FROM public.sales_results " +
					"LEFT JOIN m_item AS I ON itemid = I.id " +
					"LEFT JOIN t_score AS S ON S.id = rank " +
					"WHERE unit_case <> 0 " +
					"GROUP BY to_char(ship_date,'YYYY年MM月') " +
					"ORDER BY to_char(ship_date,'YYYY年MM月');";
			}else {
				title = "品目別月次集計一覧";
				sql = "SELECT I.name AS 品目名, to_char(ship_date,'YYYY年MM月') AS 集計月," +
						"sum(unit_case*num_case) AS 本数, sum(num_case) AS 箱数," +
						"to_char(sum(sales_amount*num_case), '99,999,999,990') AS 売上（税別）," +
						"to_char(sum(sales_amount*num_case)*1.08, '99,999,999,990') AS 売上（税込） ," +
						" to_char(sum(sales_amount*num_case)/sum(unit_case*num_case),'999,999,990.00') AS 単価（税別）," +
						" to_char(sum(sales_amount*num_case)/sum(unit_case*num_case) * 1.08,'999,999,990.00') AS 単価（税込） " +
						"FROM public.sales_results " +
						"LEFT JOIN m_item AS I ON itemid = I.id " +
						"LEFT JOIN t_score AS S ON S.id = rank " +
						"WHERE itemid = "+ itemid +" and unit_case <> 0 " +
						"GROUP BY itemid,I.name,to_char(ship_date,'YYYY年MM月') " +
						"ORDER BY itemid,to_char(ship_date,'YYYY年MM月');";
			}

			break;

		case "listweek":
			//年号と週番号でグループ化して集計をとる。
			if(itemid.equals("0")) {
				title = "週次集計一覧";
				sql = "SELECT TO_CHAR(ship_date,'YYYY年') AS 年, EXTRACT(WEEK FROM ship_date) AS 集計週,sum(unit_case*num_case) AS 本数, sum(num_case) AS 箱数,"+
						"to_char(sum(sales_amount*num_case), '99,999,999,990') AS 売上（税別）,"+
						"to_char(sum(sales_amount*num_case)*1.08, '99,999,999,990') AS 売上（税込） ," +
						" to_char(sum(sales_amount*num_case)/sum(unit_case*num_case),'999,999,990.00') AS 単価（税別）," +
						" to_char(sum(sales_amount*num_case)/sum(unit_case*num_case) * 1.08,'999,999,990.00') AS 単価（税込） " +
						"FROM public.sales_results " +
						"LEFT JOIN m_item AS I ON itemid = I.id " +
						"LEFT JOIN t_score AS S ON S.id = rank " +
						"WHERE  unit_case <> 0 " +
						"GROUP BY TO_CHAR(ship_date,'YYYY年'),EXTRACT(WEEK FROM ship_date) " +
						"ORDER BY TO_CHAR(ship_date,'YYYY年'),EXTRACT(WEEK FROM ship_date);";
			}else {
				title = "品目別週次集計一覧";
				sql = "SELECT I.name AS 品目名,TO_CHAR(ship_date,'YYYY年') AS 年, EXTRACT(WEEK FROM ship_date) AS 集計週,sum(unit_case*num_case) AS 本数, sum(num_case) AS 箱数,"+
						"to_char(sum(sales_amount*num_case), '99,999,999,990') AS 売上（税別）,"+
						"to_char(sum(sales_amount*num_case)*1.08, '99,999,999,990') AS 売上（税込） ," +
						" to_char(sum(sales_amount*num_case)/sum(unit_case*num_case),'999,999,990.00') AS 単価（税別）," +
						" to_char(sum(sales_amount*num_case)/sum(unit_case*num_case) * 1.08,'999,999,990.00') AS 単価（税込） " +
						"FROM public.sales_results " +
						"LEFT JOIN m_item AS I ON itemid = I.id " +
						"LEFT JOIN t_score AS S ON S.id = rank " +
						"WHERE  itemid = "+ itemid +" and unit_case <> 0 " +
						"GROUP BY itemid,I.name,TO_CHAR(ship_date,'YYYY年'),EXTRACT(WEEK FROM ship_date) " +
						"ORDER BY itemid,TO_CHAR(ship_date,'YYYY年'),EXTRACT(WEEK FROM ship_date);";
			}

			break;

		case "rankweek":
			title = "品目別等階級週次集計一覧";
			sql = "SELECT TO_CHAR(ship_date,'YYYY年') AS 年, EXTRACT(WEEK FROM ship_date) AS 集計週,S.name AS 等階級," +
					"sum(unit_case*num_case) AS 本数, sum(num_case) AS 箱数,to_char(sum(sales_amount*num_case), '99,999,999,990') AS 売上（税別）," +
					"to_char(sum(sales_amount*num_case)*1.08, '99,999,999,990') AS 売上（税込） , " +
					"to_char(sum(sales_amount*num_case)/sum(unit_case*num_case),'999,999,990.00') AS 単価（税別）, " +
					"to_char(sum(sales_amount*num_case)/sum(unit_case*num_case) * 1.08,'999,999,990.00') AS 単価（税込） " +
					"FROM public.sales_results " +
					"LEFT JOIN m_item AS I ON itemid = I.id " +
					"LEFT JOIN t_score AS S ON S.id = rank " +
					"WHERE  itemid = " + itemid + " and unit_case <> 0 " +
					"GROUP BY TO_CHAR(ship_date,'YYYY年'),EXTRACT(WEEK FROM ship_date),S.id " +
					"ORDER BY TO_CHAR(ship_date,'YYYY年'),EXTRACT(WEEK FROM ship_date),S.id;";
			break;
		default:
			break;
		}

        try {
        	SqlResults = dao.getListMetaDate(sql);
        }catch(ClassNotFoundException e) {
        	e.printStackTrace();
        }catch(SQLException e){
        	e.printStackTrace();
        }

		request.setAttribute("title", title);
		request.setAttribute("SqlResults", SqlResults);
		request.setAttribute("sql",sql);

		RequestDispatcher rdis = request.getRequestDispatcher("SqlListVeiw.jsp");
		rdis.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String title = "SQL実行";
		String sql = request.getParameter("sql");

		SampleQueryDAO dao = new SampleQueryDAO();
		List<List<String>> SqlResults = new ArrayList<List<String>>();

		RequestDispatcher rdis = request.getRequestDispatcher("SqlListVeiw.jsp");

		try {
			SqlResults = dao.getListMetaDate(sql);
		}catch(ClassNotFoundException e) {
			rdis = request.getRequestDispatcher("SQLErrorPase.html");
		}catch(SQLException e){
			rdis = request.getRequestDispatcher("SQLErrorPase.html");
		}

		request.setAttribute("title", title);
		request.setAttribute("SqlResults", SqlResults);
		request.setAttribute("sql", sql);

		rdis.forward(request, response);
	}

}
