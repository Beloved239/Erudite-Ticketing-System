package com.tobi.Erudite_Event_System.event.entity;

import com.tobi.Erudite_Event_System.users.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nameOfAttendee;
        private String email;
        private String phoneNumber;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "attendee_id")
        private Users organizer;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "event_id")
        private Events event;

        @CreationTimestamp
        private LocalDate bookingDate;

        private Integer price;

        private Integer quantity;

        private String eventName;

        private String ticketType;

        private Integer subTotal;


}
