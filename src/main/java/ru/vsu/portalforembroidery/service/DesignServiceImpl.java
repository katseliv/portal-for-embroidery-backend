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
import ru.vsu.portalforembroidery.model.dto.DesignUpdateDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignForListDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignViewDto;
import ru.vsu.portalforembroidery.model.dto.view.FileForListDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.model.entity.DesignEntity;
import ru.vsu.portalforembroidery.model.entity.DesignerProfileEntity;
import ru.vsu.portalforembroidery.model.entity.FolderEntity;
import ru.vsu.portalforembroidery.repository.DesignRepository;
import ru.vsu.portalforembroidery.repository.DesignerProfileRepository;
import ru.vsu.portalforembroidery.repository.FolderRepository;
import ru.vsu.portalforembroidery.utils.ParseUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DesignServiceImpl implements DesignService, PaginationService<DesignForListDto> {

    @Value("${pagination.defaultPageNumber}")
    private int defaultPageNumber;
    @Value("${pagination.defaultPageSize}")
    private int defaultPageSize;

    private final FileService fileService;
    private final DesignRepository designRepository;
    private final FolderRepository folderRepository;
    private final DesignerProfileRepository designerProfileRepository;
    private final DesignMapper designMapper;

    @Override
    @Transactional
    public int createDesign(DesignDto designDto) {
        final FolderEntity folderEntity = getFolder(designDto);
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
                .map((design) -> {
                    design.setFolder(folderEntity);
                    designRepository.save(design);
                    return design;
                })
                .orElseThrow(() -> new EntityCreationException("Design hasn't been created!"));
        log.info("Design with id = {} has been created.", designEntity.getId());
        return designEntity.getId();
    }

    private FolderEntity getFolder(DesignDto designDto) {
        if (designDto.getFolderId() != null) {
            final Optional<FolderEntity> folderEntityOptional = folderRepository.findById(designDto.getFolderId());
            if (folderEntityOptional.isPresent()) {
                log.info("Folder has been found.");
                return folderEntityOptional.get();
            } else {
                log.warn("Folder hasn't been found.");
                throw new EntityNotFoundException("Folder not found!");
            }
        } else {
            return null;
        }
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
    public void updateDesignById(int id, DesignUpdateDto designUpdateDto) {
        final Optional<DesignEntity> designEntity = designRepository.findById(id);
        designEntity.ifPresentOrElse(
                (design) -> {
                    log.info("Design with id = {} has been found.", design.getId());
                    designMapper.mergeDesignEntityAndDesignUpdateDto(design, designUpdateDto);
                    designRepository.save(design);
                },
                () -> {
                    log.warn("Design hasn't been found.");
                    throw new EntityNotFoundException("Design not found!");
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
    public ViewListPage<DesignForListDto> getViewListPage(String page, String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<DesignForListDto> listDesigns = listDesigns(pageable);
        final int totalAmount = numberOfDesigns();

        return getViewListPage(totalAmount, pageSize, pageNumber, listDesigns);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<DesignForListDto> getViewListPage(int folderId, String page, String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<DesignForListDto> listDesigns = listDesignsByFolder(folderId, pageable);
        final int totalAmount = numberOfDesignsByFolder(folderId);

        return getViewListPage(totalAmount, pageSize, pageNumber, listDesigns);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<FileForListDto> getFileViewListPage(int id, String page, String size) {
        return fileService.getViewListPage(id, page, size);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DesignForListDto> listDesigns(Pageable pageable) {
        final List<DesignEntity> designEntities = designRepository.findAll(pageable).getContent();
        log.info("There have been found {} designs.", designEntities.size());
        return designMapper.designEntitiesToDesignForListDtoList(designEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DesignForListDto> listDesignsByFolder(int folderId, Pageable pageable) {
        final List<DesignEntity> designEntities = designRepository.findAllByFolderId(folderId, pageable).getContent();
        log.info("There have been found {} designs.", designEntities.size());
        return designMapper.designEntitiesToDesignForListDtoList(designEntities);
    }

    @Override
    public int numberOfDesigns() {
        final long numberOfDesigns = designRepository.count();
        log.info("There have been found {} designs.", numberOfDesigns);
        return (int) numberOfDesigns;
    }

    @Override
    public int numberOfDesignsByFolder(int folderId) {
        final long numberOfDesigns = designRepository.countByFolderId(folderId);
        log.info("There have been found {} designs.", numberOfDesigns);
        return (int) numberOfDesigns;
    }

}
