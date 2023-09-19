package com.tobi.Erudite_Event_System.email.service.serviceImpl;


import com.tobi.Erudite_Event_System.dto.EmailDetails;
import com.tobi.Erudite_Event_System.dto.EmailResponse;
import com.tobi.Erudite_Event_System.email.entity.Message;
import com.tobi.Erudite_Event_System.email.repository.MessageRepository;
import com.tobi.Erudite_Event_System.email.service.EmailService;

import com.tobi.Erudite_Event_System.utils.ResponseUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class  EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final MessageRepository repository;
    @Value("${spring.mail.username}")
    private String senderEmail;
    private final String regexPattern = "^(.+)@(\\S+)$";

    @Override
    public ResponseEntity<EmailResponse> sendSimpleEmail(EmailDetails emailDetails) {

        if (patternMatches(emailDetails.getRecipient(), regexPattern))
        {
            return ResponseEntity.badRequest().body(EmailResponse.builder()
                    .responseCode(ResponseUtils.EMAIL_IS_NOT_VALID_CODE)
                    .responseMessage(ResponseUtils.EMAIL_IS_NOT_VALID_MESSAGE)
                    .build());
        }
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(senderEmail);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getMessageBody());
            mailMessage.setSubject(emailDetails.getSubject());
            Message message = Message.builder()
                            .subject(mailMessage.getSubject())
                                    .messageBody(mailMessage.getText())
                                            .recipient(emailDetails.getRecipient())
                                                    .build();

            javaMailSender.send(mailMessage);
            repository.save(message);
            return ResponseEntity.ok(EmailResponse.builder()
                    .responseCode(ResponseUtils.EMAIL_SENT_SUCCESSFULLY_CODE)
                    .responseMessage(ResponseUtils.EMAIL_SENT_SUCCESSFULLY_MESSAGE)
                    .responseBody("recipient: "+ emailDetails.getRecipient())
                    .build());
        }catch (MailException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public ResponseEntity<EmailResponse> sendEmailWithAttachment(EmailDetails emailDetails) {

        if (patternMatches(emailDetails.getRecipient(), regexPattern))
        {
            return ResponseEntity.badRequest().body(EmailResponse.builder()
                    .responseCode(ResponseUtils.EMAIL_IS_NOT_VALID_CODE)
                    .responseMessage(ResponseUtils.EMAIL_IS_NOT_VALID_MESSAGE)
                    .build());
        }

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(senderEmail);
            mimeMessageHelper.setTo(emailDetails.getRecipient());
            mimeMessageHelper.setText(emailDetails.getMessageBody());
            mimeMessageHelper.setSubject(emailDetails.getSubject());

            FileSystemResource file = new FileSystemResource(new File(emailDetails.getAttachment()));
            mimeMessageHelper.addAttachment(file.getFilename(), file);
            Message message = Message.builder()
                    .subject(emailDetails.getSubject())
                    .messageBody(emailDetails.getMessageBody())
                    .recipient(emailDetails.getRecipient())
                    .attachment(emailDetails.getAttachment())
                    .build();

            repository.save(message);
            javaMailSender.send(mimeMessage);
            return ResponseEntity.ok().body(EmailResponse.builder()
                    .responseCode(ResponseUtils.EMAIL_SENT_SUCCESSFULLY_CODE)
                    .responseMessage(ResponseUtils.EMAIL_SENT_SUCCESSFULLY_MESSAGE)
                    .responseBody("recipient: "+ emailDetails.getRecipient())
                    .build());

        }catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }


    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return !Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
