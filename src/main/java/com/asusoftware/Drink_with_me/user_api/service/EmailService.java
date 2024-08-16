package com.asusoftware.Drink_with_me.user_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmationEmail(String to, String token) {
        String subject = "Confirm your account";
        String confirmationUrl = "http://localhost:8080/api/v1/auth/confirm-account?token=" + token;
        String message = "Thank you for registering. Please click the link below to activate your account:\n" + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom("your-email@gmail.com");  // Setează adresa ta de email

        mailSender.send(email);
    }
}
