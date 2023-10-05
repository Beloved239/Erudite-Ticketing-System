package com.tobi.Erudite_Event_System.users.service;


import com.tobi.Erudite_Event_System.dto.*;
import com.tobi.Erudite_Event_System.users.entity.Users;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    ResponseEntity<CustomResponse> signUp(OrganizerSignUpRequest request,  final HttpServletRequest urlRequest);
    ResponseEntity<CustomResponse> confirmToken(String token);
    ResponseEntity<CustomResponse> login(LoginRequest request);
    ResponseEntity<CustomResponse> updateCredentials(Long userId, UsersUpdateRequest request);
    ResponseEntity<?> getSingleUserById(Long userId);
    ResponseEntity<List<CredentialResponse>>  getAllOrganizer(int page, int size);
    ResponseEntity<CustomResponse> resetPassword(LoginRequest request);
    ResponseEntity<CustomResponse> getUserByEmail(String email);
    public void sendVerificationMail(String email)throws MessagingException, UnsupportedEncodingException;




}
