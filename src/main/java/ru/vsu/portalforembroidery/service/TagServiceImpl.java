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
import ru.vsu.portalforembroidery.mapper.TagMapper;
import ru.vsu.portalforembroidery.model.dto.TagDto;
import ru.vsu.portalforembroidery.model.dto.view.TagViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.model.entity.TagEntity;
import ru.vsu.portalforembroidery.repository.TagRepository;
import ru.vsu.portalforembroidery.utils.ParseUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService, PaginationService<TagViewDto> {

    @Value("${pagination.defaultPageNumber}")
    private int defaultPageNumber;
    @Value("${pagination.defaultPageSize}")
    private int defaultPageSize;

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    @Transactional
    public int createTag(TagDto tagDto) {
        final TagEntity tagEntity = Optional.of(tagDto)
                .map(tagMapper::tagDtoToTagEntity)
                .map(tagRepository::save)
                .orElseThrow(() -> new EntityCreationException("Tag hasn't been created!"));
        log.info("Tag with id = {} has been created.", tagEntity.getId());
        return tagEntity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public TagViewDto getTagViewById(int id) {
        final Optional<TagEntity> tagEntity = tagRepository.findById(id);
        tagEntity.ifPresentOrElse(
                (tag) -> log.info("Tag with id = {} has been found.", tag.getId()),
                () -> log.warn("Tag hasn't been found."));
        return tagEntity.map(tagMapper::tagEntityToTagViewDto)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found!"));
    }

    @Override
    @Transactional
    public void updateTagById(int id, TagDto tagDto) {
        final Optional<TagEntity> tagEntity = tagRepository.findById(id);
        tagEntity.ifPresentOrElse(
                (Tag) -> log.info("Tag with id = {} has been found.", Tag.getId()),
                () -> {
                    log.warn("Tag hasn't been found.");
                    throw new EntityNotFoundException("Tag not found!");
                });
        Optional.of(tagDto)
                .map(tagMapper::tagDtoToTagEntity)
                .map((tag) -> {
                    tag.setId(id);
                    return tagRepository.save(tag);
                });
        log.info("Tag with id = {} has been updated.", id);
    }

    @Override
    @Transactional
    public void deleteTagById(int id) {
        final Optional<TagEntity> TagEntity = tagRepository.findById(id);
        TagEntity.ifPresentOrElse(
                (tag) -> log.info("Tag with id = {} has been found.", tag.getId()),
                () -> {
                    log.warn("Tag hasn't been found.");
                    throw new EntityNotFoundException("Tag not found!");
                });
        tagRepository.deleteById(id);
        log.info("Tag with id = {} has been deleted.", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<TagViewDto> getViewListPage(String page, String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<TagViewDto> listTags = listTags(pageable);
        final int totalAmount = numberOfTags();

        return getViewListPage(totalAmount, pageSize, pageNumber, listTags);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagViewDto> listTags(Pageable pageable) {
        final List<TagEntity> tagEntities = tagRepository.findAll(pageable).getContent();
        log.info("There have been found {} Tags.", tagEntities.size());
        return tagMapper.tagEntitiesToTagViewDtoList(tagEntities);
    }

    @Override
    public int numberOfTags() {
        final long numberOfTags = tagRepository.count();
        log.info("There have been found {} tags.", numberOfTags);
        return (int) numberOfTags;
    }
    
}
