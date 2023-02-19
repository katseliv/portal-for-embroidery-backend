package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ru.vsu.portalforembroidery.exception.EntityNotFoundException;
import ru.vsu.portalforembroidery.model.dto.PostDto;
import ru.vsu.portalforembroidery.model.dto.PostUpdateDto;
import ru.vsu.portalforembroidery.model.dto.view.FileViewDto;
import ru.vsu.portalforembroidery.model.dto.view.PostForListDto;
import ru.vsu.portalforembroidery.model.dto.view.PostViewDto;
import ru.vsu.portalforembroidery.model.entity.FileEntity;
import ru.vsu.portalforembroidery.model.entity.LikeEntity;
import ru.vsu.portalforembroidery.model.entity.PostEntity;
import ru.vsu.portalforembroidery.model.entity.TagEntity;

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
    @Mapping(target = "files", source = "design.files", qualifiedByName = "listOfFilesToListOfStrings")
    @Mapping(target = "tags", source = "design.tags", qualifiedByName = "listOfTagsToListOfStrings")
    @Mapping(target = "countLikes", source = "likes", qualifiedByName = "listOfLikesToCountLikes")
    PostViewDto postEntityToPostViewDto(PostEntity entity);

    @Mapping(target = "designName", source = "design.name")
    @Mapping(target = "designBase64StringImage", source = "design.files", qualifiedByName = "listOfFilesToFile")
    @Mapping(target = "countLikes", source = "likes", qualifiedByName = "listOfLikesToCountLikes")
    PostForListDto postEntityToPostForListDto(PostEntity entity);

    @Named(value = "listOfFilesToListOfStrings")
    default List<FileViewDto> mapListOfFilesToListOfStrings(List<FileEntity> files) {
        return files.stream().map(fileEntity -> {
            String name = fileEntity.getName();
            String extension = fileEntity.getExtension();
            String base64StringFile = Base64.getEncoder().encodeToString(fileEntity.getFile());
            return FileViewDto.builder().name(name).extension(extension).base64StringFile(base64StringFile).build();
        }).toList();
    }

    @Named(value = "listOfTagsToListOfStrings")
    default List<String> mapListOfTagsToListOfStrings(List<TagEntity> tags) {
        return tags.stream().map(TagEntity::getTitle).toList();
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

    void mergePostEntityAndPostUpdateDto(@MappingTarget PostEntity entity, PostUpdateDto dto);

    List<PostForListDto> postEntitiesToPostViewDtoList(Iterable<PostEntity> entities);

}
