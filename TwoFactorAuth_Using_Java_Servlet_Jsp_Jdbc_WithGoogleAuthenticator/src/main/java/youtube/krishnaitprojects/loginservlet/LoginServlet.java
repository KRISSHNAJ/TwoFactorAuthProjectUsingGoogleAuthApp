package youtube.krishnaitprojects.loginservlet;

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

import youtube.krishnaitprojects.dbconnections.DBConnection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		try {
			Connection connection = DBConnection.getConnection();
			String query = "SELECT * FROM users_info WHERE username = ? AND password = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				response.sendRedirect("verify.jsp");
			} else {
				response.getWriter().write("Invalid username or password");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("Error: " + e.getMessage());
		}
	}

}
