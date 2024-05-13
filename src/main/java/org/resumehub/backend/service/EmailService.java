package org.resumehub.backend.service;

public interface EmailService {
    void sendEmail(String to, String subject, String recipientName, String body);
}
