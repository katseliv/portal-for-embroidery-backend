package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.vsu.portalforembroidery.model.dto.DesignDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignViewDto;
import ru.vsu.portalforembroidery.model.entity.DesignEntity;

import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DesignMapper {

    @Mapping(target = "folderId", source = "folder.id")
    @Mapping(target = "creatorDesignerId", source = "creatorDesigner.id")
    DesignDto designEntityToDesignDto(DesignEntity entity);

    @Mapping(target = "base64StringFile", source = "file", qualifiedByName = "bytesArrayFile")
    @Mapping(target = "folderName", source = "folder.name")
    @Mapping(target = "creatorDesignerFirstName", source = "creatorDesigner.firstName")
    @Mapping(target = "creatorDesignerLastName", source = "creatorDesigner.lastName")
    DesignViewDto designEntityToDesignViewDto(DesignEntity entity);

    @Named(value = "bytesArrayFile")
    default String mapBytesArrayFile(byte[] file) {
        return Base64.getEncoder().encodeToString(file);
    }

    @Mapping(target = "folder.id", source = "folderId")
    @Mapping(target = "creatorDesigner.id", source = "creatorDesignerId")
    DesignEntity designDtoToDesignEntity(DesignDto dto);

    @Mapping(target = "file", source = "base64StringFile", qualifiedByName = "base64StringFile")
    @Mapping(target = "folder.name", source = "folderName")
    @Mapping(target = "creatorDesigner.firstName", source = "creatorDesignerFirstName")
    @Mapping(target = "creatorDesigner.lastName", source = "creatorDesignerLastName")
    DesignEntity designViewDtoToDesignEntity(DesignViewDto dto);

    @Named(value = "base64StringFile")
    default byte[] mapBase64StringImage(String base64StringFile) {
        return Base64.getDecoder().decode(base64StringFile);
    }

    List<DesignViewDto> designEntitiesToDesignViewDtoList(Iterable<DesignEntity> entities);

}
