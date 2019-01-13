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

import baratora10.ReadFile;
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
		ReadFile rf = new ReadFile();

        try {
			switch (mode) {
			case "listall":
				if(itemid.equals("0")) {
					title = "売上情報一覧";
					sql = rf.readSql("mode_ListAll.sql");
		        	SqlResults = dao.getListMetaDate(sql);
				}else {
					title = "品目別売上情報一覧";
					sql = rf.readSql("mode_ListAll_itemid.sql");
		        	SqlResults = dao.getListMetaDate(sql,Integer.parseInt(itemid));
				}
				break;

			case "listmonth":
				//年月でグループ化し集計をとる。
				if(itemid.equals("0")) {
					title = "月次集計一覧";
					sql = rf.readSql("mode_ListMonth.sql");
		        	SqlResults = dao.getListMetaDate(sql);
				}else {
					title = "品目別月次集計一覧";
					sql = rf.readSql("mode_ListMonth_itemid.sql");
		        	SqlResults = dao.getListMetaDate(sql,Integer.parseInt(itemid));
				}
				break;

			case "listweek":
				//年号と週番号でグループ化して集計をとる。
				if(itemid.equals("0")) {
					title = "週次集計一覧";
					sql = rf.readSql("mode_ListWeek.sql");
		        	SqlResults = dao.getListMetaDate(sql);
				}else {
					title = "品目別週次集計一覧";
					sql = rf.readSql("mode_ListWeek_itemid.sql");
		        	SqlResults = dao.getListMetaDate(sql,Integer.parseInt(itemid));
				}

				break;

			case "rankweek":
				title = "品目別等階級週次集計一覧";
				sql = rf.readSql("mode_RankWeek_itemid.sql");
	        	SqlResults = dao.getListMetaDate(sql,Integer.parseInt(itemid));
				break;
			default:
				break;
			}

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
