package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vsu.portalforembroidery.model.dto.DesignFileDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignFileViewDto;
import ru.vsu.portalforembroidery.model.entity.DesignFileEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DesignFileMapper {

    @Mapping(target = "designId", source = "design.id")
    @Mapping(target = "fileId", source = "file.id")
    DesignFileDto designFileEntityToDesignFileDto(DesignFileEntity entity);

    @Mapping(target = "designName", source = "design.name")
    DesignFileViewDto designFileEntityToDesignFileViewDto(DesignFileEntity entity);

    @Mapping(target = "design.id", source = "designId")
    @Mapping(target = "file.id", source = "fileId")
    DesignFileEntity designFileDtoToDesignFileEntity(DesignFileDto dto);

    @Mapping(target = "design.name", source = "designName")
    DesignFileEntity designFileViewDtoToDesignFileEntity(DesignFileViewDto dto);

    List<DesignFileViewDto> designFileEntitiesToDesignFileViewDtoList(Iterable<DesignFileEntity> entities);

}
