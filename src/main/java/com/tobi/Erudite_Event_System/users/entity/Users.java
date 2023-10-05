package com.tobi.Erudite_Event_System.users.entity;


import com.tobi.Erudite_Event_System.images.entity.EventImages;
import com.tobi.Erudite_Event_System.role.entity.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.YesNoConverter;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "organizer")
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true)
    private String email;
    private String phoneNumber;
    private String address;
    @Convert(converter = YesNoConverter.class)
    private Boolean isEnabled;
    private String eventDetails;
    private String password;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "organizers_role",
            joinColumns = @JoinColumn(name = "organizer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roleName;

    @OneToOne(mappedBy = "organizer")
    private EventImages images;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreatedBy
    private String owner;

}
