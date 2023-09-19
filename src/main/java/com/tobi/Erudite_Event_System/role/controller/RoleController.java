package com.tobi.Erudite_Event_System.role.controller;

import com.tobi.Erudite_Event_System.dto.CustomResponse;
import com.tobi.Erudite_Event_System.dto.RoleRequest;
import com.tobi.Erudite_Event_System.dto.RoleResponse;
import com.tobi.Erudite_Event_System.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("api/identity/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<CustomResponse> createRole(@RequestBody RoleRequest request){
        return roleService.createRole(request);
    }

    @DeleteMapping("/delete/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<CustomResponse> deleteRole(@PathVariable Long roleId){
        return roleService.deleteRole(roleId);
    }

    @GetMapping("/role/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<RoleResponse> getSingleRole(@PathVariable Long roleId){
        return roleService.getRoleById(roleId);
    }

    @GetMapping("/get/allRoles")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<RoleResponse> getAllRoles(){
        return roleService.getAllRoles();
    }



}
