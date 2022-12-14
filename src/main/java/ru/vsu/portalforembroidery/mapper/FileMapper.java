package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.vsu.portalforembroidery.model.dto.FileDto;
import ru.vsu.portalforembroidery.model.dto.view.FileForListDto;
import ru.vsu.portalforembroidery.model.dto.view.FileViewDto;
import ru.vsu.portalforembroidery.model.entity.FileEntity;

import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mapping(target = "folderId", source = "folder.id")
    FileDto fileEntityToFileDto(FileEntity entity);

    @Mapping(target = "folderName", source = "folder.name")
    @Mapping(target = "base64StringFile", source = "file", qualifiedByName = "bytesArrayFile")
    FileViewDto fileEntityToFileViewDto(FileEntity entity);

    @Named(value = "bytesArrayFile")
    default String mapBytesArrayFile(byte[] file) {
        return Base64.getEncoder().encodeToString(file);
    }

    @Mapping(target = "folder.id", source = "folderId")
    @Mapping(target = "file", source = "base64StringFile", qualifiedByName = "base64StringFile")
    FileEntity fileDtoToFileEntity(FileDto dto);

    @Mapping(target = "file", source = "base64StringFile", qualifiedByName = "base64StringFile")
    FileEntity fileViewDtoToFileEntity(FileViewDto dto);

    @Named(value = "base64StringFile")
    default byte[] mapBase64StringImage(String base64StringFile) {
        return Base64.getDecoder().decode(base64StringFile);
    }

    @Mapping(target = "folderName", source = "folder.name")
    FileForListDto fileEntityToFileForListDto(FileEntity entity);

    @Mapping(target = "folderName", source = "folder.name")
    List<FileForListDto> fileEntitiesToFileForListDtoList(Iterable<FileEntity> entities);

}
