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
import ru.vsu.portalforembroidery.mapper.DesignerDesignMapper;
import ru.vsu.portalforembroidery.model.dto.DesignerDesignDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignerDesignViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.model.entity.DesignEntity;
import ru.vsu.portalforembroidery.model.entity.DesignerDesignEntity;
import ru.vsu.portalforembroidery.model.entity.UserEntity;
import ru.vsu.portalforembroidery.repository.DesignRepository;
import ru.vsu.portalforembroidery.repository.DesignerDesignRepository;
import ru.vsu.portalforembroidery.repository.UserRepository;
import ru.vsu.portalforembroidery.utils.ParseUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DesignerDesignServiceImpl implements DesignerDesignService, PaginationService<DesignerDesignViewDto> {

    @Value("${pagination.defaultPageNumber}")
    private int defaultPageNumber;
    @Value("${pagination.defaultPageSize}")
    private int defaultPageSize;

    private final DesignerDesignRepository designerDesignRepository;
    private final UserRepository userRepository;
    private final DesignRepository designRepository;
    private final DesignerDesignMapper designerDesignMapper;

    @Override
    @Transactional
    public int createDesignerDesign(DesignerDesignDto designerDesignDto) {
        final Optional<UserEntity> designerEntity = userRepository.findById(designerDesignDto.getDesignerId());
        designerEntity.ifPresentOrElse(
                (designer) -> log.info("Designer has been found."),
                () -> {
                    log.warn("Designer hasn't been found.");
                    throw new EntityNotFoundException("Designer not found!");
                }
        );
        final Optional<DesignEntity> designEntity = designRepository.findById(designerDesignDto.getDesignId());
        designEntity.ifPresentOrElse(
                (design) -> log.info("Design has been found."),
                () -> {
                    log.warn("Design hasn't been found.");
                    throw new EntityNotFoundException("Design not found!");
                }
        );
        final DesignerDesignEntity designerDesignEntity = Optional.of(designerDesignDto)
                .map(designerDesignMapper::designerDesignDtoToDesignerDesignEntity)
                .map(designerDesignRepository::save)
                .orElseThrow(() -> new EntityCreationException("Designer Design hasn't been created!"));
        log.info("Designer Design with id = {} has been created.", designerDesignEntity.getId());
        return designerDesignEntity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public DesignerDesignViewDto getDesignerDesignViewById(int id) {
        final Optional<DesignerDesignEntity> designerDesignEntity = designerDesignRepository.findById(id);
        designerDesignEntity.ifPresentOrElse(
                (designerDesign) -> log.info("Designer Design with id = {} has been found.", designerDesign.getId()),
                () -> log.warn("Designer Design hasn't been found."));
        return designerDesignEntity.map(designerDesignMapper::designerDesignEntityToDesignerDesignViewDto)
                .orElseThrow(() -> new EntityNotFoundException("Designer Design not found!"));
    }

    @Override
    @Transactional
    public void updateDesignerDesignById(int id, DesignerDesignDto designerDesignDto) {
        final Optional<DesignerDesignEntity> designerDesignEntity = designerDesignRepository.findById(id);
        designerDesignEntity.ifPresentOrElse(
                (designerDesign) -> log.info("Designer Design with id = {} has been found.", designerDesign.getId()),
                () -> {
                    log.warn("Designer Design hasn't been found.");
                    throw new EntityNotFoundException("Designer Design not found!");
                });
        Optional.of(designerDesignDto)
                .map(designerDesignMapper::designerDesignDtoToDesignerDesignEntity)
                .map((designerDesign) -> {
                    designerDesign.setId(id);
                    return designerDesignRepository.save(designerDesign);
                });
        log.info("Designer Design with id = {} has been updated.", id);
    }

    @Override
    @Transactional
    public void deleteDesignerDesignById(int id) {
        final Optional<DesignerDesignEntity> designerDesignEntity = designerDesignRepository.findById(id);
        designerDesignEntity.ifPresentOrElse(
                (designerDesign) -> log.info("Designer Design with id = {} has been found.", designerDesign.getId()),
                () -> {
                    log.warn("Designer Design hasn't been found.");
                    throw new EntityNotFoundException("Designer Design not found!");
                });
        designerDesignRepository.deleteById(id);
        log.info("Designer Design with id = {} has been deleted.", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<DesignerDesignViewDto> getViewListPage(String page, String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<DesignerDesignViewDto> listDesignerDesigns = listDesignerDesigns(pageable);
        final int totalAmount = numberOfDesignerDesigns();

        return getViewListPage(totalAmount, pageSize, pageNumber, listDesignerDesigns);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DesignerDesignViewDto> listDesignerDesigns(Pageable pageable) {
        final List<DesignerDesignEntity> designerDesignEntities = designerDesignRepository.findAll(pageable).getContent();
        log.info("There have been found {} designer-designs.", designerDesignEntities.size());
        return designerDesignMapper.designerDesignEntitiesToDesignerDesignViewDtoList(designerDesignEntities);
    }

    @Override
    public int numberOfDesignerDesigns() {
        final long numberOfDesignerDesigns = designerDesignRepository.count();
        log.info("There have been found {} designer-designs.", numberOfDesignerDesigns);
        return (int) numberOfDesignerDesigns;
    }

}
