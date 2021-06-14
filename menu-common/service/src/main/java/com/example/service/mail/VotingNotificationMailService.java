package com.example.service.mail;

import com.example.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class VotingNotificationMailService implements MailService {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @Autowired
    public VotingNotificationMailService(JavaMailSender mailSender,
                                         TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }


    @Override
    public void sendMail(Customer customer) {
        String process = buildContextWithTemplate(customer);
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject(customer.getEmail());
            helper.setText(process, true);
            helper.setTo(customer.getEmail());
        };

        mailSender.send(preparator);
    }

    private String buildContextWithTemplate(Customer customer) {
        Context context = new Context();
        context.setVariable("customer", customer);

        String process = templateEngine.process(THANKFUL_TEMPLATE,context);

        return process;
    }
}
