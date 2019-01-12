package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateGraphLineServlet
 */
@WebServlet(description = "1種類分の売上結果を日付順にグラフにする。", urlPatterns = { "/CreateGraphLineServlet" })
public class CreateGraphLineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateGraphLineServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*		try {
			SalesResultsQueryDAO dao = new SalesResultsQueryDAO();
			List aves = dao.getAverage(1);//今回は1に限定した。

			RequestDispatcher rdis = request.getRequestDispatcher("./graph/GraphLineView.jsp");

			SalesResult ave = (SalesResult)aves.get(0);

			String title = ave.getItemName();
			String color = "light-blue";
			String unit = ave.getUnit();

			String[] labels = new String[aves.size()];
			double[] values = new double[aves.size()];

			for(int i=0;i<aves.size();i++) {
				ave = (SalesResult)aves.get(i);
				labels[i] = new SimpleDateFormat("MM月dd日").format(ave.getShipmentDate());
				values[i] = (double)ave.getAve();
			}

			request.setAttribute("pageTitle", title + "の平均価格の遷移グラフ");
			request.setAttribute("graphTitle",title + "の平均価格の遷移グラフ");
			request.setAttribute("labels", labels);
			request.setAttribute("values", values);
			request.setAttribute("title", title);
			request.setAttribute("color", color);
			request.setAttribute("unit", unit);

			rdis.forward(request, response);

		}catch(Exception e) {
			e.printStackTrace();
		}

		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
