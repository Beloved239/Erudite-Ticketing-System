package com.tobi.Erudite_Event_System.event.service;

import com.google.zxing.WriterException;
import com.tobi.Erudite_Event_System.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestValueException;

import java.io.IOException;

public interface EventService {
    ResponseEntity<CustomResponse> createEvent(Long id, EventDto eventDto);

    ResponseEntity<CustomEventResponse> getAllEventByName(String eventName);

    ResponseEntity<CustomEventResponse> getAllUpcomingEvents();
    ResponseEntity<BookEventResponse> bookEvent(Long organizerId, Long eventId, BookEventRequest request) throws IOException, MissingRequestValueException, WriterException;


}
