package com.tobi.Erudite_Event_System.email.service;



import com.tobi.Erudite_Event_System.dto.EmailDetails;
import com.tobi.Erudite_Event_System.dto.EmailResponse;
import org.springframework.http.ResponseEntity;


public interface EmailService {
    ResponseEntity<EmailResponse> sendSimpleEmail(EmailDetails emailDetails);

    ResponseEntity<EmailResponse> sendEmailWithAttachment(EmailDetails emailDetails);
}
