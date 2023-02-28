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
import ru.vsu.portalforembroidery.mapper.FolderMapper;
import ru.vsu.portalforembroidery.model.dto.FolderDto;
import ru.vsu.portalforembroidery.model.dto.FolderUpdateDto;
import ru.vsu.portalforembroidery.model.dto.view.FileForListDto;
import ru.vsu.portalforembroidery.model.dto.view.FolderViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.model.entity.DesignerProfileEntity;
import ru.vsu.portalforembroidery.model.entity.FolderEntity;
import ru.vsu.portalforembroidery.repository.DesignerProfileRepository;
import ru.vsu.portalforembroidery.repository.FolderRepository;
import ru.vsu.portalforembroidery.utils.ParseUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FolderServiceImpl implements FolderService, PaginationService<FolderViewDto> {

    @Value("${pagination.defaultPageNumber}")
    private int defaultPageNumber;
    @Value("${pagination.defaultPageSize}")
    private int defaultPageSize;

    private final FileService fileService;
    private final FolderRepository folderRepository;
    private final DesignerProfileRepository designerProfileRepository;
    private final FolderMapper folderMapper;

    @Override
    @Transactional
    public int createFolder(FolderDto folderDto) {
        checkExistingDesignerProfile(folderDto);
        final FolderEntity parentFolderEntity = getParentFolder(folderDto);
        final FolderEntity folderEntity = Optional.of(folderDto)
                .map(folderMapper::folderDtoToFolderEntity)
                .map((folder) -> {
                    folder.setParentFolder(parentFolderEntity);
                    folderRepository.save(folder);
                    return folder;
                })
                .orElseThrow(() -> new EntityCreationException("Folder hasn't been created!"));
        log.info("Folder with id = {} has been created.", folderEntity.getId());
        return folderEntity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public FolderViewDto getFolderViewById(int id) {
        final Optional<FolderEntity> folderEntity = folderRepository.findById(id);
        folderEntity.ifPresentOrElse(
                (folder) -> log.info("Folder with id = {} has been found.", folder.getId()),
                () -> log.warn("Folder hasn't been found."));
        return folderEntity.map(folderMapper::folderEntityToFolderViewDto)
                .orElseThrow(() -> new EntityNotFoundException("Folder not found!"));
    }

    @Override
    @Transactional
    public void updateFolderById(int id, FolderUpdateDto folderUpdateDto) {
        final Optional<FolderEntity> folderEntity = folderRepository.findById(id);
        folderEntity.ifPresentOrElse(
                (folder) -> {
                    log.info("Folder with id = {} has been found.", folder.getId());
                    folderMapper.mergeFolderEntityAndFolderUpdateDto(folder, folderUpdateDto);
                    folderRepository.save(folder);
                },
                () -> {
                    log.warn("Folder hasn't been found.");
                    throw new EntityNotFoundException("Folder not found!");
                });
        log.info("Folder with id = {} has been updated.", id);
    }

    private void checkExistingDesignerProfile(FolderDto folderDto) {
        final Optional<DesignerProfileEntity> designerProfileEntity = designerProfileRepository.findById(folderDto.getCreatorDesignerId());
        designerProfileEntity.ifPresentOrElse(
                (designerProfile) -> log.info("Designer Profile has been found."),
                () -> {
                    log.warn("Designer Profile hasn't been found.");
                    throw new EntityNotFoundException("Designer Profile not found!");
                }
        );
    }

    private FolderEntity getParentFolder(FolderDto folderDto) {
        if (folderDto.getParentFolderId() != null) {
            final Optional<FolderEntity> parentFolderEntityOptional = folderRepository.findById(folderDto.getParentFolderId());
            if (parentFolderEntityOptional.isPresent()) {
                log.info("Parent Folder has been found.");
                return parentFolderEntityOptional.get();
            } else {
                log.warn("Parent Folder hasn't been found.");
                throw new EntityNotFoundException("Parent Folder not found!");
            }
        } else {
            return null;
        }
    }

    // TODO: 30.11.2022 подумать над логикой удалений папки и дизайнами этой папки
    @Override
    @Transactional
    public void deleteFolderById(int id) {
        final Optional<FolderEntity> folderEntity = folderRepository.findById(id);
        folderEntity.ifPresentOrElse(
                (folder) -> log.info("Folder with id = {} has been found.", folder.getId()),
                () -> {
                    log.warn("Folder hasn't been found.");
                    throw new EntityNotFoundException("Folder not found!");
                });
        folderRepository.deleteById(id);
        log.info("Folder with id = {} has been deleted.", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<FolderViewDto> getViewListPage(String page, String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<FolderViewDto> listFolders = listFolders(pageable);
        final int totalAmount = numberOfFolders();

        return getViewListPage(totalAmount, pageSize, pageNumber, listFolders);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<FolderViewDto> getViewListPage(int userId, String page, String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<FolderViewDto> listFolders = listFoldersByUser(userId, pageable);
        final int totalAmount = numberOfFoldersByUser(userId);

        return getViewListPage(totalAmount, pageSize, pageNumber, listFolders);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<FolderViewDto> getChildrenFolderViewListPage(int folderId, String page, String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        Optional<FolderEntity> optionalFolderEntity = folderRepository.findById(folderId);
        FolderEntity folderEntity = optionalFolderEntity.orElseThrow(() -> new EntityNotFoundException("Folder not found!"));
        List<FolderEntity> children = folderEntity.getChildren().stream().toList();

        final List<FolderViewDto> listFolders = folderMapper.folderEntitiesToFolderViewDtoList(children);
        final int totalAmount = children.size();

        return getViewListPage(totalAmount, pageSize, pageNumber, listFolders);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<FileForListDto> getFileViewListPage(int id, String page, String size) {
        return fileService.getViewListPage(id, page, size);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FolderViewDto> listFolders(Pageable pageable) {
        final List<FolderEntity> folderEntities = folderRepository.findAll(pageable).getContent();
        log.info("There have been found {} folders.", folderEntities.size());
        return folderMapper.folderEntitiesToFolderViewDtoList(folderEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FolderViewDto> listFoldersByUser(int userId, Pageable pageable) {
        final List<FolderEntity> folderEntities = folderRepository.findAllByCreatorDesignerIdAndParentFolderIdIsNull(userId, pageable).getContent();
        log.info("There have been found {} folders.", folderEntities.size());
        return folderMapper.folderEntitiesToFolderViewDtoList(folderEntities);
    }

    @Override
    public int numberOfFolders() {
        final long numberOfFolders = folderRepository.count();
        log.info("There have been found {} folders.", numberOfFolders);
        return (int) numberOfFolders;
    }

    @Override
    public int numberOfFoldersByUser(int userId) {
        final long numberOfFolders = folderRepository.countByCreatorDesignerId(userId);
        log.info("There have been found {} folders.", numberOfFolders);
        return (int) numberOfFolders;
    }

}
