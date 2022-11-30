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
import ru.vsu.portalforembroidery.mapper.DesignMapper;
import ru.vsu.portalforembroidery.model.dto.DesignDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.model.entity.DesignEntity;
import ru.vsu.portalforembroidery.model.entity.FolderEntity;
import ru.vsu.portalforembroidery.model.entity.UserEntity;
import ru.vsu.portalforembroidery.repository.DesignRepository;
import ru.vsu.portalforembroidery.repository.FolderRepository;
import ru.vsu.portalforembroidery.repository.UserRepository;
import ru.vsu.portalforembroidery.utils.ParseUtils;

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
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;
    private final DesignMapper designMapper;

    @Override
    @Transactional
    public int createDesign(DesignDto designDto) {
        final Optional<FolderEntity> parentFolderEntity = folderRepository.findById(designDto.getFolderId());
        parentFolderEntity.ifPresentOrElse(
                (folder) -> log.info("Folder has been found."),
                () -> {
                    log.warn("Folder hasn't been found.");
                    throw new EntityNotFoundException("Folder not found!");
                }
        );
        final Optional<UserEntity> userEntity = userRepository.findById(designDto.getCreatorDesignerId());
        userEntity.ifPresentOrElse(
                (user) -> log.info("User has been found."),
                () -> {
                    log.warn("User hasn't been found.");
                    throw new EntityNotFoundException("User not found!");
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
    public ViewListPage<DesignViewDto> getViewListPage(String page, String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<DesignViewDto> listDesigns = listDesigns(pageable);
        final int totalAmount = numberOfDesigns();

        return getViewListPage(totalAmount, pageSize, pageNumber, listDesigns);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DesignViewDto> listDesigns(Pageable pageable) {
        final List<DesignEntity> designEntities = designRepository.findAll(pageable).getContent();
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
