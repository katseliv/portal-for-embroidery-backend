package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ru.vsu.portalforembroidery.exception.EntityNotFoundException;
import ru.vsu.portalforembroidery.model.dto.PostDto;
import ru.vsu.portalforembroidery.model.dto.view.PostForListDto;
import ru.vsu.portalforembroidery.model.dto.view.PostViewDto;
import ru.vsu.portalforembroidery.model.entity.FileEntity;
import ru.vsu.portalforembroidery.model.entity.LikeEntity;
import ru.vsu.portalforembroidery.model.entity.PostEntity;

import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "designerId", source = "designer.id")
    @Mapping(target = "designId", source = "design.id")
    PostDto postEntityToPostDto(PostEntity entity);

    @Mapping(target = "designerFirstName", source = "designer.firstName")
    @Mapping(target = "designerLastName", source = "designer.lastName")
    @Mapping(target = "designName", source = "design.name")
    @Mapping(target = "designBase64StringImages", source = "design.files", qualifiedByName = "listOfFilesToListOfStrings")
    @Mapping(target = "countLikes", source = "likes", qualifiedByName = "listOfLikesToCountLikes")
    PostViewDto postEntityToPostViewDto(PostEntity entity);

    @Mapping(target = "designName", source = "design.name")
    @Mapping(target = "designBase64StringImage", source = "design.files", qualifiedByName = "listOfFilesToFile")
    @Mapping(target = "countLikes", source = "likes", qualifiedByName = "listOfLikesToCountLikes")
    PostForListDto postEntityToPostForListDto(PostEntity entity);

    @Named(value = "listOfFilesToListOfStrings")
    default List<String> mapListOfFilesToListOfStrings(List<FileEntity> files) {
        return files.stream().map(fileEntity -> Base64.getEncoder().encodeToString(fileEntity.getFile())).toList();
    }

    @Named(value = "listOfLikesToCountLikes")
    default Integer mapListOfLikesToCountLikes(List<LikeEntity> likes) {
        return likes.size();
    }

    @Named(value = "listOfFilesToFile")
    default String mapListOfFiles(List<FileEntity> files) {
        FileEntity fileEntity = files.stream().findFirst()
                .orElseThrow(() -> new EntityNotFoundException("File not found!"));
        return Base64.getEncoder().encodeToString(fileEntity.getFile());
    }

    @Mapping(target = "designer.id", source = "designerId")
    @Mapping(target = "design.id", source = "designId")
    PostEntity postDtoToPostEntity(PostDto dto);

    @Mapping(target = "designer.firstName", source = "designerFirstName")
    @Mapping(target = "designer.lastName", source = "designerLastName")
    @Mapping(target = "design.name", source = "designName")
    PostEntity postViewDtoToPostEntity(PostViewDto dto);

    void mergePostEntityAndPostDto(@MappingTarget PostEntity entity, PostDto dto);

    List<PostForListDto> postEntitiesToPostViewDtoList(Iterable<PostEntity> entities);

}
