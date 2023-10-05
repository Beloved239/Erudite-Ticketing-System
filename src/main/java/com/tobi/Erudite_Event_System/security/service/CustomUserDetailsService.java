package com.tobi.Erudite_Event_System.security.service;




import com.tobi.Erudite_Event_System.users.entity.Users;
import com.tobi.Erudite_Event_System.users.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         if (userRepository.existsByEmail(username)) {
            Users users = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

            Set<GrantedAuthority> authorities = users.getRoleName().stream()
                    .map((role) -> new SimpleGrantedAuthority(role.getRoleName()))
                    .collect(Collectors.toSet());

            return new User(
                    users.getEmail(),
                    users.getPassword(),
                    authorities
            );
        } else {

            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}
