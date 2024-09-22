package com.mascotas.app.email.services;

import com.mascotas.app.email.dto.EmailValuesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Value("${mail.url.front}")
    String urlFront;

    public void sendEmailTemplate(EmailValuesDTO emailValuesDTO){
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();
            Map<String, Object> model = new HashMap<>();
            model.put("userName", emailValuesDTO.getUserName());
            model.put("url", urlFront + emailValuesDTO.getToken());
            model.put("token", emailValuesDTO.getToken());
            context.setVariables(model);

            String htmlText = templateEngine.process("email-template", context);

            helper.setFrom(emailValuesDTO.getMailFrom());
            helper.setTo(emailValuesDTO.getMailTo());
            helper.setSubject(emailValuesDTO.getSubject());
            helper.setText(htmlText, true);

            javaMailSender.send(message);

        }catch (MessagingException e){
            e.printStackTrace();
        }
    }
}