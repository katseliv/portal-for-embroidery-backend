package ru.vsu.portalforembroidery.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "designer_profiles")
@Table(name = "designer_profiles")
public class DesignerProfileEntity extends UserEntity {

    @Column(name = "experienced_since")
    private LocalDateTime experiencedSince;

    @Column(name = "description")
    private String description;

}
