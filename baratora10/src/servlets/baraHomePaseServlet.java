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
 * Servlet implementation class baraHomePaseServlet
 */
@WebServlet("/baraHomePaseServlet")
public class baraHomePaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public baraHomePaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		SampleQueryDAO dao = new SampleQueryDAO();
		List<List<String>> SqlResults = new ArrayList<List<String>>();
		String sql = "SELECT * FROM m_item ORDER BY id";

        try {
        	SqlResults = dao.getListMetaDate(sql);
        }catch(ClassNotFoundException e) {
        	e.printStackTrace();
        }catch(SQLException e){
        	e.printStackTrace();
        }

		request.setAttribute("itemList", SqlResults);

		RequestDispatcher rdis = request.getRequestDispatcher("baraHomePaseView.jsp");
		rdis.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
