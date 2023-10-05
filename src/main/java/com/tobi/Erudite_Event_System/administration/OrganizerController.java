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
    @Operation(
            description = "Get Single User endpoint",
            summary = "This endpoint is used to get Registered Users details from the database",
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
                            description = "User not found",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/single")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getSingleUser(@RequestParam(value = "userId") Long userId){
        return userService.getSingleUserById(userId);
    }

    @Operation(
            description = "Get All User Endpoint",
            summary = "This endpoint is used to fetch all registered Organizers from the database",
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
                            description = "User not found",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CredentialResponse>> getAllOrganizers(@RequestParam(value = "pageNo",defaultValue = "0", required = false) int page,
                                                                    @RequestParam(value = "pageSize", defaultValue = "8", required = false) int size){
        return userService.getAllOrganizer(page, size);
    }

    @Operation(
            description = "Get Attendee endpoint",
            summary = "This endpoint is used to get a single Attendee details from the database by Email",
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
                            description = "User not found",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/single/user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomResponse> getAttendeeByEmail(@RequestParam(value = "email") String email){
        return userService.getUserByEmail(email);
    }

    //Organizer Endpoints
    @Operation(
            description = "Update Record endpoint for organizers",
            summary = "This endpoint allow users/organizers to update their records",
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

    @Operation(
            description = "Get Single User/Organizer endpoint",
            summary = "This endpoint is used to get Registered Users details from the database using organizerId",
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
                            description = "User not found",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/getAll/getSingleOrganizer")
    @PreAuthorize("hasRole('ORGANIZER')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getSingleOrganizer(@RequestParam(value = "organizerid") Long organizerId){
        return userService.getSingleUserById(organizerId);
    }


    //Roles Endpoints
    @Operation(
            description = "Create Role endpoint",
            summary = "This endpoint is used to Create Roles",
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
                            description = "User not found",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomResponse> createRole(@RequestBody RoleRequest request){
        return roleService.createRole(request);
    }

    @Operation(
            description = "Delete Role endpoint",
            summary = "This endpoint is used to Delete Roles",
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
                            description = "User not found",
                            responseCode = "400"
                    )
            }
    )
    @DeleteMapping("/delete/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<CustomResponse> deleteRole(@PathVariable Long roleId){
        return roleService.deleteRole(roleId);
    }

    @Operation(
            description = "Get Role endpoint",
            summary = "This endpoint is used to Create Roles",
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
                            description = "User not found",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/role")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<RoleResponse> getSingleRole(@RequestParam Long roleId){
        return roleService.getRoleById(roleId);
    }

    @Operation(
            description = "Get All Roles endpoint",
            summary = "This endpoint is used get all created roles",
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
                            description = "User not found",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/get/allRoles")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<RoleResponse> getAllRoles(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int page,
                                                    @RequestParam(value = "pageSize", defaultValue = "8", required = false) int size){
        return roleService.getAllRoles(page,size);
    }


    //Payment Endpoints
    @Operation(
            description = "Verify Payment endpoint",
            summary = "This endpoint is used to Verify All Payments",
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
                            description = "User not found",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/verifyPayment")
    @ResponseStatus(HttpStatus.OK)
    public PaymentVerificationResponse paymentVerification(@RequestParam(value = "reference") String reference)
                                                                    throws Exception {
        return paystackService.paymentVerification(reference);
    }



    //Event Image Controller
    @Operation(
            description = "Save Image endpoint",
            summary = "This endpoint is used to save event Images",
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
                            description = "User not found",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ImageResponse> saveEventPicture(@RequestParam("user")Long id,
                                                          @RequestParam("file") MultipartFile file,
                                                          @RequestParam("name") String name,
                                                          @RequestParam("desc") String desc) throws IOException {
        return service.saveImage(id,file, name, desc);
    }

    @Operation(
            description = "Get Image endpoint",
            summary = "This endpoint is used to get event images",
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
                            description = "User not found",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/picture")
    @ResponseStatus(HttpStatus.OK)
    public EventImages getPicture(@RequestParam("id") Long id) throws IOException {
        return service.getProductImage(id);
    }

    @Operation(
            description = "Update Event Image Endpoint",
            summary = "This endpoint is used to get update event Image from the database",
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
                            description = "User not found",
                            responseCode = "400"
                    )
            }
    )
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
    @Operation(
            description = "Create Event endpoint",
            summary = "This endpoint is used to Create A New Event",
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
                            description = "User not found",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/create/event")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomResponse> createEvent(@RequestParam(value = "organizerid")Long id,
                                                      @RequestBody EventDto request){
        return eventService.createEvent(id, request);
    }



}
