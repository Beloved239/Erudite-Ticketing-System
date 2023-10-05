package com.tobi.Erudite_Event_System.token.repository;

import com.tobi.Erudite_Event_System.token.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    //    Optional<ConfirmationToken> findByToken(String token);
    ConfirmationToken findByToken(String token);

}
