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
@Entity(name = "model_photos")
@Table(name = "model_photos")
public class ModelPhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "file")
    private byte[] file;

    @ManyToOne
    @JoinColumn(name = "design_id", referencedColumnName = "id")
    private DesignEntity design;

    @ManyToOne
    @JoinColumn(name = "placement_position_id", referencedColumnName = "id")
    private PlacementPositionEntity placementPosition;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "model_photos_tags",
            joinColumns = {@JoinColumn(name = "model_photo_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    @ToString.Exclude
    private List<TagEntity> tags = new ArrayList<>();

}
