package com.tobi.Erudite_Event_System.event.repository;

import com.tobi.Erudite_Event_System.event.entity.Events;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface EventsRepository extends JpaRepository<Events, Long> {
    List<Events> findByEventLocation(String location, PageRequest pageRequest);
    List<Events> findByEventName(String eventName, PageRequest pageRequest);

    Boolean existsByEventName(String eventName);
    List<Events> findAllByStartDateAfter(LocalDate date, PageRequest pageRequest);
    boolean existsByStartTimeBefore(LocalTime time);

}
