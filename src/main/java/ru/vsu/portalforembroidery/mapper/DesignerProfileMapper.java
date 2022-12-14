package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ru.vsu.portalforembroidery.model.dto.DesignerProfileDto;
import ru.vsu.portalforembroidery.model.dto.DesignerProfileRegistrationDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignerProfileViewDto;
import ru.vsu.portalforembroidery.model.entity.DesignerProfileEntity;

import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DesignerProfileMapper {

    DesignerProfileDto designerProfileEntityToDesignerProfileDto(DesignerProfileEntity entity);

    DesignerProfileViewDto designerProfileEntityToDesignerProfileViewDto(DesignerProfileEntity entity);

    DesignerProfileEntity designerProfileDtoToDesignerProfileEntity(DesignerProfileDto dto);

    DesignerProfileEntity designerProfileViewDtoToDesignerProfileEntity(DesignerProfileViewDto dto);

    @Mapping(target = "password", source = "password")
    DesignerProfileEntity designerRegistrationProfileViewDtoToDesignerProfileEntityWithPassword(DesignerProfileRegistrationDto dto, String password);

    @Mapping(target = "image", source = "base64StringImage", qualifiedByName = "base64StringImage")
    void mergeDesignerProfileEntityAndDesignerProfileDto(@MappingTarget DesignerProfileEntity entity, DesignerProfileDto dto);

    @Named(value = "base64StringImage")
    default byte[] mapBase64StringImage(String base64StringImage) {
        return Base64.getDecoder().decode(base64StringImage);
    }

    void mergeDesignerProfileEntityAndDesignerProfileDtoWithoutPicture(@MappingTarget DesignerProfileEntity entity, DesignerProfileDto dto);

    List<DesignerProfileViewDto> designerProfileEntitiesToDesignerProfileViewDtoList(Iterable<DesignerProfileEntity> entities);

}
