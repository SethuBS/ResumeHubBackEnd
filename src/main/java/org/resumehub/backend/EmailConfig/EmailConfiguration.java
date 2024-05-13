package org.resumehub.backend.EmailConfig;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@AllArgsConstructor
@Component
public class EmailConfiguration {

    private static final Logger logger = LogManager.getLogger(EmailConfiguration.class);

    private Properties props = new Properties();

    public EmailConfiguration() {
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("email.properties"));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public String getEmailSubject() {
        return props.getProperty("email.subject");
    }

    public String getEmailBody() {
        return props.getProperty("email.body");
    }
}
