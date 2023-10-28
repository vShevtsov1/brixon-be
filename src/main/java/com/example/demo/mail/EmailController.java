package com.example.demo.mail;

import com.example.demo.security.TokenServices;
import com.example.demo.users.data.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@Service
public class EmailController {

    private Properties prop;
    private final String username = "abireset@gmail.com";
    private final String password = "jdmzaewqxnlqflat";

    @Autowired
    private TokenServices tokenServices;
    @PostConstruct
    public void postInitialization() {
        prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
    }


    public void sendEmail(users users) {
        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(users.getEmail()));
            message.setSubject("Reset Your Property Partners Password");

            // Read HTML content from the file
            String htmlContent = loadHTMLContent(users.getNickName(), generateResetLink(users));
            message.setContent(htmlContent, "text/html");

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private String loadHTMLContent(String username, String resetLink) {
        String htmlContent = ""; // Read HTML content from your file or resource
        ClassPathResource resource = new ClassPathResource("reset.html");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            htmlContent = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Replace placeholders with actual values
        htmlContent = htmlContent.replace("{{username}}", username);
        htmlContent = htmlContent.replace("{{resetLink}}", resetLink);

        return htmlContent;
    }

    private String generateResetLink(users users) {
        // Generate the reset link based on your logic
        // For example, if you have a reset endpoint in your application:
        return "http://localhost:5173/reset-password?token=" + tokenServices.generateTokenUserReset(users);
    }
}

