package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.vsu.portalforembroidery.model.dto.JwtTokenDto;
import ru.vsu.portalforembroidery.model.entity.JwtTokenEntity;

@Mapper(componentModel = "spring")
public interface JwtTokenMapper {

    JwtTokenEntity jwtTokenDtoToJwtTokenEntity(JwtTokenDto dto);

    void mergeJwtTokenEntityAndJwtTokenDto(@MappingTarget JwtTokenEntity entity, JwtTokenDto dto);

}