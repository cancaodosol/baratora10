package servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import baratora10.ItemQueryDAO;
import baratora10.RankQueryDAO;
import baratora10.SalesResult;
import baratora10.SalesResultsQueryDAO;
import baratora10.SalesResultsUpdateDAO;
import baratora10.Transform;

/**
 * Servlet implementation class InsertSalesResultServlet
 */
@WebServlet("/InsertSalesResultServlet")
public class InsertSalesResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertSalesResultServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		int itemid = Integer.parseInt((String)request.getParameter("itemid"));

		String itemName = null;
		List<SalesResult> LastSalesResults = new ArrayList<SalesResult>();
		java.util.Map<Integer, String> rankMap = new HashMap<Integer,String>();

		//品目名、等級名の取得
		try {
			itemName = (new ItemQueryDAO()).getItemName(itemid);
			rankMap = (new RankQueryDAO()).getRankMap();
		}catch(SQLException e) {
		}catch(ClassNotFoundException e) {
		}

		for(int i=0;i<=11;i++) {
			SalesResult sales = new SalesResult();
			sales.setItemId(itemid);
			sales.setItemName(itemName);
			if(i!=11) {
				sales.setRank(i+1);
				sales.setRankname(rankMap.get(i+1));
			}else {
				sales.setRank(99);
				sales.setRankname(rankMap.get(99));
			}
			LastSalesResults.add(sales);
		}

		Date ship_date = null;
		//最後の売上入力のレコードから情報の取得
		try {
			ship_date = (new SalesResultsQueryDAO()).getLastShipDate(itemid);

			if(ship_date != null) {
				LastSalesResults = (new SalesResultsQueryDAO()).getSalesResult(LastSalesResults, ship_date);
			}
		}catch(SQLException e) {
		}catch(ClassNotFoundException e) {
		}
		System.out.println(ship_date);

		//年度累計結果の取得
		if(ship_date != null) {
			try {
				Date start_date = Date.valueOf("2018-08-01");
				LastSalesResults = (new SalesResultsQueryDAO()).getTotalSalesResult(LastSalesResults, start_date);
			}catch(SQLException e) {
				e.printStackTrace();
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}

			System.out.println(ship_date);
			Transform trn = new Transform();
			Calendar cal = trn.getCal(ship_date);
			System.out.println("ori"+cal.getTimeInMillis());
			cal.add(Calendar.DATE, 2);
			System.out.println("2days" +cal.getTimeInMillis());
			ship_date = trn.getDate(cal);
		}

		System.out.println("last:" + ship_date);
		if(ship_date == null) ship_date = Date.valueOf("2018-08-01");
		System.out.println("last:" + ship_date);

		request.setAttribute("next_ship_date", ship_date);
		request.setAttribute("LSR", LastSalesResults);

		RequestDispatcher rdis = request.getRequestDispatcher("InsertSalesResults.jsp");
		//RequestDispatcher rdis = request.getRequestDispatcher("ListSalesResults.jsp");
		rdis.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		SalesResultsUpdateDAO dao = new SalesResultsUpdateDAO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		int itemId = Integer.parseInt(request.getParameter("itemid"));
		int area = Integer.parseInt(request.getParameter("area"));
		String next = "./baraHomePaseServlet";

		try {
			for(int i=1;i<=11;i++) {
				SalesResult sales = new SalesResult();
				sales.setItemId(itemId);
				sales.setArea(area);
				String date = request.getParameter("date");
				Date sqlDate = Date.valueOf(date);
				sales.setShip_date(sqlDate);
				sales.setRank(Integer.parseInt(request.getParameter("rank"+ String.valueOf(i))));
				sales.setUnit_case(Integer.parseInt(request.getParameter("uca"+ String.valueOf(i))));
				sales.setNum_case(Integer.parseInt(request.getParameter("qty"+ String.valueOf(i))));
				int uslr = Integer.parseInt(request.getParameter("uslr"+ String.valueOf(i)));
				sales.setSales_amount(uslr);

				if(uslr != 0) {
					 dao.insertSalesResult(sales);
				}
			}
		}catch(NullPointerException e) {

		}catch(SQLException e) {
			next = "./baraHomePaseServlet";
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}

		response.sendRedirect(next);
	}

}
