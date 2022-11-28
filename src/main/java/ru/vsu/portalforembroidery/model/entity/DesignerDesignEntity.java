package ru.vsu.portalforembroidery.model.entity;

import lombok.*;
import ru.vsu.portalforembroidery.converter.PermissionConverter;
import ru.vsu.portalforembroidery.model.Permission;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "designers_designs")
@Table(name = "designers_designs")
public class DesignerDesignEntity {

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

    @Column(name = "permission_id")
    @Convert(converter = PermissionConverter.class)
    private Permission permission;

}
