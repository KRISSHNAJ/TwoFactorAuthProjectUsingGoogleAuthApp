package youtube.krishnaitprojects.customutils;

import java.io.IOException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {
	public static void sendEmail(String toAddress, String subject, String message, String attachmentPath)
			throws MessagingException, IOException {
		final String fromEmail = "jnkrishnamoorthy@gmail.com";  //your gmail id
		final String password = "KRIssHNAJ@110119981"; //password

		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		});

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(fromEmail));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
		msg.setSubject(subject);
		msg.setText(message);

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(message);

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		MimeBodyPart attachPart = new MimeBodyPart();
		attachPart.attachFile(attachmentPath);
		multipart.addBodyPart(attachPart);

		msg.setContent(multipart);
		Transport.send(msg);
	}

}
