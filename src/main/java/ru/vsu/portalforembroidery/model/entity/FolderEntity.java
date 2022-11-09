package ru.vsu.portalforembroidery.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "folders")
@Table(name = "folders")
public class FolderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_folder_id", referencedColumnName = "id")
    private FolderEntity parentFolder;

    @ManyToOne
    @JoinColumn(name = "creator_designer_id", referencedColumnName = "id")
    private UserEntity creatorDesigner;

}
