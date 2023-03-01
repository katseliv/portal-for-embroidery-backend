package ru.vsu.portalforembroidery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.portalforembroidery.exception.EntityCreationException;
import ru.vsu.portalforembroidery.exception.EntityNotFoundException;
import ru.vsu.portalforembroidery.mapper.DesignMapper;
import ru.vsu.portalforembroidery.model.dto.DesignDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.model.entity.DesignEntity;
import ru.vsu.portalforembroidery.model.entity.DesignerProfileEntity;
import ru.vsu.portalforembroidery.repository.DesignRepository;
import ru.vsu.portalforembroidery.repository.DesignerProfileRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DesignServiceImpl implements DesignService, PaginationService<DesignViewDto> {

    @Value("${pagination.defaultPageNumber}")
    private int defaultPageNumber;
    @Value("${pagination.defaultPageSize}")
    private int defaultPageSize;

    private final DesignRepository designRepository;
    private final DesignerProfileRepository designerProfileRepository;
    private final DesignMapper designMapper;

    @Override
    @Transactional
    public int createDesign(DesignDto designDto) {
        final Optional<DesignerProfileEntity> designerProfileEntity = designerProfileRepository.findById(designDto.getCreatorDesignerId());
        designerProfileEntity.ifPresentOrElse(
                (designerProfile) -> log.info("Designer Profile has been found."),
                () -> {
                    log.warn("Designer Profile hasn't been found.");
                    throw new EntityNotFoundException("Designer Profile not found!");
                }
        );
        final DesignEntity designEntity = Optional.of(designDto)
                .map(designMapper::designDtoToDesignEntity)
                .map(designRepository::save)
                .orElseThrow(() -> new EntityCreationException("Design hasn't been created!"));
        log.info("Design with id = {} has been created.", designEntity.getId());
        return designEntity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public DesignViewDto getDesignViewById(int id) {
        final Optional<DesignEntity> designEntity = designRepository.findById(id);
        designEntity.ifPresentOrElse(
                (design) -> log.info("Design with id = {} has been found.", design.getId()),
                () -> log.warn("Design hasn't been found."));
        return designEntity.map(designMapper::designEntityToDesignViewDto)
                .orElseThrow(() -> new EntityNotFoundException("Design not found!"));
    }

    @Override
    @Transactional
    public void updateDesignById(int id, DesignDto designDto) {
        final Optional<DesignEntity> designEntity = designRepository.findById(id);
        designEntity.ifPresentOrElse(
                (design) -> log.info("Design with id = {} has been found.", design.getId()),
                () -> {
                    log.warn("Design hasn't been found.");
                    throw new EntityNotFoundException("Design not found!");
                });
        final Optional<DesignerProfileEntity> designerProfileEntity = designerProfileRepository.findById(designDto.getCreatorDesignerId());
        designerProfileEntity.ifPresentOrElse(
                (designerProfile) -> log.info("Designer Profile has been found."),
                () -> {
                    log.warn("Designer Profile hasn't been found.");
                    throw new EntityNotFoundException("Designer Profile not found!");
                }
        );
        Optional.of(designDto)
                .map(designMapper::designDtoToDesignEntity)
                .map((design) -> {
                    design.setId(id);
                    return designRepository.save(design);
                });
        log.info("Design with id = {} has been updated.", id);
    }

    @Override
    @Transactional
    public void deleteDesignById(int id) {
        final Optional<DesignEntity> designEntity = designRepository.findById(id);
        designEntity.ifPresentOrElse(
                (design) -> log.info("Design with id = {} has been found.", design.getId()),
                () -> {
                    log.warn("Design hasn't been found.");
                    throw new EntityNotFoundException("Design not found!");
                });
        designRepository.deleteById(id);
        log.info("Design with id = {} has been deleted.", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<DesignViewDto> getViewListPage() {
        final List<DesignViewDto> listDesigns = listDesigns();
        final int totalAmount = listDesigns.size();

        return getViewListPage(totalAmount, totalAmount, 1, listDesigns);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DesignViewDto> listDesigns() {
        final List<DesignEntity> designEntities = designRepository.findAll();
        log.info("There have been found {} designs.", designEntities.size());
        return designMapper.designEntitiesToDesignViewDtoList(designEntities);
    }

    @Override
    public int numberOfDesigns() {
        final long numberOfDesigns = designRepository.count();
        log.info("There have been found {} designs.", numberOfDesigns);
        return (int) numberOfDesigns;
    }

}
