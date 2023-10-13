package com.tobi.Erudite_Event_System.email.service;



import com.tobi.Erudite_Event_System.dto.EmailDetails;
import com.tobi.Erudite_Event_System.dto.EmailResponse;
import com.tobi.Erudite_Event_System.users.entity.Users;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;


public interface EmailService {
    ResponseEntity<EmailResponse> sendSimpleEmail(EmailDetails emailDetails);

    ResponseEntity<EmailResponse> sendEmailWithAttachment(EmailDetails emailDetails);
    public void sendVerificationMail(String url, Users user)throws MessagingException, UnsupportedEncodingException;
}
