package com.tobi.Erudite_Event_System.images.entity;


import com.tobi.Erudite_Event_System.users.entity.Users;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event_images")
@Builder
public class EventImages {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String description;

        @Lob
        @Column(name = "image_data",columnDefinition ="MEDIUMBLOB")
        private String image;

        @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinTable(name = "Images",
                joinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "organizer_id", referencedColumnName = "id"))
        private Users organizer;
}
