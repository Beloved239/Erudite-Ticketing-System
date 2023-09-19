package com.tobi.Erudite_Event_System.users.service;


import com.tobi.Erudite_Event_System.dto.*;
import com.tobi.Erudite_Event_System.users.entity.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<CustomResponse> signUp(OrganizerSignUpRequest request);
    ResponseEntity<CustomResponse> confirmToken(String token);
    ResponseEntity<CustomResponse> login(LoginRequest request);
    ResponseEntity<CustomResponse> updateCredentials(Long userId, UsersUpdateRequest request);
    ResponseEntity<?> getSingleUserById(Long userId);
    ResponseEntity<List<CredentialResponse>>  getAllOrganizer();
    ResponseEntity<CustomResponse> resetPassword(LoginRequest request);
    ResponseEntity<CustomResponse> getUserByEmail(String email);


}
