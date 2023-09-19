package com.tobi.Erudite_Event_System.event.repository;

import com.tobi.Erudite_Event_System.event.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface EventsRepository extends JpaRepository<Events, Long> {
    List<Events> findByEventLocation(String location);
    List<Events> findByEventName(String eventName);

    Boolean existsByEventName(String eventName);
    List<Events> findAllByStartDateAfter(LocalDate date);
    boolean existsByStartTimeBefore(LocalTime time);

}
