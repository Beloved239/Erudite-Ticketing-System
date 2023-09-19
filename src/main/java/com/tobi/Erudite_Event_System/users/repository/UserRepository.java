package com.tobi.Erudite_Event_System.users.repository;



import com.tobi.Erudite_Event_System.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {
    Boolean existsByEmail(String email);
    Boolean existsByPassword(String password);
    Optional<Users> findByEmail(String email);
    Users findByEmailIgnoreCase(String email);

}
