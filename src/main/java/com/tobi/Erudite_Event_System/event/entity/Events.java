package com.tobi.Erudite_Event_System.event.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "event")
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String eventName;
    private String eventDescription;
    private String eventLocation;
    @Column(columnDefinition = "DATE")
    private LocalDate startDate;
    @Column(columnDefinition = "DATE")
    private LocalDate endDate;
    @Column(columnDefinition = "TIME")
    private LocalTime startTime;
    @Column(columnDefinition = "TIME")
    private LocalTime endTime;
    private String ticketDescription;
    @Column(nullable = false)
    private String ticketType;
    @Column(nullable = false)
    private Integer ticketCapacity;
    @Column(nullable = false)
    private Integer ticketPrice;

    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL)
    private List<Booking> bookings;

}
