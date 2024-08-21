package com.asusoftware.Drink_with_me.user_api.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.confirmation.url}")
    private String confirmationBaseUrl;

    @Value("${app.mail.from}")
    private String fromEmail;

    @Value("${app.login.url}")
    private String loginUrl;

    public void sendConfirmationEmail(String to, String token) {
        try {
            String subject = "Confirm your account";
            String confirmationUrl = confirmationBaseUrl + "?token=" + token;

            // Create an HTML message that includes the confirmation link and the login button
            String message = "<p>Thank you for registering. Please click the button below to activate your account:</p>"
                    + "<a href=\"" + confirmationUrl + "\" style=\"display:inline-block; padding:10px 20px; font-size:16px; color:white; background-color:#4CAF50; text-decoration:none; border-radius:5px;\">Confirm Account</a>"
                    + "<p>If the button above doesn't work, copy and paste the following link into your browser:</p>"
                    + "<p><a href=\"" + confirmationUrl + "\">" + confirmationUrl + "</a></p>"
                    + "<p>After activating your account, click the button below to log in:</p>"
                    + "<a href=\"" + loginUrl + "\" style=\"display:inline-block; padding:10px 20px; font-size:16px; color:white; background-color:#007bff; text-decoration:none; border-radius:5px;\">Go to Login</a>";

            MimeMessage email = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(email, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true); // Enable HTML content
            helper.setFrom(fromEmail);

            mailSender.send(email);
        } catch (MessagingException e) {
            // Handle the exception: log it, wrap it in a custom exception, or rethrow it
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
