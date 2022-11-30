package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import ru.vsu.portalforembroidery.model.dto.DesignerProfileDto;
import ru.vsu.portalforembroidery.model.dto.DesignerProfileRegistrationDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignerProfileViewDto;
import ru.vsu.portalforembroidery.model.entity.DesignerProfileEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DesignerProfileMapper {

    DesignerProfileDto designerProfileEntityToDesignerProfileDto(DesignerProfileEntity entity);

    DesignerProfileViewDto designerProfileEntityToDesignerProfileViewDto(DesignerProfileEntity entity);

    DesignerProfileEntity designerProfileDtoToDesignerProfileEntity(DesignerProfileDto dto);

    DesignerProfileEntity designerProfileViewDtoToDesignerProfileEntity(DesignerProfileViewDto dto);

    DesignerProfileEntity designerRegistrationProfileViewDtoToDesignerProfileEntityWithPassword(DesignerProfileRegistrationDto dto, String password);

    List<DesignerProfileViewDto> designerProfileEntitiesToDesignerProfileViewDtoList(Iterable<DesignerProfileEntity> entities);

}
