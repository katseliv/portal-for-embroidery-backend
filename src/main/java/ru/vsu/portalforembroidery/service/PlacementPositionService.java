package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.model.dto.PlacementPositionDto;
import ru.vsu.portalforembroidery.model.dto.view.PlacementPositionViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;

import java.util.List;

public interface PlacementPositionService {

    int createPlacementPosition(PlacementPositionDto PlacementPositionDto);

    PlacementPositionViewDto getPlacementPositionViewById(int id);

    void updatePlacementPositionById(int id, PlacementPositionDto PlacementPositionDto);

    void deletePlacementPositionById(int id);

    ViewListPage<PlacementPositionViewDto> getViewListPage(String page, String size);

    List<PlacementPositionViewDto> listPlacementPositions(Pageable pageable);

    int numberOfPlacementPositions();
    
}
