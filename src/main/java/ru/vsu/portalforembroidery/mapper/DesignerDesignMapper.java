package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vsu.portalforembroidery.model.dto.DesignerDesignDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignerDesignViewDto;
import ru.vsu.portalforembroidery.model.entity.DesignerDesignEntity;

import java.util.List;

// TODO: 30.11.2022 протестировать permission
@Mapper(componentModel = "spring")
public interface DesignerDesignMapper {

    @Mapping(target = "designerId", source = "designer.id")
    @Mapping(target = "designId", source = "design.id")
        //@Mapping(target = "permissionId", source = "permission.id")
    DesignerDesignDto designerDesignEntityToDesignerDesignDto(DesignerDesignEntity entity);

    @Mapping(target = "designerFirstName", source = "designer.firstName")
    @Mapping(target = "designerLastName", source = "designer.lastName")
    @Mapping(target = "designName", source = "design.name")
    DesignerDesignViewDto designerDesignEntityToDesignerDesignViewDto(DesignerDesignEntity entity);

    @Mapping(target = "designer.id", source = "designerId")
    @Mapping(target = "design.id", source = "designId")
        //@Mapping(target = "permission.id", source = "permissionId")
    DesignerDesignEntity designerDesignDtoToDesignerDesignEntity(DesignerDesignDto dto);

    @Mapping(target = "designer.firstName", source = "designerFirstName")
    @Mapping(target = "designer.lastName", source = "designerLastName")
    @Mapping(target = "design.name", source = "designName")
    DesignerDesignEntity designerDesignViewDtoToDesignerDesignEntity(DesignerDesignViewDto dto);

    List<DesignerDesignViewDto> designerDesignEntitiesToDesignerDesignViewDtoList(Iterable<DesignerDesignEntity> entities);

}
