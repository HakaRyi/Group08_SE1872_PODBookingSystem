package com.example.POD_BookingSystem.Controller;

import com.example.POD_BookingSystem.DTO.Request.OTP.EmailVerificationRequest;
import com.example.POD_BookingSystem.Service.MailService;
import com.example.POD_BookingSystem.Service.OtpService;
import com.example.POD_BookingSystem.Service.SmsService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/sms")
public class OtpController {

    @Autowired
    private OtpService emailVerificationService;

    @PostMapping("/request")
    public String sendVerification(@RequestBody EmailVerificationRequest request) throws MessagingException {
        emailVerificationService.sendVerificationEmail(request.getEmail());
        return "Verification email sent to " + request.getEmail();
    }

    @GetMapping("/verify")
    public ModelAndView verifyEmail(@RequestParam String token) {
        ModelAndView modelAndView = new ModelAndView("verificationResult");

        boolean isVerified = emailVerificationService.verifyToken(token);

        return isVerified ? modelAndView.addObject("message", "Email verified successfully!")
                : modelAndView.addObject("message", "Invalid or expired verification link.");
    }
}
