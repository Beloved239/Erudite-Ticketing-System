package com.tobi.Erudite_Event_System.administration;



import com.google.zxing.WriterException;
import com.tobi.Erudite_Event_System.dto.*;
import com.tobi.Erudite_Event_System.event.service.EventService;
import com.tobi.Erudite_Event_System.users.service.UserService;
import com.tobi.Erudite_Event_System.users.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/identity")
@RequiredArgsConstructor
@Tag(
        name = "General Controller REST APIs/Endpoint",
        description = "Endpoints for sign up, sign in, discover events and book event"
)
public class GeneralController {

    private final UserService userService;
    private final EventService eventService;


    //Organizer Endpoints
    @Operation(
            description = "Signup endpoint for organizers",
            summary = "This endpoint allows user create account",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @PostMapping("/organizer/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomResponse> register(@RequestBody OrganizerSignUpRequest request){
        return userService.signUp(request);

    }

    @Operation(
            description = "Confirm token endpoint for users",
            summary = "This endpoint allows user confirm their email address",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/organizer/confirmtoken")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomResponse> confirmToken(@RequestParam("token") String token){
        return userService.confirmToken(token);
    }

    @Operation(
            description = "Login endpoint for users",
            summary = "This endpoint allows user log into their accounts",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @PostMapping("/organizer/signin")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomResponse> organizerSignIn(@RequestBody LoginRequest request){
        return userService.login(request);
    }

    @Operation(
            description = "Reset password endpoint for users",
            summary = "This endpoint allows user to reset their account password",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @PostMapping("/organizer/password/reset")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomResponse> organizerResetPassword(@RequestBody LoginRequest request){
        return userService.resetPassword(request);
    }

    //Events
    @Operation(
            description = "BookEvent endpoint for users",
            summary = "This endpoint allows user book an event",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @PostMapping("/bookEvent")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookEventResponse> bookEvent(@RequestParam(value = "organizerId") Long organizerId,
                                                       @RequestParam(value = "eventId")Long eventId,
                                                       @RequestBody BookEventRequest request) throws MissingRequestValueException, IOException, WriterException {
        return eventService.bookEvent(organizerId,eventId, request);
    }

    @Operation(
            description = "Get Event endpoint for users",
            summary = "This endpoint allows attendees to search for event using eventName",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                    description = "Event not found",
                    responseCode = "400"
            )
            }
    )
    @GetMapping("/available/event")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomEventResponse> getAllAvailableEvents(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int page,
                                                                     @RequestParam(value = "pageSize", defaultValue = "8", required = false) int size,
                                                                     @RequestParam(value = "eventName")String eventName){
        return eventService.getAllEventByName(page, size, eventName);
    }

    @Operation(
            description = "Discover Event endpoint",
            summary = "This endpoint allows for event discovery: i.e Get All Events",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/discover/allEvent")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomEventResponse> discoverEVents(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int page,
                                                              @RequestParam(value = "pageSize", defaultValue = "8", required = false) int size){
        return eventService.getAllUpcomingEvents(page, size);
    }




}
