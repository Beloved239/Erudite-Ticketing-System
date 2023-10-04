package com.tobi.Erudite_Event_System.administration;


import com.tobi.Erudite_Event_System.dto.*;
import com.tobi.Erudite_Event_System.event.service.EventService;
import com.tobi.Erudite_Event_System.images.entity.EventImages;
import com.tobi.Erudite_Event_System.images.service.EventImageService;
import com.tobi.Erudite_Event_System.payment.dto.PaymentVerificationResponse;
import com.tobi.Erudite_Event_System.payment.service.PayStackService;
import com.tobi.Erudite_Event_System.users.service.UserService;
import com.tobi.Erudite_Event_System.role.service.RoleService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/organizer")
@RequiredArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Beloved",
                        email = "adettob@gmail.com",
                        url = "https://www.linkedin.com/in/adebanjo-oluwatobi-6bb25b156/"
                ),
                description = "OpenApi documentation for Event Ticketing System",
                title = "Erudite OpenApi Specification",
                version = "v1",
                license = @License(
                        name = "License name",
                        url = "https://url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Prod ENV",
                        url = "https://url.com"
                ),
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        }
)
@SecurityScheme(
        name = "Authorization",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Tag(
        name = "Organizer Controller REST APIs/Endpoint",
        description = "Endpoints for creating event and other application management"
)
@SecurityRequirement(name = "bearerAuth")
public class OrganizerController{

    private final UserService userService;
    private final RoleService roleService;
    private final PayStackService paystackService;
    private final EventImageService service;
    private final EventService eventService;

    //Attendee Endpoints
    @GetMapping("/single")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getSingleUser(@RequestParam(value = "userId") Long userId){
        return userService.getSingleUserById(userId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CredentialResponse>> getAllOrganizers(@RequestParam(value = "pageNo",defaultValue = "0", required = false) int page,
                                                                    @RequestParam(value = "pageSize", defaultValue = "8", required = false) int size){
        return userService.getAllOrganizer(page, size);
    }

    @GetMapping("/single/user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomResponse> getAttendeeByEmail(@RequestParam(value = "email") String email){
        return userService.getUserByEmail(email);
    }

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
    @PutMapping("/update")
    @PreAuthorize("hasRole('ORGANIZER')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomResponse> updateRecord(@RequestParam(value = "userId")Long userId,
                                                       @RequestBody UsersUpdateRequest request){
        return userService.updateCredentials(userId, request);
    }

    @GetMapping("/getAll/getSingleOrganizer")
    @PreAuthorize("hasRole('ORGANIZER')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getSingleOrganizer(@RequestParam(value = "organizerid") Long organizerId){
        return userService.getSingleUserById(organizerId);
    }


    //Roles Endpoints
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomResponse> createRole(@RequestBody RoleRequest request){
        return roleService.createRole(request);
    }

    @DeleteMapping("/delete/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<CustomResponse> deleteRole(@PathVariable Long roleId){
        return roleService.deleteRole(roleId);
    }

    @GetMapping("/role")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<RoleResponse> getSingleRole(@RequestParam Long roleId){
        return roleService.getRoleById(roleId);
    }

    @GetMapping("/get/allRoles")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<RoleResponse> getAllRoles(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int page,
                                                    @RequestParam(value = "pageSize", defaultValue = "8", required = false) int size){
        return roleService.getAllRoles(page,size);
    }


    //Payment Endpoints
    @GetMapping("/verifyPayment")
    @ResponseStatus(HttpStatus.OK)
    public PaymentVerificationResponse paymentVerification(@RequestParam(value = "reference") String reference)
                                                                    throws Exception {
        return paystackService.paymentVerification(reference);
    }



    //Event Image Controller
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ImageResponse> saveEventPicture(@RequestParam("user")Long id,
                                                          @RequestParam("file") MultipartFile file,
                                                          @RequestParam("name") String name,
                                                          @RequestParam("desc") String desc) throws IOException {
        return service.saveImage(id,file, name, desc);
    }

    @GetMapping("/picture")
    @ResponseStatus(HttpStatus.OK)
    public EventImages getPicture(@RequestParam("id") Long id) throws IOException {
        return service.getProductImage(id);
    }

    @PutMapping("/update/event/picture")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ImageResponse> replacePicture(@RequestParam("image")Long imageId,
                                                        @RequestParam("user")Long id,
                                                        @RequestParam("file") MultipartFile file,
                                                        @RequestParam("name") String name,
                                                        @RequestParam("desc") String desc) throws IOException {
        return service.updatePicture(imageId,id,file, name, desc);
    }


    //Event
    @PostMapping("/create/event")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomResponse> createEvent(@RequestParam(value = "organizerid")Long id,
                                                      @RequestBody EventDto request){
        return eventService.createEvent(id, request);
    }



}
