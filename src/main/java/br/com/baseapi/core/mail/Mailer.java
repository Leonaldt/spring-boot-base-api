package br.com.baseapi.core.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;

@Component
public class Mailer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Mailer.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine thymeleaf;


    public void send(String sender, String receiver, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(sender);
            helper.setTo(receiver);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            LOGGER.error(e.getMessage());
        }

    }

    @Async
    public void enviarEmail(String remente, String destinatario, String assunto,
                            String template, Map<String, Object> variaveis) {
        Context context = new Context(new Locale("pt", "BR"));
        variaveis.entrySet().forEach(e -> context.setVariable(e.getKey(), e.getValue()));
        String menssagem = thymeleaf.process(template, context);
        this.enviarEmail(remente, destinatario, assunto, menssagem);

    }

    public void enviarEmail(String remente, String destinatario, String assunto, String mensagem) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(remente);
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(mensagem, true);
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
