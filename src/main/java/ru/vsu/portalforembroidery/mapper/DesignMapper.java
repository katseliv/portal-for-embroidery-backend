package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vsu.portalforembroidery.model.dto.DesignDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignViewDto;
import ru.vsu.portalforembroidery.model.entity.DesignEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DesignMapper {

    @Mapping(target = "creatorDesignerId", source = "creatorDesigner.id")
    DesignDto designEntityToDesignDto(DesignEntity entity);

    @Mapping(target = "creatorDesignerFirstName", source = "creatorDesigner.firstName")
    @Mapping(target = "creatorDesignerLastName", source = "creatorDesigner.lastName")
    DesignViewDto designEntityToDesignViewDto(DesignEntity entity);

    @Mapping(target = "creatorDesigner.id", source = "creatorDesignerId")
    DesignEntity designDtoToDesignEntity(DesignDto dto);

    @Mapping(target = "creatorDesigner.firstName", source = "creatorDesignerFirstName")
    @Mapping(target = "creatorDesigner.lastName", source = "creatorDesignerLastName")
    DesignEntity designViewDtoToDesignEntity(DesignViewDto dto);

    List<DesignViewDto> designEntitiesToDesignViewDtoList(Iterable<DesignEntity> entities);

}
