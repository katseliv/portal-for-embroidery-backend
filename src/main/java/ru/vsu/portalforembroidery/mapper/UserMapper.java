package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ru.vsu.portalforembroidery.model.Role;
import ru.vsu.portalforembroidery.model.dto.UserDetailsDto;
import ru.vsu.portalforembroidery.model.dto.UserDto;
import ru.vsu.portalforembroidery.model.dto.UserRegistrationDto;
import ru.vsu.portalforembroidery.model.dto.view.UserForListDto;
import ru.vsu.portalforembroidery.model.dto.view.UserViewDto;
import ru.vsu.portalforembroidery.model.entity.UserEntity;

import java.util.Base64;
import java.util.List;

// TODO: 30.11.2022 протестировать role
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userEntityToUserDto(UserEntity entity);

    @Mapping(target = "base64StringImage", source = "image", qualifiedByName = "bytesArrayImage")
    UserViewDto userEntityToUserViewDto(UserEntity entity);

    @Named(value = "bytesArrayImage")
    default String mapImage(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }

    @Mapping(target = "roles", source = "role", qualifiedByName = "role")
    UserDetailsDto userEntityToUserDetailsDto(UserEntity entity);

    @Named(value = "role")
    default List<Role> mapRoles(Role role) {
        return List.of(role);
    }

    @Mapping(target = "password", source = "password")
    UserEntity userRegistrationDtoToUserEntityWithPassword(UserRegistrationDto dto, String password);

    @Mapping(target = "image", source = "base64StringImage", qualifiedByName = "base64StringImage")
    void mergeUserEntityAndUserDto(@MappingTarget UserEntity entity, UserDto dto);

    @Named(value = "base64StringImage")
    default byte[] mapBase64StringImage(String base64StringImage) {
        return Base64.getDecoder().decode(base64StringImage);
    }

    void mergeUserEntityAndUserDtoWithoutPicture(@MappingTarget UserEntity entity, UserDto dto);

    List<UserForListDto> userEntitiesToUserForListDtoList(Iterable<UserEntity> entities);

}
