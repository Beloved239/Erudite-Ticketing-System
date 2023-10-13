package com.tobi.Erudite_Event_System.publisher;

import com.tobi.Erudite_Event_System.email.service.EmailService;
import com.tobi.Erudite_Event_System.users.entity.Users;
import com.tobi.Erudite_Event_System.users.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompletePublisher> {
    private final UserService userService;
    private Users theUser;
    private final EmailService emailService;

    @Override
    public void onApplicationEvent(RegistrationCompletePublisher event) {
        //1. Get the newly registered user
        theUser = event.getUser();
        //2. Create verification token for user
        String verificationToken = UUID.randomUUID().toString();

        //3. Save the verification token to db
        userService.confirmToken(verificationToken);
        //4. Build the verification url
        String url = event.getApplicationUrl()+"/api/identity/organizer/confirmtoken?token="+verificationToken;

        //5. Send the url to user mail
        try {
            emailService.sendVerificationMail(url, theUser);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("click this link to verify your email {}", url);

    }
}
