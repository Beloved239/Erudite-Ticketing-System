package com.tobi.Erudite_Event_System.event.repository;

import com.tobi.Erudite_Event_System.event.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
