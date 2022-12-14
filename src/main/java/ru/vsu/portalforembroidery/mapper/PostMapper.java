package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.vsu.portalforembroidery.model.dto.PostDto;
import ru.vsu.portalforembroidery.model.dto.view.PostViewDto;
import ru.vsu.portalforembroidery.model.entity.PostEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "designerId", source = "designer.id")
    @Mapping(target = "designId", source = "design.id")
    PostDto postEntityToPostDto(PostEntity entity);

    @Mapping(target = "designerFirstName", source = "designer.firstName")
    @Mapping(target = "designerLastName", source = "designer.lastName")
    @Mapping(target = "designName", source = "design.name")
    PostViewDto postEntityToPostViewDto(PostEntity entity);

    @Mapping(target = "designer.id", source = "designerId")
    @Mapping(target = "design.id", source = "designId")
    PostEntity postDtoToPostEntity(PostDto dto);

    @Mapping(target = "designer.firstName", source = "designerFirstName")
    @Mapping(target = "designer.lastName", source = "designerLastName")
    @Mapping(target = "design.name", source = "designName")
    PostEntity postViewDtoToPostEntity(PostViewDto dto);

    void mergePostEntityAndPostDto(@MappingTarget PostEntity entity, PostDto dto);

    List<PostViewDto> postEntitiesToPostViewDtoList(Iterable<PostEntity> entities);

}
