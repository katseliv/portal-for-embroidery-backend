package ru.vsu.portalforembroidery.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.portalforembroidery.model.dto.PlacementPositionDto;
import ru.vsu.portalforembroidery.model.dto.view.PlacementPositionViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.service.PlacementPositionService;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/placement-positions")
public class PlacementPositionController {

    private final PlacementPositionService placementPositionService;

    @PostMapping
    public ResponseEntity<Integer> createPlacementPosition(@RequestBody @Valid final PlacementPositionDto placementPositionDto) {
        final int id = placementPositionService.createPlacementPosition(placementPositionDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public PlacementPositionViewDto getPlacementPosition(@PathVariable final int id) {
        return placementPositionService.getPlacementPositionViewById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePlacementPosition(@PathVariable final int id, @RequestBody @Valid final PlacementPositionDto placementPositionDto) {
        placementPositionService.updatePlacementPositionById(id, placementPositionDto);
        return new ResponseEntity<>("Placement Position was updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePlacementPosition(@PathVariable final int id) {
        placementPositionService.deletePlacementPositionById(id);
    }

    @GetMapping
    public ViewListPage<PlacementPositionViewDto> getPlacementPositions(@RequestParam(required = false) final Map<String, String> allParams) {
        return placementPositionService.getViewListPage(allParams.get("page"), allParams.get("size"));
    }

}
