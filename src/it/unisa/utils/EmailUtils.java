package it.unisa.utils;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
public class EmailUtils {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USERNAME = "rivenditauto@gmail.com";
    private static final String SMTP_PASSWORD = "ygazcsgvveftwrlu";
    
    public static void sendResetPasswordEmail(String recipientEmail, String token) throws MessagingException {
        String subject = "Reset della password";
        String resetLink = "http://localhost:8080/SitoBase/jsp/utente/resetPassword.jsp?token=" + token;

        String messageText = "Ciao,\n\nPer reimpostare la tua password clicca sul link seguente:\n"
                + resetLink + "\n\nSe non hai richiesto il reset, ignora questa email.\n\nSaluti,\nIl team";

        sendEmail(recipientEmail, subject, messageText);
    }

    private static void sendEmail(String recipientEmail, String subject, String content) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        };

        Session session = Session.getInstance(props, auth);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SMTP_USERNAME));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        message.setSubject(subject);
        message.setText(content);

        Transport.send(message);
    }
}