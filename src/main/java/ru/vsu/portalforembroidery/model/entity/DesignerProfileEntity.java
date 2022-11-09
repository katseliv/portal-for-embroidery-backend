package ru.vsu.portalforembroidery.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "designer_profiles")
@Table(name = "designer_profiles")
public class DesignerProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_designer_id", referencedColumnName = "id")
    private UserEntity designer;

    @Column(name = "experienced_since")
    private LocalDateTime experiencedSince;

    @Column(name = "description")
    private String description;

}
