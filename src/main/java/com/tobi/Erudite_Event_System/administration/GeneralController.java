package com.tobi.Erudite_Event_System.administration;



import com.google.zxing.WriterException;
import com.tobi.Erudite_Event_System.dto.*;
import com.tobi.Erudite_Event_System.event.service.EventService;
import com.tobi.Erudite_Event_System.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/identity")
@RequiredArgsConstructor
public class GeneralController {

    private final UserService userService;
    private final EventService eventService;


    //Organizer Endpoints
    @PostMapping("/organizer/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomResponse> register(@RequestBody OrganizerSignUpRequest request){
        return userService.signUp(request);
    }

    @GetMapping("/organizer/confirmtoken")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomResponse> confirmToken(@RequestParam("token") String token){
        return userService.confirmToken(token);
    }

    @PostMapping("/organizer/signin")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomResponse> organizerSignIn(@RequestBody LoginRequest request){
        return userService.login(request);
    }

    @PostMapping("/organizer/password/reset")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomResponse> organizerResetPassword(@RequestBody LoginRequest request){
        return userService.resetPassword(request);
    }

    //Events
    @PostMapping("/bookEvent")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookEventResponse> bookEvent(@RequestParam(value = "organizerId") Long organizerId,
                                                       @RequestParam(value = "eventId")Long eventId,
                                                       @RequestBody BookEventRequest request) throws MissingRequestValueException, IOException, WriterException {
        return eventService.bookEvent(organizerId,eventId, request);
    }

    @GetMapping("/available/event")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomEventResponse> getAllAvailableEvents(@RequestParam(value = "eventName")String eventName){
        return eventService.getAllEventByName(eventName);
    }

    @GetMapping("/discover/allEvent")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomEventResponse> discoverEVents(){
        return eventService.getAllUpcomingEvents();
    }




}
