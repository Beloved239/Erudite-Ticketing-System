package com.tobi.Erudite_Event_System.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EventDto {
    private Long id;
    private String eventTitle;
    private String description;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String ticketType;
    private Integer ticketCapacity;
    private Integer ticketPrice;
    private String ticketDescription;
}
