package youtube.krishnaitprojects.verifyservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.warrenstrange.googleauth.GoogleAuthenticator;

import youtube.krishnaitprojects.dbconnections.DBConnection;

@WebServlet("/verify")
public class VerifyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code");
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (username != null) {
			try {
				Connection connection = DBConnection.getConnection();
				String query = "SELECT secret FROM users_info WHERE username = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, username);
				ResultSet resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					String secret = resultSet.getString("secret");
					GoogleAuthenticator gAuth = new GoogleAuthenticator();

					if (gAuth.authorize(secret, Integer.parseInt(code))) {
						response.sendRedirect("dashboard.jsp");
					} else {
						response.getWriter().write("Invalid code. Please try again.");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().write("Error: " + e.getMessage());
			}
		} else {
			response.sendRedirect("login.jsp");
		}
	}

}
