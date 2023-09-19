package com.tobi.Erudite_Event_System.event.controller;

import com.tobi.Erudite_Event_System.dto.CustomEventResponse;
import com.tobi.Erudite_Event_System.dto.CustomResponse;
import com.tobi.Erudite_Event_System.dto.EventDto;
import com.tobi.Erudite_Event_System.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//@RestController
@RequiredArgsConstructor
//@RequestMapping("api/event")
public class EventController {

    private final EventService eventService;

    @PostMapping("/create/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<CustomResponse> createEvent(@PathVariable(name = "id")Long id,
                                                      @RequestBody EventDto eventDto){
        return eventService.createEvent(id, eventDto);
    }

    @GetMapping("/eventName")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomEventResponse> getAllAvailableEvents(@RequestParam(value = "eventName")String eventName){
        return eventService.getAllEventByName(eventName);
    }

    @GetMapping("/discover/allEvent")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomEventResponse> discoverEVents(){
        return eventService.getAllUpcomingEvents();
    }


}
