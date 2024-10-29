package com.example.POD_BookingSystem.Service;

import com.example.POD_BookingSystem.Entity.VerificationToken;
import com.example.POD_BookingSystem.Repository.VerificationTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

@Service
@EnableTransactionManagement
public class OtpService {
    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private static final int EXPIRATION_MINUTES = 15;

    @Transactional
    public void sendVerificationEmail(String email) throws MessagingException {
        // Xóa token cũ nếu có
        tokenRepository.deleteByEmail(email);

        // Tạo token mới
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setEmail(email);
        verificationToken.setToken(token);
        verificationToken.setExpiryDate(expiryDate);
        tokenRepository.save(verificationToken);

        // Tạo liên kết xác thực
        String verificationUrl = "http://localhost:8080/POD_BookingSystem/api/sms/verify?token=" + token;
        Context context = new Context();
        context.setVariable("verificationUrl", verificationUrl);

        String htmlContent = templateEngine.process("emailVerification", context);

        // Gửi email xác thực
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject("Email Verification");
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    @Transactional
    public boolean verifyToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElse(null);

        if (verificationToken != null && LocalDateTime.now().isBefore(verificationToken.getExpiryDate())) {
            // Token hợp lệ, xác thực thành công
            tokenRepository.delete(verificationToken); // Xóa token sau khi xác thực
            return true;
        }
        return false;
    }
}