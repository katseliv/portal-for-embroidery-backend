package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import ru.vsu.portalforembroidery.model.dto.TagDto;
import ru.vsu.portalforembroidery.model.dto.view.TagViewDto;
import ru.vsu.portalforembroidery.model.entity.TagEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto tagEntityToTagDto(TagEntity entity);

    TagViewDto tagEntityToTagViewDto(TagEntity entity);

    TagEntity tagDtoToTagEntity(TagDto dto);

    TagEntity tagViewDtoToTagEntity(TagViewDto dto);

    List<TagViewDto> tagEntitiesToTagViewDtoList(Iterable<TagEntity> entities);

}
