package ru.vsu.portalforembroidery.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@PrimaryKeyJoinColumn(name = "user_id_ptr")
@Entity(name = "designer_profiles")
@Table(name = "designer_profiles")
public class DesignerProfileEntity extends UserEntity {

    @Column(name = "experienced_since")
    private LocalDateTime experiencedSince;

    @Column(name = "description")
    private String description;

}
