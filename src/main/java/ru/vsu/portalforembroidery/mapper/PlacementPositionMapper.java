package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import ru.vsu.portalforembroidery.model.dto.PlacementPositionDto;
import ru.vsu.portalforembroidery.model.dto.view.PlacementPositionViewDto;
import ru.vsu.portalforembroidery.model.entity.PlacementPositionEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlacementPositionMapper {

    PlacementPositionDto placementPositionEntityToPlacementPositionDto(PlacementPositionEntity entity);

    PlacementPositionViewDto placementPositionEntityToPlacementPositionViewDto(PlacementPositionEntity entity);

    PlacementPositionEntity placementPositionDtoToPlacementPositionEntity(PlacementPositionDto dto);

    PlacementPositionEntity placementPositionViewDtoToPlacementPositionEntity(PlacementPositionViewDto dto);

    List<PlacementPositionViewDto> placementPositionEntitiesToPlacementPositionViewDtoList(Iterable<PlacementPositionEntity> entities);

}
