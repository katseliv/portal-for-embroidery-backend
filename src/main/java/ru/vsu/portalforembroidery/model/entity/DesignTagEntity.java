package ru.vsu.portalforembroidery.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "designs_tags")
@Table(name = "designs_tags")
public class DesignTagEntity {

    @EmbeddedId
    private DesignTagId id;

}
