package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.vsu.portalforembroidery.model.dto.FolderDto;
import ru.vsu.portalforembroidery.model.dto.FolderUpdateDto;
import ru.vsu.portalforembroidery.model.dto.view.FolderViewDto;
import ru.vsu.portalforembroidery.model.entity.FolderEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FolderMapper {

    @Mapping(target = "parentFolderId", source = "parentFolder.id")
    @Mapping(target = "creatorDesignerId", source = "creatorDesigner.id")
    FolderDto folderEntityToFolderDto(FolderEntity entity);

    @Mapping(target = "parentFolderName", source = "parentFolder.name")
    @Mapping(target = "creatorDesignerFirstName", source = "creatorDesigner.firstName")
    @Mapping(target = "creatorDesignerLastName", source = "creatorDesigner.lastName")
    FolderViewDto folderEntityToFolderViewDto(FolderEntity entity);

    @Mapping(target = "parentFolder.id", source = "parentFolderId")
    @Mapping(target = "creatorDesigner.id", source = "creatorDesignerId")
    FolderEntity folderDtoToFolderEntity(FolderDto dto);

    FolderEntity folderUpdateDtoToFolderEntity(FolderUpdateDto dto);

    @Mapping(target = "parentFolder.name", source = "parentFolderName")
    @Mapping(target = "creatorDesigner.firstName", source = "creatorDesignerFirstName")
    @Mapping(target = "creatorDesigner.lastName", source = "creatorDesignerLastName")
    FolderEntity folderViewDtoToFolderEntity(FolderViewDto dto);

    void mergeFolderEntityAndFolderUpdateDto(@MappingTarget FolderEntity entity, FolderUpdateDto dto);

    List<FolderViewDto> folderEntitiesToFolderViewDtoList(Iterable<FolderEntity> entities);

}
