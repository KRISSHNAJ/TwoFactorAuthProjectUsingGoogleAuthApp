package youtube.krishnaitprojects.dashboardservlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Check if the user is logged in
		if (request.getSession().getAttribute("username") != null) {
			// If logged in, forward the request to the dashboard JSP page
			request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
		} else {
			// If not logged in, redirect to the login page
			response.sendRedirect("login.jsp");
		}
	}
}
