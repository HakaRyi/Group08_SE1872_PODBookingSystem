package com.example.POD_BookingSystem.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MailService {
    @Autowired
    JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendMail(String to, String subject, String name, String roomName, String bookingDate, String address) {
        final Context context = new Context(); // Đánh dấu context là final
        context.setVariable("name", name);
        context.setVariable("roomName", roomName);
        context.setVariable("bookingDate", bookingDate);
        context.setVariable("address", address);

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                messageHelper.setFrom("haxuankhang194@gmail.com");
                messageHelper.setTo(to);
                messageHelper.setSubject(subject);

                String content = templateEngine.process("emailTemplate", context);
                messageHelper.setText(content, true);
            }
        };
        mailSender.send(preparator);
    }
}