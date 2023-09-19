//package com.tobi.Erudite_Event_System.users.controller;
//
//
//import com.tobi.Erudite_Event_System.dto.CustomResponse;
//import com.tobi.Erudite_Event_System.dto.*;
//import com.tobi.Erudite_Event_System.users.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
////@RestController
////@RequestMapping("api/identity/organizer")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserService userService;
//
//    @PostMapping("/signup")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<CustomResponse> register(@RequestBody OrganizerSignUpRequest request){
//        return userService.signUp(request);
//    }
//
//    @GetMapping("/confirmtoken")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<CustomResponse> confirmToken(@RequestParam("token") String token){
//        return userService.confirmToken(token);
//    }
//
//    @PostMapping("/signin")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<CustomResponse> signIn(@RequestBody LoginRequest request){
//        return userService.login(request);
//    }
//
//    @PutMapping("/update/{userId}")
//    @PreAuthorize("hasRole('ORGANIZER')")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<CustomResponse> updateRecord(@PathVariable(name = "userId") Long userId,
//                                                       @RequestBody UsersUpdateRequest request){
//        return userService.updateCredentials(userId, request);
//    }
//
//    @GetMapping("/getAll/organizer")
//    @PreAuthorize("hasRole('ORGANIZER')")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<List<CredentialResponse>> getAllOrganizers(){
//        return userService.getAllOrganizer();
//    }
//
//    @GetMapping("/getAll/getSingleOrganizer/{organizerId}")
//    @PreAuthorize("hasRole('ORGANIZER')")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<?> getSingleOrganizer(@PathVariable Long organizerId){
//        return userService.getSingleUserById(organizerId);
//    }
//
//    @PostMapping("/password/reset")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<CustomResponse> resetPassword(@RequestBody LoginRequest request){
//        return userService.resetPassword(request);
//    }
//
//
//
//
//}
