package youtube.krishnaitprojects.registrationservlet;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

import youtube.krishnaitprojects.customutils.EmailUtil;
import youtube.krishnaitprojects.customutils.QRCodeGenerator;
import youtube.krishnaitprojects.dbconnections.DBConnection;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final SecureRandom RANDOM = new SecureRandom();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		GoogleAuthenticator gAuth = new GoogleAuthenticator();
		GoogleAuthenticatorKey key = gAuth.createCredentials();

		String secret = key.getKey();
		String qrBarcodeURL = "otpauth://totp/DataManagementSystem:" + username + "?secret=" + secret
				+ "&issuer=DataManagementSystem";

		try {
			String qrFilePath = getServletContext().getRealPath("/") + "QRCode.png";
			QRCodeGenerator.generateQRCode(qrBarcodeURL, qrFilePath);

			Connection connection = DBConnection.getConnection();
			String query = "INSERT INTO users_info (username, password, secret) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, secret);
			preparedStatement.executeUpdate();

			EmailUtil.sendEmail(email, "Your QR Code for 2FA", "Scan this QR code with Google Authenticator app.",
					qrFilePath);

			response.sendRedirect("login.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("Error: " + e.getMessage());
		}
	}

}
