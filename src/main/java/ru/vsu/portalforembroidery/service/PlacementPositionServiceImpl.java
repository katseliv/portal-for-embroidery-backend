package ru.vsu.portalforembroidery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.portalforembroidery.exception.EntityCreationException;
import ru.vsu.portalforembroidery.exception.EntityNotFoundException;
import ru.vsu.portalforembroidery.mapper.PlacementPositionMapper;
import ru.vsu.portalforembroidery.model.dto.PlacementPositionDto;
import ru.vsu.portalforembroidery.model.dto.view.PlacementPositionViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.model.entity.PlacementPositionEntity;
import ru.vsu.portalforembroidery.repository.PlacementPositionRepository;
import ru.vsu.portalforembroidery.utils.ParseUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlacementPositionServiceImpl implements PlacementPositionService, PaginationService<PlacementPositionViewDto> {

    @Value("${pagination.defaultPageNumber}")
    private int defaultPageNumber;
    @Value("${pagination.defaultPageSize}")
    private int defaultPageSize;

    private final PlacementPositionRepository placementPositionRepository;
    private final PlacementPositionMapper placementPositionMapper;

    @Override
    @Transactional
    public int createPlacementPosition(PlacementPositionDto placementPositionDto) {
        final PlacementPositionEntity placementPositionEntity = Optional.of(placementPositionDto)
                .map(placementPositionMapper::placementPositionDtoToPlacementPositionEntity)
                .map(placementPositionRepository::save)
                .orElseThrow(() -> new EntityCreationException("Placement Position hasn't been created!"));
        log.info("Placement Position with id = {} has been created.", placementPositionEntity.getId());
        return placementPositionEntity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public PlacementPositionViewDto getPlacementPositionViewById(int id) {
        final Optional<PlacementPositionEntity> placementPositionEntity = placementPositionRepository.findById(id);
        placementPositionEntity.ifPresentOrElse(
                (placementPosition) -> log.info("Placement Position with id = {} has been found.", placementPosition.getId()),
                () -> log.warn("Placement Position hasn't been found."));
        return placementPositionEntity.map(placementPositionMapper::placementPositionEntityToPlacementPositionViewDto)
                .orElseThrow(() -> new EntityNotFoundException("Placement Position not found!"));
    }

    @Override
    @Transactional
    public void updatePlacementPositionById(int id, PlacementPositionDto placementPositionDto) {
        final Optional<PlacementPositionEntity> placementPositionEntity = placementPositionRepository.findById(id);
        placementPositionEntity.ifPresentOrElse(
                (placementPosition) -> log.info("Placement Position with id = {} has been found.", placementPosition.getId()),
                () -> {
                    log.warn("Placement Position hasn't been found.");
                    throw new EntityNotFoundException("Placement Position not found!");
                });
        Optional.of(placementPositionDto)
                .map(placementPositionMapper::placementPositionDtoToPlacementPositionEntity)
                .map((placementPosition) -> {
                    placementPosition.setId(id);
                    return placementPositionRepository.save(placementPosition);
                });
        log.info("Placement Position with id = {} has been updated.", id);
    }

    @Override
    @Transactional
    public void deletePlacementPositionById(int id) {
        final Optional<PlacementPositionEntity> placementPositionEntity = placementPositionRepository.findById(id);
        placementPositionEntity.ifPresentOrElse(
                (placementPosition) -> log.info("Placement Position with id = {} has been found.", placementPosition.getId()),
                () -> {
                    log.warn("Placement Position hasn't been found.");
                    throw new EntityNotFoundException("Placement Position not found!");
                });
        placementPositionRepository.deleteById(id);
        log.info("Placement Position with id = {} has been deleted.", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<PlacementPositionViewDto> getViewListPage(String page, String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<PlacementPositionViewDto> listPlacementPositions = listPlacementPositions(pageable);
        final int totalAmount = numberOfPlacementPositions();

        return getViewListPage(totalAmount, pageSize, pageNumber, listPlacementPositions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlacementPositionViewDto> listPlacementPositions(Pageable pageable) {
        final List<PlacementPositionEntity> placementPositionEntities = placementPositionRepository.findAll(pageable).getContent();
        log.info("There have been found {} placement positions.", placementPositionEntities.size());
        return placementPositionMapper.placementPositionEntitiesToPlacementPositionViewDtoList(placementPositionEntities);
    }

    @Override
    public int numberOfPlacementPositions() {
        final long numberOfPlacementPositions = placementPositionRepository.count();
        log.info("There have been found {} placement positions.", numberOfPlacementPositions);
        return (int) numberOfPlacementPositions;
    }

}
