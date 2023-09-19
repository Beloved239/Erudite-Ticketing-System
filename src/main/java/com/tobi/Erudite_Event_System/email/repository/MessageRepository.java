package com.tobi.Erudite_Event_System.email.repository;


import com.tobi.Erudite_Event_System.email.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
