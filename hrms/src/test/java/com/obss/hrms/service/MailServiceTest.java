package com.obss.hrms.service;

import com.obss.hrms.request.SendMailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MailServiceTest {

    private JavaMailSender mailSender;
    private MailService mailService;
    @BeforeEach
    void setUp() {
        mailSender = mock(JavaMailSender.class);
        mailService = new MailService(mailSender);
    }

    @Test
    public void testSendMail_shouldSendMail(){
        SendMailRequest request = new SendMailRequest(
                "ahmet@gmail.com",
                "Advertisement Application status changed",
                "Advertisement Statue"
        );
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("noreply@metsoft.com");
        mailMessage.setTo(request.sendTo());
        mailMessage.setText(request.message());
        mailMessage.setSubject(request.subject());
        mailService.sendMail(request);
        verify(mailSender).send(mailMessage);

    }


}