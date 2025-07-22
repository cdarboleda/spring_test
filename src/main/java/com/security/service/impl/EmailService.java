package com.security.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String emailUsername;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String plantilla, String to, String subject, Map<String, Object> variables)
            throws MessagingException {
        // Crear el contexto de Thymeleaf con variables dinámicas
        Context context = new Context();
        context.setVariables(variables);
        context.setVariable("bodyTemplate", "email/" + plantilla); // ejemplo: email/procesoCancelado
        // Procesar la plantilla
        String htmlContent = templateEngine.process("email/layout", context);

        // Crear y enviar el correo
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        // helper.setFrom(emailUsername);
        try {
            helper.setFrom(new InternetAddress(emailUsername, "Administrador SIGEPROCP"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mailSender.send(message);
    }
}
