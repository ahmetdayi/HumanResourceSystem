package com.obss.hrms.service;

import com.obss.hrms.request.SendMailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;


    public void sendMail(SendMailRequest request) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("noreply@metsoft.com");
        mailMessage.setTo(request.sendTo());
        mailMessage.setText(request.message());
        mailMessage.setSubject(request.subject());
        mailSender.send(mailMessage);
    }
}
