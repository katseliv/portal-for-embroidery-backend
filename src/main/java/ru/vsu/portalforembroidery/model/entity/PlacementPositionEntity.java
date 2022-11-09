package ru.vsu.portalforembroidery.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "placement_positions")
@Table(name = "placement_positions")
public class PlacementPositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "anchor")
    private Integer anchor;

    @Column(name = "top_margin_position")
    private BigDecimal topMarginPosition;

    @Column(name = "bottom_margin_position")
    private BigDecimal bottomMarginPosition;

    @Column(name = "left_margin_position")
    private BigDecimal leftMarginPosition;

    @Column(name = "right_margin_position")
    private BigDecimal rightMarginPosition;

    @Column(name = "height_percent")
    private BigDecimal heightPercent;

    @Column(name = "width_percent")
    private BigDecimal widthPercent;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "placement_positions_tags",
            joinColumns = {@JoinColumn(name = "placement_position_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    @ToString.Exclude
    List<TagEntity> tags = new ArrayList<>();

}
