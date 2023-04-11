package jmaster.io.btvnproject3.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Async ///chay o luong khac
    public void sendBirthday(String to, String name) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariable("name", name);
            String body = templateEngine.process("email/birthday.html", context);

            /// send email
            helper.setTo(to);// email gui toi
            helper.setText(body, true);// nd mail
            helper.setSubject("HPBD");
            helper.setFrom("synam141@gmail.com");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("Email sent with error: ", e);
        }
    }

    public void sendTest() {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariable("name", "noi dung truyen vao");
            String body = templateEngine.process("email/hi.html", context);

            /// send email
            helper.setTo("synam141@gmail.com");// email gui toi
            helper.setText(body, true);// nd mail
            helper.setSubject("Test email from springboot");
            helper.setFrom("synam141@gmail.com");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("Email sent with error: ", e);
        }
    }
}