package ru.zheleznov.impl.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.SpringTemplateLoader;
import ru.zheleznov.api.services.MailService;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

@Component
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${host}")
    private String host;

    private final Template confirmMailTemplate;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateLoader(
                new SpringTemplateLoader(new ClassRelativeResourceLoader(this.getClass()),
                        "/"));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        try {
            this.confirmMailTemplate = configuration.getTemplate("mail/confirm_mail.ftlh");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmailForConfirm(String email, String code) {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("confirm_code", code);
        attributes.put("host", host);
        String mailText = getEmailText(attributes);
        MimeMessagePreparator messagePreparator = getEmail(email, mailText, "Регистрация");
        javaMailSender.send(messagePreparator);
    }


    private MimeMessagePreparator getEmail(String email, String mailText, String subject) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(mailFrom);
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(mailText, true);
        };
    }

    private String getEmailText(HashMap<String, String> attributes) {
        StringWriter writer = new StringWriter();
        try {
            confirmMailTemplate.process(attributes, writer);
        } catch (TemplateException |
                IOException e) {
            throw new IllegalStateException(e);
        }
        return writer.toString();
    }
}
