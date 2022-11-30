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
import ru.vsu.portalforembroidery.mapper.ModelPhotoMapper;
import ru.vsu.portalforembroidery.model.dto.ModelPhotoDto;
import ru.vsu.portalforembroidery.model.dto.view.ModelPhotoViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.model.entity.DesignEntity;
import ru.vsu.portalforembroidery.model.entity.ModelPhotoEntity;
import ru.vsu.portalforembroidery.model.entity.PlacementPositionEntity;
import ru.vsu.portalforembroidery.repository.DesignRepository;
import ru.vsu.portalforembroidery.repository.ModelPhotoRepository;
import ru.vsu.portalforembroidery.repository.PlacementPositionRepository;
import ru.vsu.portalforembroidery.utils.ParseUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ModelPhotoServiceImpl implements ModelPhotoService, PaginationService<ModelPhotoViewDto> {

    @Value("${pagination.defaultPageNumber}")
    private int defaultPageNumber;
    @Value("${pagination.defaultPageSize}")
    private int defaultPageSize;

    private final ModelPhotoRepository modelPhotoRepository;
    private final PlacementPositionRepository placementPositionRepository;
    private final DesignRepository designRepository;
    private final ModelPhotoMapper modelPhotoMapper;

    @Override
    @Transactional
    public int createModelPhoto(ModelPhotoDto modelPhotoDto) {
        final Optional<DesignEntity> designEntity = designRepository.findById(modelPhotoDto.getDesignId());
        designEntity.ifPresentOrElse(
                (design) -> log.info("Design has been found."),
                () -> {
                    log.warn("Design hasn't been found.");
                    throw new EntityNotFoundException("Design not found!");
                }
        );
        final Optional<PlacementPositionEntity> placementPositionEntity = placementPositionRepository.findById(modelPhotoDto.getPlacementPositionId());
        placementPositionEntity.ifPresentOrElse(
                (user) -> log.info("Placement Position has been found."),
                () -> {
                    log.warn("Placement Position hasn't been found.");
                    throw new EntityNotFoundException("User not found!");
                }
        );
        final ModelPhotoEntity modelPhotoEntity = Optional.of(modelPhotoDto)
                .map(modelPhotoMapper::modelPhotoDtoToModelPhotoEntity)
                .map(modelPhotoRepository::save)
                .orElseThrow(() -> new EntityCreationException("Model Photo hasn't been created!"));
        log.info("Model Photo with id = {} has been created.", modelPhotoEntity.getId());
        return modelPhotoEntity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public ModelPhotoViewDto getModelPhotoViewById(int id) {
        final Optional<ModelPhotoEntity> modelPhotoEntity = modelPhotoRepository.findById(id);
        modelPhotoEntity.ifPresentOrElse(
                (modelPhoto) -> log.info("Model Photo with id = {} has been found.", modelPhoto.getId()),
                () -> log.warn("Model Photo hasn't been found."));
        return modelPhotoEntity.map(modelPhotoMapper::modelPhotoEntityToModelPhotoViewDto)
                .orElseThrow(() -> new EntityNotFoundException("Model Photo not found!"));
    }

    @Override
    @Transactional
    public void updateModelPhotoById(int id, ModelPhotoDto modelPhotoDto) {
        final Optional<ModelPhotoEntity> modelPhotoEntity = modelPhotoRepository.findById(id);
        modelPhotoEntity.ifPresentOrElse(
                (modelPhoto) -> log.info("Model Photo with id = {} has been found.", modelPhoto.getId()),
                () -> {
                    log.warn("Model Photo hasn't been found.");
                    throw new EntityNotFoundException("Model Photo not found!");
                });
        Optional.of(modelPhotoDto)
                .map(modelPhotoMapper::modelPhotoDtoToModelPhotoEntity)
                .map((modelPhoto) -> {
                    modelPhoto.setId(id);
                    return modelPhotoRepository.save(modelPhoto);
                });
        log.info("Model Photo with id = {} has been updated.", id);
    }

    @Override
    @Transactional
    public void deleteModelPhotoById(int id) {
        final Optional<ModelPhotoEntity> modelPhotoEntity = modelPhotoRepository.findById(id);
        modelPhotoEntity.ifPresentOrElse(
                (modelPhoto) -> log.info("Model Photo with id = {} has been found.", modelPhoto.getId()),
                () -> {
                    log.warn("Model Photo hasn't been found.");
                    throw new EntityNotFoundException("ModelPhoto not found!");
                });
        modelPhotoRepository.deleteById(id);
        log.info("Model Photo with id = {} has been deleted.", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<ModelPhotoViewDto> getViewListPage(String page, String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<ModelPhotoViewDto> listModelPhotos = listModelPhotos(pageable);
        final int totalAmount = numberOfModelPhotos();

        return getViewListPage(totalAmount, pageSize, pageNumber, listModelPhotos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModelPhotoViewDto> listModelPhotos(Pageable pageable) {
        final List<ModelPhotoEntity> modelPhotoEntities = modelPhotoRepository.findAll(pageable).getContent();
        log.info("There have been found {} model photos.", modelPhotoEntities.size());
        return modelPhotoMapper.modelPhotoEntitiesToModelPhotoViewDtoList(modelPhotoEntities);
    }

    @Override
    public int numberOfModelPhotos() {
        final long numberOfModelPhotos = modelPhotoRepository.count();
        log.info("There have been found {} model photos.", numberOfModelPhotos);
        return (int) numberOfModelPhotos;
    }
    
}
