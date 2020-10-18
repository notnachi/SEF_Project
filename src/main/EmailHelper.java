package main;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailHelper {

    private String to;
    private String from;
    private String mailSubject;
    private String mailMessage;
    private String host;
    private Properties properties;
    private Session session;

    public EmailHelper(String to, String from, String subject, String message)
    {
        // Recipient's email ID needs to be mentioned.
        this.to = to;

        // Sender's email ID needs to be mentioned
        this.from = from;

        this.mailSubject = subject;
        this.mailMessage = message;

        // Assuming you are sending email from localhost
        host = "localhost";

        // Get system properties
        properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        session = Session.getDefaultInstance(properties);
    }

    public void sendEmail() throws MessagingException {
        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(from));

        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        // Set Subject: header field
        message.setSubject(mailSubject);

        // Now set the actual message
        message.setText(mailMessage);

        // Send message
        Transport.send(message);

        System.out.println("Sent message successfully....");
    }

}

