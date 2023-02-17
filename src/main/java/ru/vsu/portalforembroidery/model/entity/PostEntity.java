package ru.vsu.portalforembroidery.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "posts")
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_designer_id", referencedColumnName = "id")
    private UserEntity designer;

    @ManyToOne
    @JoinColumn(name = "design_id", referencedColumnName = "id")
    private DesignEntity design;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_datetime")
    private LocalDateTime creationDatetime;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "post_id")
    @ToString.Exclude
    private List<LikeEntity> likes = new ArrayList<>();

}
