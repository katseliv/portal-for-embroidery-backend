package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.vsu.portalforembroidery.model.dto.DesignDto;
import ru.vsu.portalforembroidery.model.dto.DesignUpdateDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignForListDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignViewDto;
import ru.vsu.portalforembroidery.model.entity.DesignEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DesignMapper {

    @Mapping(target = "folderId", source = "folder.id")
    @Mapping(target = "creatorDesignerId", source = "creatorDesigner.id")
    DesignDto designEntityToDesignDto(DesignEntity entity);

    @Mapping(target = "folderName", source = "folder.name")
    @Mapping(target = "creatorDesignerFirstName", source = "creatorDesigner.firstName")
    @Mapping(target = "creatorDesignerLastName", source = "creatorDesigner.lastName")
    DesignViewDto designEntityToDesignViewDto(DesignEntity entity);

    @Mapping(target = "folder.id", source = "folderId")
    @Mapping(target = "creatorDesigner.id", source = "creatorDesignerId")
    DesignEntity designDtoToDesignEntity(DesignDto dto);

    @Mapping(target = "folder.name", source = "folderName")
    @Mapping(target = "creatorDesigner.firstName", source = "creatorDesignerFirstName")
    @Mapping(target = "creatorDesigner.lastName", source = "creatorDesignerLastName")
    DesignEntity designViewDtoToDesignEntity(DesignViewDto dto);

    DesignForListDto designEntityToDesignForListDto(DesignEntity entity);

    List<DesignViewDto> designEntitiesToDesignViewDtoList(Iterable<DesignEntity> entities);

    List<DesignForListDto> designEntitiesToDesignForListDtoList(Iterable<DesignEntity> entities);

    void mergeDesignEntityAndDesignUpdateDto(@MappingTarget DesignEntity design, DesignUpdateDto designUpdateDto);

}
