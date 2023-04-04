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
@Entity(name = "designs")
@Table(name = "designs")
public class DesignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "folder_id", referencedColumnName = "id")
    private FolderEntity folder;

    @ManyToOne
    @JoinColumn(name = "creator_designer_id", referencedColumnName = "id")
    private UserEntity creatorDesigner;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "designs_files",
            joinColumns = {@JoinColumn(name = "design_id")},
            inverseJoinColumns = {@JoinColumn(name = "file_id")}
    )
    @ToString.Exclude
    private List<FileEntity> files = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "designs_tags",
            joinColumns = {@JoinColumn(name = "design_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    @ToString.Exclude
    private List<TagEntity> tags = new ArrayList<>();

}
