package org.resumehub.backend.EmailConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class EmailConfiguration {

    private static final Logger logger = LogManager.getLogger(EmailConfiguration.class);

    private final Properties props = new Properties();

    public EmailConfiguration() {
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("email.properties"));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public String geWelcomeEmailSubject() {
        return props.getProperty("email.welcome.subject");
    }

    public String getWelcomeEmailBody() {
        return props.getProperty("email.welcome.body");
    }

    public String getResetPasswordSubject() {
        return props.getProperty("email.resetPassword.subject");
    }

    public String getResetPasswordBody() {
        return props.getProperty("email.resetPassword.body");
    }
}
