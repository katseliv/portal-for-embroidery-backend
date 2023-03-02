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
    @Mapping(target = "creatorUserId", source = "creatorUser.id")
    FolderDto folderEntityToFolderDto(FolderEntity entity);

    @Mapping(target = "parentFolderName", source = "parentFolder.name")
    @Mapping(target = "creatorUserFirstName", source = "creatorUser.firstName")
    @Mapping(target = "creatorUserLastName", source = "creatorUser.lastName")
    FolderViewDto folderEntityToFolderViewDto(FolderEntity entity);

    @Mapping(target = "parentFolder.id", source = "parentFolderId")
    @Mapping(target = "creatorUser.id", source = "creatorUserId")
    FolderEntity folderDtoToFolderEntity(FolderDto dto);

    FolderEntity folderUpdateDtoToFolderEntity(FolderUpdateDto dto);

    @Mapping(target = "parentFolder.name", source = "parentFolderName")
    @Mapping(target = "creatorUser.firstName", source = "creatorUserFirstName")
    @Mapping(target = "creatorUser.lastName", source = "creatorUserLastName")
    FolderEntity folderViewDtoToFolderEntity(FolderViewDto dto);

    void mergeFolderEntityAndFolderUpdateDto(@MappingTarget FolderEntity entity, FolderUpdateDto dto);

    List<FolderViewDto> folderEntitiesToFolderViewDtoList(Iterable<FolderEntity> entities);

}
