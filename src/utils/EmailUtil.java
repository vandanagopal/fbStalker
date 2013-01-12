package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class EmailUtil {
    private static Session session = getSession();

    private static Session getSession() {

        final String username = "fbforv@gmail.com";
        final String password = "hackersdef";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

    }

    public static void sendEmail(String subject, String body) {
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("fbforv@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("gopal.vandana@gmail.com"));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
