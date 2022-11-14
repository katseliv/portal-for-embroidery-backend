package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vsu.portalforembroidery.model.dto.DesignerProfileDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignerProfileViewDto;
import ru.vsu.portalforembroidery.model.entity.DesignerProfileEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DesignerProfileMapper {

    @Mapping(target = "designerId", source = "designer.id")
    DesignerProfileDto designerProfileEntityToDesignerProfileDto(DesignerProfileEntity entity);

    DesignerProfileViewDto designerProfileEntityToDesignerProfileViewDto(DesignerProfileEntity entity);

    @Mapping(target = "designer.id", source = "designerId")
    DesignerProfileEntity designerProfileDtoToDesignerProfileEntity(DesignerProfileDto dto);

    DesignerProfileEntity designerProfileViewDtoToDesignerProfileEntity(DesignerProfileViewDto dto);

    List<DesignerProfileViewDto> designerProfileEntitiesToDesignerProfileViewDtoList(Iterable<DesignerProfileEntity> entities);

}
