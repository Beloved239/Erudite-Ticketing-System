package com.tobi.Erudite_Event_System.event.entity;

import com.tobi.Erudite_Event_System.users.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Column(name = "unique_id")
    private String uniqueId;

    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "organizers_event",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Users users;

}
