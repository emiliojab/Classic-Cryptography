/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author User
 */
public final class SendEmail {

    Properties properties = System.getProperties();
    String from = "..."; // your outlook
    String host = "smtp-mail.outlook.com";
    private static String rand;

    public SendEmail(String to) {
        try {
            session.setDebug(true);
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Verification!");

            // Now set the actual message
            this.rand = generateRandom();
            message.setText(rand);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public Properties Properties() {
        // Setup mail server
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        return properties;
    }

    Session session = Session.getInstance(Properties(), new javax.mail.Authenticator() {

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
                
            return new PasswordAuthentication(from, "PASSWORD"); // your email's password

        }
    });

    public String generateRandom() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public static String getRand() {
        return rand;
    }

}
