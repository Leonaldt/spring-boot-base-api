package br.com.baseapi.core.config;

import br.com.baseapi.core.properties.EmailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender(EmailProperties property) {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.connectiontimeout", 10000);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setJavaMailProperties(props);
        mailSender.setHost(property.getHost());
        mailSender.setPort(property.getPort());
        mailSender.setUsername(property.getUsername());
        mailSender.setPassword(property.getPassword());

        return mailSender;
    }
}
