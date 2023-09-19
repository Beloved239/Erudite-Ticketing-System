package com.tobi.Erudite_Event_System.dto;


import com.tobi.Erudite_Event_System.role.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredentialResponse {
     private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean isEnabled;
    private Set<Role> roleName;


}
