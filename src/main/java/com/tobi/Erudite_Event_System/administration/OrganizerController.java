package com.tobi.Erudite_Event_System.administration;


import com.tobi.Erudite_Event_System.dto.*;
import com.tobi.Erudite_Event_System.event.service.EventService;
import com.tobi.Erudite_Event_System.images.entity.EventImages;
import com.tobi.Erudite_Event_System.images.service.EventImageService;
import com.tobi.Erudite_Event_System.payment.dto.PaymentVerificationResponse;
import com.tobi.Erudite_Event_System.payment.service.PayStackService;
import com.tobi.Erudite_Event_System.users.service.UserService;
import com.tobi.Erudite_Event_System.role.service.RoleService;
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
