package com.tobi.Erudite_Event_System.role.service;


import com.tobi.Erudite_Event_System.dto.CustomResponse;
import com.tobi.Erudite_Event_System.dto.RoleRequest;
import com.tobi.Erudite_Event_System.dto.RoleResponse;
import org.springframework.http.ResponseEntity;

public interface RoleService {
    ResponseEntity<CustomResponse> createRole(RoleRequest request);
    ResponseEntity<CustomResponse> deleteRole(Long roleId);
    ResponseEntity<RoleResponse> getAllRoles(int page, int size);
    ResponseEntity<RoleResponse> getRoleById(Long roleId);

}
