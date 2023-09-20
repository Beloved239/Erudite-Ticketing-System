
package com.tobi.Erudite_Event_System.role.service;


import com.tobi.Erudite_Event_System.dto.CustomResponse;
import com.tobi.Erudite_Event_System.dto.RoleRequest;
import com.tobi.Erudite_Event_System.dto.RoleResponse;
import com.tobi.Erudite_Event_System.dto.EmailDetails;
import com.tobi.Erudite_Event_System.email.service.EmailService;
import com.tobi.Erudite_Event_System.role.entity.Role;
import com.tobi.Erudite_Event_System.role.repository.RoleRepository;
import com.tobi.Erudite_Event_System.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final EmailService emailService;


    @Override
    public ResponseEntity<CustomResponse> createRole(RoleRequest request) {

        if (roleRepository.existsByRoleName(request.getRoleName())){
            return ResponseEntity.badRequest().body(CustomResponse.builder()
                    .responseCode(ResponseUtils.USER_ROLE_EXIST_CODE)
                    .responseMessage(ResponseUtils.USER_ROLE_EXIST_MESSAGE)
                    .responseBody("Kindly Provide another Role Name or Proceed to make use of the existing Role Name")
                    .build());

        }else {
//            Role role = roleRepository.findByRoleName(request.getRoleName())
//                    .orElseThrow(() -> new UsernameNotFoundException("Role with the provided name does not exist"));
            Role role = Role.builder().build();
            role.setRoleName(request.getRoleName());
            roleRepository.save(role);

            return ResponseEntity.ok(CustomResponse.builder()
                    .responseCode(ResponseUtils.USER_ROLE_SET_CODE)
                    .responseBody(ResponseUtils.USER_ROLE_SET_MESSAGE)
                    .responseMessage(request.getRoleName()+" has been set Successfully")
                    .build());


        }

    }

    @Override
    public ResponseEntity<CustomResponse> deleteRole(Long roleId) {

        if (roleRepository.existsById(roleId)){
                Role role = roleRepository.findById(roleId).orElseThrow(() ->
                        new UsernameNotFoundException("Role does not exist"));
                roleRepository.delete(role);
            return ResponseEntity.ok(CustomResponse.builder()
                            .responseBody(ResponseUtils.ROLE_DELETED_MESSAGE)
                            .responseCode(ResponseUtils.ROLE_DELETED_CODE)
                            .responseMessage("Success")
                    .build());
        }else {
            return ResponseEntity.badRequest().body(CustomResponse.builder()
                    .responseCode(ResponseUtils.ROLE_DOES_NOT_EXIST_CODE)
                    .responseMessage(ResponseUtils.ROLE_DOES_NOT_EXIST_MESSAGE)
                    .responseBody("Kindly Provide a valid Role")
                    .build());
        }
    }

    @Override
    public ResponseEntity<RoleResponse> getAllRoles(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("roleName").ascending());
        Page<Role> roles = roleRepository.findAll(pageRequest);
        List<RoleRequest> roleRequestList = new ArrayList<>();

        for (Role newRole: roles){
            roleRequestList.add(RoleRequest.builder()
                            .roleName(newRole.getRoleName())
                    .build());
        }return ResponseEntity.ok(RoleResponse.builder()
                        .statusCode(ResponseUtils.SUCCESS_CODE)
                        .statusMessage(ResponseUtils.SUCCESS_MESSAGE)
                        .roleRequest(roleRequestList)
                .build());

    }

    @Override
    public ResponseEntity<RoleResponse> getRoleById(Long roleId) {
        if (roleRepository.existsById(roleId)){
            Role role = roleRepository.findById(roleId).orElseThrow(() ->
                    new UsernameNotFoundException("Role does not exist"));
            List<RoleRequest> responseList = new ArrayList<>();
            responseList.add(0,RoleRequest.builder()
                            .roleName(role.getRoleName())
                    .build());
            return ResponseEntity.ok().body(RoleResponse.builder()
                            .statusMessage(ResponseUtils.SUCCESS_MESSAGE)
                            .statusCode(ResponseUtils.SUCCESS_CODE)
                            .roleRequest(responseList)
                    .build());
        }else {
            return ResponseEntity.badRequest().body(RoleResponse.builder()
                            .statusCode(ResponseUtils.ROLE_DOES_NOT_EXIST_CODE)
                            .statusMessage(ResponseUtils.ROLE_DOES_NOT_EXIST_MESSAGE)
                    .build());
        }
    }

    public void sendMail(EmailDetails emailDetails){
        emailService.sendSimpleEmail(emailDetails);
    }
}
