package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.vsu.portalforembroidery.model.dto.DesignerDesignDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignerDesignViewDto;
import ru.vsu.portalforembroidery.model.entity.DesignerDesignEntity;

import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DesignerDesignMapper {

    @Mapping(target = "designerId", source = "designer.id")
    @Mapping(target = "designId", source = "design.id")
    @Mapping(target = "permissionId", source = "permission.id")
    DesignerDesignDto designerDesignEntityToDesignerDesignDto(DesignerDesignEntity entity);

    @Mapping(target = "designerFirstName", source = "designer.firstName")
    @Mapping(target = "designerLastName", source = "designer.lastName")
    @Mapping(target = "designName", source = "design.name")
    @Mapping(target = "designBase64StringFile", source = "design.file", qualifiedByName = "bytesArrayFile")
    @Mapping(target = "permission", source = "permission.name")
    DesignerDesignViewDto designerDesignEntityToDesignerDesignViewDto(DesignerDesignEntity entity);

    @Named(value = "bytesArrayFile")
    default String mapBytesArrayFile(final byte[] file) {
        return Base64.getEncoder().encodeToString(file);
    }

    @Mapping(target = "designer.id", source = "designerId")
    @Mapping(target = "design.id", source = "designId")
    @Mapping(target = "permission.id", source = "permissionId")
    DesignerDesignEntity designerDesignDtoToDesignerDesignEntity(DesignerDesignDto dto);

    @Mapping(target = "designer.firstName", source = "designerFirstName")
    @Mapping(target = "designer.lastName", source = "designerLastName")
    @Mapping(target = "design.name", source = "designName")
    @Mapping(target = "design.file", source = "designBase64StringFile", qualifiedByName = "base64StringFile")
    @Mapping(target = "permission.name", source = "permission")
    DesignerDesignEntity designerDesignViewDtoToDesignerDesignEntity(DesignerDesignViewDto dto);

    @Named(value = "base64StringFile")
    default byte[] mapBase64StringFile(final String base64StringFile) {
        return Base64.getDecoder().decode(base64StringFile);
    }

    List<DesignerDesignViewDto> designerDesignEntitiesToDesignerDesignViewDtoList(Iterable<DesignerDesignEntity> entities);

}
