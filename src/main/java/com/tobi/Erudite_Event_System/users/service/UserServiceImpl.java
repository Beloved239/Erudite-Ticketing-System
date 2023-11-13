package com.tobi.Erudite_Event_System.users.service;



import com.tobi.Erudite_Event_System.dto.*;
import com.tobi.Erudite_Event_System.email.service.EmailService;
import com.tobi.Erudite_Event_System.role.entity.Role;
import com.tobi.Erudite_Event_System.role.repository.RoleRepository;
import com.tobi.Erudite_Event_System.security.config.JwtTokenProvider;
import com.tobi.Erudite_Event_System.token.entity.ConfirmationToken;
import com.tobi.Erudite_Event_System.token.repository.ConfirmationTokenRepository;
import com.tobi.Erudite_Event_System.users.entity.Users;
import com.tobi.Erudite_Event_System.users.repository.UserRepository;
import com.tobi.Erudite_Event_System.utils.ResponseUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ConfirmationTokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public ResponseEntity<CustomResponse> signUp(OrganizerSignUpRequest request, final HttpServletRequest urlRequest) {


        if (!patternMatches(request.getEmail(), ResponseUtils.REGEX_PATTERN)){
            return ResponseEntity.badRequest().body(CustomResponse.builder()
                    .responseCode(ResponseUtils.EMAIL_IS_NOT_VALID_CODE)
                    .responseMessage(ResponseUtils.EMAIL_IS_NOT_VALID_MESSAGE)
                    .build());
        }
        Boolean isExists = userRepository.existsByEmail(request.getEmail());
        if (isExists){
            return ResponseEntity.badRequest().body(CustomResponse.builder()
                    .responseCode(ResponseUtils.EMAIL_EXISTS_CODE)
                    .responseMessage(ResponseUtils.EMAIL_EXISTS_MESSAGE)
                    .build());
        }
        Users users = Users.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .organizerId(ResponseUtils.generateUniqueAlphaNumericString(8))
                .isEnabled(false)
                .build();

        Role role = roleRepository.findByRoleName("ROLE_ORGANIZER").get();
        users.setRoleName(Collections.singleton(role));
        userRepository.save(users);

        ConfirmationToken confirmationToken = new ConfirmationToken(users);
        log.info("confirmation token sent");

        tokenRepository.save(confirmationToken);
//        String mailContent = "Hello, "+ users.getEmail()+ ", </p>"+
//                "<p>Kindly Click on the link to confirm your email address. "+
//                "<h2 style='color: #057d25; letter-spacing: 0.1em'> "
//                +applicationUrl(urlRequest)+"/api/identity/organizer/confirmtoken?token="+confirmationToken.getToken()+"  "+
//                "<p> Thank you. </P> <hr> <br> <b> Central EventHub Ticketing Service.</b>";

//        String mailContentf = "<p> Hi, "+ theUser.getUsername()+ ", </p>"+
//                "<p>Below is the token to reset your password. "+"" +
//                "If you did not initiate this request, kindly contact admin at <b>info@techiebros.come</b>.</p>"+
//                "<h2 style='color: #057d25; letter-spacing: 0.1em'>"+token+"</h2>"+
//                "<p> Thank you. </P> <hr> <br> <b> Central Estore Service.</b>";

        EmailDetails messages = EmailDetails.builder()
                .subject("Account Created Successfully")
                .recipient(users.getEmail())
                .messageBody("Hello, "+ users.getEmail()+ ",\n"+
                        "Kindly Click on the link to confirm your email address. "+
                        applicationUrl(urlRequest)+"/api/identity/organizer/confirmtoken?token="+confirmationToken.getToken()+"  \n"+
                        "Thank you.\n" +
                        "EventHub Ticketing Service.")
                .build();

        emailService.sendSimpleEmail(messages);

        return ResponseEntity.ok(CustomResponse.builder()
                .responseCode(ResponseUtils.ACCOUNT_CREATION_SUCCESS_CODE)
                .responseMessage(ResponseUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                .responseBody("Kindly proceed to confirm your email address ")
                .build());
    }

    @Override
    public ResponseEntity<CustomResponse> confirmToken(String token) {
        ConfirmationToken token1 = tokenRepository.findByToken(token);
        if (token1 !=null){
            Users users = userRepository.findByEmailIgnoreCase(token1.getUsers().getEmail());
            users.setIsEnabled(true);
            String temporaryPassword = ResponseUtils.generateTemporaryPassword();
            userRepository.save(users);
            log.info("password: "+ temporaryPassword);
            EmailDetails message = EmailDetails.builder()
                    .recipient(users.getEmail())
                    .subject("Email Confirmed Successfully")
                    .messageBody("kindly proceed to login ")
                    .build();
            emailService.sendSimpleEmail(message);
            return ResponseEntity.ok(CustomResponse.builder()
                    .responseCode(ResponseUtils.ACCOUNT_CREATION_SUCCESS_CODE)
                    .responseMessage(ResponseUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                    .responseBody("Email Confirmed Successfully\n" +
                            "Kindly proceed to login")
                    .build());
        }
        return ResponseEntity.badRequest().body(CustomResponse.builder()
                .responseCode(ResponseUtils.INVALID_TOKEN_CODE)
                .responseMessage(ResponseUtils.INVALID_TOKEN_MESSAGE)
                .responseBody("Your Token is either expired or broken")
                .build());
    }

    @Override
    @Transactional
    public ResponseEntity<CustomResponse> login(LoginRequest request) {
        boolean exists = userRepository.existsByEmail(request.getEmail());
        log.info("Attendee status: " + exists);
        if (!exists) {
            return createErrorResponse("Incorrect Username or Password. Try Again!");
        }
        Optional<Users> attendees = userRepository.findByEmail(request.getEmail());
        if (attendees.isEmpty()) {
            return createErrorResponse("Incorrect Username or Password. Try Again!");
        }

        String password = request.getPassword();
        String hashedPassword = attendees.get().getPassword();

        if (!passwordEncoder.matches(password, hashedPassword)) {
            return createErrorResponse("Incorrect Username or Password. Try Again!");
        }

        Authentication authentication = authenticateUser(request.getEmail(), request.getPassword());
        log.info(authentication.getName());

        sendLoginAlertEmail(request.getEmail());

        return ResponseEntity.ok(
                CustomResponse.builder()
                        .responseCode(ResponseUtils.LOG_IN_CODE)
                        .responseMessage(ResponseUtils.LOG_IN_MESSAGE)
                        .responseBody(jwtTokenProvider.generateToken(authentication))
                        .build()
        );

    }

    public void sendVerificationMail(String url, Users user)throws MessagingException, UnsupportedEncodingException {
        url= "www.google.com";
        emailService.sendVerificationMail(url, user);
    }


    @Override
    @Transactional
    public ResponseEntity<CustomResponse> updateCredentials(Long userId, UsersUpdateRequest request) {
        boolean existsById = userRepository.existsById(userId);
        if (!existsById){
            log.info("organizer: ");
            return ResponseEntity.badRequest().body(CustomResponse.builder()
                    .responseCode(HttpStatus.BAD_REQUEST.toString())
                    .responseMessage(ResponseUtils.ORGANIZER_DOES_NOT_EXIST_MESSAGE)
                    .responseBody("Provide a valid id")
                    .build());
        }else {
            Users users = userRepository.findById(userId).get();
            log.info("user status: "+ users.getName());
            users.setAddress(request.getAddress());
            users.setName(request.getName());
            users.setPassword(passwordEncoder.encode(request.getPassword()));
            users.setPhoneNumber(request.getPhoneNumber());

            userRepository.save(users);
            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(users.getEmail())
                    .subject(ResponseUtils.ACCOUNT_CREATION_ALERT_SUBJECT)
                    .messageBody("Congratulations! Your account has been successfully updated. " +
                            "\nYour username is: " + users.getEmail() +
                            "\n Enjoy a Seamless Experience on our Event Software")
                    .build();

            emailService.sendSimpleEmail(emailDetails);
            return ResponseEntity.ok(CustomResponse.builder()
                    .responseCode(ResponseUtils.ACCOUNT_UPDATED_CODE)
                    .responseMessage(ResponseUtils.ACCOUNT_UPDATED_MESSAGE)
                    .responseBody("You're Welcome to Erudite Event Management System")
                    .build());
        }
    }

    @Override
    public ResponseEntity<?> getSingleUserById(Long userId) {
        if (userRepository.existsById(userId)) {
            Users users = userRepository.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("User with the provided Id does not exist"));
            log.info("Organizer= " + users);
            CredentialResponse response = CredentialResponse.builder()
                    .phoneNumber(users.getPhoneNumber())
                    .name(users.getName())
                    .address(users.getAddress())
                    .isEnabled(users.getIsEnabled())
                    .email(users.getEmail())
                    .roleName(users.getRoleName())
                    .build();

            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body(CustomResponse.builder()
                    .responseBody(ResponseUtils.ORGANIZER_DOES_NOT_EXIST_MESSAGE)
                    .responseCode(ResponseUtils.ORGANIZER_DOES_NOT_EXIST_CODE)
                    .responseMessage("User not found").build());
        }
    }

    @Override
    public ResponseEntity<List<CredentialResponse>> getAllOrganizer(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        Page<Users> usersList = userRepository.findAll(pageRequest);
        log.info("Organizers: "+ usersList);
        List<CredentialResponse> responseList = new ArrayList<>();
        for (Users users : usersList){
            responseList.add(CredentialResponse.builder()
                            .address(users.getAddress())
                            .name(users.getName())
                            .phoneNumber(users.getPhoneNumber())
                            .isEnabled(users.getIsEnabled())
                            .email(users.getEmail())
                            .roleName(users.getRoleName()).build());
        }
        return ResponseEntity.ok(responseList);

    }

    @Override
    public ResponseEntity<CustomResponse> resetPassword(LoginRequest request) {
        if (!userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.ok(CustomResponse.builder()
                            .responseCode(ResponseUtils.UNSUCCESSFUL_LOG_IN_CODE)
                            .responseMessage(ResponseUtils.UNSUCCESSFUL_LOG_IN_MESSAGE)
                            .responseBody("This user does not Exist")
                    .build());
        }else {
            Users users = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User with the provided email does not exist"));
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            users.setPassword(encodedPassword);
            userRepository.save(users);
            EmailDetails message = EmailDetails.builder()
                    .subject("Password Changed Successfully")
                    .messageBody("An attempt was made on your account to change password")
                    .recipient(users.getEmail())
                    .build();
            emailService.sendSimpleEmail(message);

            return ResponseEntity.ok(CustomResponse.builder()
                            .responseCode(ResponseUtils.SUCCESS_CODE)
                            .responseMessage(ResponseUtils.SUCCESS_MESSAGE)
                            .responseBody("Password changed Successfully " +
                                    "kindly proceed to Login")
                    .build());
        }
    }

    @Override
    public ResponseEntity<CustomResponse> getUserByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            log.info("User with email '{}' exists.", email);

            Users attendees = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User with the provided email does not exist"));

            log.info("Attendee found: {}", attendees);

            UserDetails userDetails = UserDetails.builder()
                    .name(attendees.getName())
                    .email(attendees.getEmail())
                    .build();

            CustomResponse response = CustomResponse.builder()

                    .responseCode(ResponseUtils.SUCCESS_CODE)
                    .responseMessage(ResponseUtils.SUCCESS_MESSAGE)
                    .responseBody(String.valueOf(userDetails))
                    .build();

            return ResponseEntity.ok(response);
        } else {
            log.info("User with email '{}' does not exist.", email);

            CustomResponse response = CustomResponse.builder()
                    .responseCode(ResponseUtils.ATTENDEE_DOES_NOT_EXIST_CODE)
                    .responseMessage(ResponseUtils.ATTENDEE_DOES_NOT_EXIST_MESSAGE)
                    .build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @Override
    public void sendVerificationMail(String email) throws MessagingException, UnsupportedEncodingException {
        String url ="www.google.com";
        Users users =userRepository.findByEmail(email).get();
        emailService.sendVerificationMail(url,users);
    }

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    private Authentication authenticateUser(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }


    private ResponseEntity<CustomResponse> createErrorResponse(String message) {
        return ResponseEntity.badRequest()
                .body(CustomResponse.builder()
                        .responseCode(ResponseUtils.UNSUCCESSFUL_LOG_IN_CODE)
                        .responseMessage(ResponseUtils.UNSUCCESSFUL_LOG_IN_MESSAGE)
                        .responseBody(message)
                        .build()
                );
    }

    private void sendLoginAlertEmail(String email) {
        EmailDetails loginAlert = EmailDetails.builder()
                .subject("YOU'RE LOGGED IN!")
                .recipient(email)
                .messageBody("You have successfully logged into your account!")
                .build();
        sendMail(loginAlert);
    }
    public void sendMail(EmailDetails emailDetails){
        emailService.sendSimpleEmail(emailDetails);
    }

    public static String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
