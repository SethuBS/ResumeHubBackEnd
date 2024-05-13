package org.resumehub.backend.service.impl;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.resumehub.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String subject, String recipientName, String body) {
        var message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            String personalizedBody = String.format("Dear %s,%n%n%s", recipientName, body);
            helper.setText(personalizedBody, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            logger.error("Error Occurred while Sending Email:{}", e.getMessage());
        }

        logger.info("Email has been sent this email address: {}  Date: {} recipient name: {}", to, new Date(), recipientName);
    }
}
