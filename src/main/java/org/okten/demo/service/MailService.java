package org.okten.demo.service;

import lombok.RequiredArgsConstructor;
import org.okten.demo.dto.SendMailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSender mailSender;

    @Value("${mail.sender.username}")
    private String mailSenderUsername;

    public void sendMail(SendMailDto mailDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(mailSenderUsername);
        mailMessage.setTo(mailDto.getRecipient());

        mailMessage.setSubject(mailDto.getSubject());
        mailMessage.setText(mailDto.getText());

//        mailSender.send(mailMessage);
        System.out.printf("Mail to: '%s', with text: '%s'%n", mailDto.getRecipient(), mailDto.getText());
    }
}
