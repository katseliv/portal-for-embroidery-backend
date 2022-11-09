package ru.vsu.portalforembroidery.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "tags")
@Table(name = "tags")
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "count")
    private Integer count;

    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude
    private List<DesignEntity> designEntityList = new ArrayList<>();

    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude
    private List<PlacementPositionEntity> placementPositionEntityList = new ArrayList<>();

    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude
    private List<ModelPhotoEntity> modelPhotoEntityList = new ArrayList<>();

}
