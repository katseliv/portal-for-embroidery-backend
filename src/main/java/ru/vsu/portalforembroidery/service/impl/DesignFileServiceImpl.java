package ru.vsu.portalforembroidery.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.portalforembroidery.exception.EntityCreationException;
import ru.vsu.portalforembroidery.exception.EntityNotFoundException;
import ru.vsu.portalforembroidery.mapper.DesignFileMapper;
import ru.vsu.portalforembroidery.model.dto.DesignFileDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignFileViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.model.entity.DesignEntity;
import ru.vsu.portalforembroidery.model.entity.DesignFileEntity;
import ru.vsu.portalforembroidery.model.entity.FileEntity;
import ru.vsu.portalforembroidery.repository.DesignFileRepository;
import ru.vsu.portalforembroidery.repository.DesignRepository;
import ru.vsu.portalforembroidery.repository.FileRepository;
import ru.vsu.portalforembroidery.service.DesignFileService;
import ru.vsu.portalforembroidery.service.PaginationService;
import ru.vsu.portalforembroidery.utils.ParseUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DesignFileServiceImpl implements DesignFileService, PaginationService<DesignFileViewDto> {

    @Value("${pagination.defaultPageNumber}")
    private int defaultPageNumber;
    @Value("${pagination.defaultPageSize}")
    private int defaultPageSize;

    private final DesignFileRepository designFileRepository;
    private final DesignRepository designRepository;
    private final FileRepository fileRepository;
    private final DesignFileMapper designFileMapper;

    @Override
    @Transactional
    public int createDesignFile(DesignFileDto designFileDto) {
        final Optional<DesignEntity> designEntity = designRepository.findById(designFileDto.getDesignId());
        designEntity.ifPresentOrElse(
                (design) -> log.info("Design has been found."),
                () -> {
                    log.warn("Design hasn't been found.");
                    throw new EntityNotFoundException("Design not found!");
                }
        );
        final Optional<FileEntity> fileEntity = fileRepository.findById(designFileDto.getFileId());
        fileEntity.ifPresentOrElse(
                (file) -> log.info("File has been found."),
                () -> {
                    log.warn("File hasn't been found.");
                    throw new EntityNotFoundException("File not found!");
                }
        );
        final DesignFileEntity DesignFileEntity = Optional.of(designFileDto)
                .map(designFileMapper::designFileDtoToDesignFileEntity)
                .map(designFileRepository::save)
                .orElseThrow(() -> new EntityCreationException("Design File hasn't been created!"));
        log.info("Design File with id = {} has been created.", DesignFileEntity.getId());
        return DesignFileEntity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public DesignFileViewDto getDesignFileViewById(int id) {
        final Optional<DesignFileEntity> designFileEntity = designFileRepository.findById(id);
        designFileEntity.ifPresentOrElse(
                (designFile) -> log.info("Design File with id = {} has been found.", designFile.getId()),
                () -> log.warn("Design File hasn't been found."));
        return designFileEntity.map(designFileMapper::designFileEntityToDesignFileViewDto)
                .orElseThrow(() -> new EntityNotFoundException("Design File not found!"));
    }

    @Override
    @Transactional
    public void updateDesignFileById(int id, DesignFileDto designFileDto) {
        final Optional<DesignFileEntity> designFileEntity = designFileRepository.findById(id);
        designFileEntity.ifPresentOrElse(
                (designFile) -> log.info("Design File with id = {} has been found.", designFile.getId()),
                () -> {
                    log.warn("Design File hasn't been found.");
                    throw new EntityNotFoundException("Design File not found!");
                });
        Optional.of(designFileDto)
                .map(designFileMapper::designFileDtoToDesignFileEntity)
                .map((DesignFile) -> {
                    DesignFile.setId(id);
                    return designFileRepository.save(DesignFile);
                });
        log.info("Design File with id = {} has been updated.", id);
    }

    @Override
    @Transactional
    public void deleteDesignFileById(int id) {
        final Optional<DesignFileEntity> designFileEntity = designFileRepository.findById(id);
        designFileEntity.ifPresentOrElse(
                (designFile) -> log.info("Design File with id = {} has been found.", designFile.getId()),
                () -> {
                    log.warn("Design File hasn't been found.");
                    throw new EntityNotFoundException("Design File not found!");
                });
        designFileRepository.deleteById(id);
        log.info("Design File with id = {} has been deleted.", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<DesignFileViewDto> getViewListPage(String page, String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<DesignFileViewDto> listDesignFiles = listDesignFiles(pageable);
        final int totalAmount = numberOfDesignFiles();

        return getViewListPage(totalAmount, pageSize, pageNumber, listDesignFiles);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DesignFileViewDto> listDesignFiles(Pageable pageable) {
        final List<DesignFileEntity> designFileEntities = designFileRepository.findAll(pageable).getContent();
        log.info("There have been found {} design-files.", designFileEntities.size());
        return designFileMapper.designFileEntitiesToDesignFileViewDtoList(designFileEntities);
    }

    @Override
    public int numberOfDesignFiles() {
        final long numberOfDesignFiles = designFileRepository.count();
        log.info("There have been found {} design-files.", numberOfDesignFiles);
        return (int) numberOfDesignFiles;
    }

}
