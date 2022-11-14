package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.vsu.portalforembroidery.model.dto.PostDto;
import ru.vsu.portalforembroidery.model.dto.view.PostViewDto;
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
    @Mapping(target = "designBase64StringFile", source = "design.file", qualifiedByName = "bytesArrayFile")
    PostViewDto postEntityToPostViewDto(PostEntity entity);

    @Named(value = "bytesArrayFile")
    default String mapBytesArrayFile(final byte[] file) {
        return Base64.getEncoder().encodeToString(file);
    }

    @Mapping(target = "designer.id", source = "designerId")
    @Mapping(target = "design.id", source = "designId")
    PostEntity postDtoToPostEntity(PostDto dto);

    @Mapping(target = "designer.firstName", source = "designerFirstName")
    @Mapping(target = "designer.lastName", source = "designerLastName")
    @Mapping(target = "design.name", source = "designName")
    @Mapping(target = "design.file", source = "designBase64StringFile", qualifiedByName = "base64StringFile")
    PostEntity postViewDtoToPostEntity(PostViewDto dto);

    @Named(value = "base64StringFile")
    default byte[] mapBase64StringFile(final String base64StringFile) {
        return Base64.getDecoder().decode(base64StringFile);
    }

    List<PostViewDto> postEntitiesToPostViewDtoList(Iterable<PostEntity> entities);

}
