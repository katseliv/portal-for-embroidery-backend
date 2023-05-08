package ru.vsu.portalforembroidery.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.portalforembroidery.exception.EntityAlreadyExistsException;
import ru.vsu.portalforembroidery.exception.EntityCreationException;
import ru.vsu.portalforembroidery.exception.EntityNotFoundException;
import ru.vsu.portalforembroidery.mapper.DesignerProfileMapper;
import ru.vsu.portalforembroidery.mapper.PostMapper;
import ru.vsu.portalforembroidery.model.Provider;
import ru.vsu.portalforembroidery.model.Role;
import ru.vsu.portalforembroidery.model.dto.DesignerProfileDto;
import ru.vsu.portalforembroidery.model.dto.DesignerProfileRegistrationDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignerProfileViewDto;
import ru.vsu.portalforembroidery.model.dto.view.FilteredViewListPage;
import ru.vsu.portalforembroidery.model.dto.view.PostForListDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.model.entity.DesignerProfileEntity;
import ru.vsu.portalforembroidery.model.entity.PostEntity;
import ru.vsu.portalforembroidery.repository.DesignerProfileRepository;
import ru.vsu.portalforembroidery.repository.PostRepository;
import ru.vsu.portalforembroidery.service.DesignerProfileService;
import ru.vsu.portalforembroidery.service.PaginationService;
import ru.vsu.portalforembroidery.utils.ParseUtils;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class DesignerProfileServiceImpl implements DesignerProfileService, PaginationService<DesignerProfileViewDto> {

    @Value("${pagination.defaultPageNumber}")
    private int defaultPageNumber;
    @Value("${pagination.defaultPageSize}")
    private int defaultPageSize;

    private final PasswordEncoder passwordEncoder;
    private final DesignerProfileRepository designerProfileRepository;
    private final PostRepository postRepository;
    private final DesignerProfileMapper designerProfileMapper;
    private final PostMapper postMapper;

    // TODO: 30.11.2022 подумать над логикой создания дизайнера
    @Override
    @Transactional
    public int createDesignerProfile(DesignerProfileRegistrationDto designerProfileRegistrationDto, Provider provider) {
        if (designerProfileRepository.findByEmail(designerProfileRegistrationDto.getEmail()).isPresent()) {
            log.warn("Designer Profile with email = {} hasn't been created. Such user already exists!", designerProfileRegistrationDto.getEmail());
            throw new EntityAlreadyExistsException("Designer Profile already exists in the database!");
        }
        final String password = passwordEncoder.encode(designerProfileRegistrationDto.getPassword());
        final DesignerProfileEntity designerProfileEntity = Optional.of(designerProfileRegistrationDto)
                .map(designerProfile -> designerProfileMapper.designerRegistrationProfileViewDtoToDesignerProfileEntityWithPassword(designerProfile, password))
                .map(designerProfile -> {
                    designerProfile.setImage(new byte[0]);
                    designerProfile.setRole(Role.DESIGNER);
                    designerProfile.setProvider(provider);
                    return designerProfileRepository.save(designerProfile);
                })
                .orElseThrow(() -> new EntityCreationException("Designer Profile not created!"));
        log.info("Designer Profile with id = {} has been created.", designerProfileEntity.getId());
        return designerProfileEntity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public DesignerProfileViewDto getDesignerProfileViewById(int id) {
        final Optional<DesignerProfileEntity> designerProfileEntity = designerProfileRepository.findById(id);
        designerProfileEntity.ifPresentOrElse(
                (designerProfile) -> log.info("Designer Profile with id = {} has been found.", designerProfile.getId()),
                () -> log.warn("Designer Profile hasn't been found."));
        return designerProfileEntity.map(designerProfileMapper::designerProfileEntityToDesignerProfileViewDto)
                .orElseThrow(() -> new EntityNotFoundException("Designer Profile not found!"));
    }

    @Override
    @Transactional
    public void updateDesignerProfileById(int id, DesignerProfileDto designerProfileDto) {
        final DesignerProfileEntity designerProfileEntity = designerProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Designer Profile not found!"));

        final String username = designerProfileDto.getUsername();
        if (!Objects.equals(username, designerProfileEntity.getUsername()) && designerProfileRepository.existsByUsername(username)) {
            log.warn("Designer Profile with username = {} hasn't been updated. Such username already exists in the database!", username);
            throw new EntityAlreadyExistsException("Such username already exists!");
        }

        if (designerProfileDto.getBase64StringImage().isEmpty()) {
            designerProfileMapper.mergeDesignerProfileEntityAndDesignerProfileDtoWithoutPicture(designerProfileEntity, designerProfileDto);
            designerProfileRepository.save(designerProfileEntity);
            log.info("Designer Profile with id = {} has been updated without picture.", id);
        } else {
            designerProfileMapper.mergeDesignerProfileEntityAndDesignerProfileDto(designerProfileEntity, designerProfileDto);
            designerProfileRepository.save(designerProfileEntity);
            log.info("Designer Profile with id = {} has been updated with picture.", id);
        }
    }

    // TODO: 30.11.2022 подумать над логикой удаления дизайнера
    @Override
    @Transactional
    public void deleteDesignerProfileById(int id) {
        final Optional<DesignerProfileEntity> designerProfileEntity = designerProfileRepository.findById(id);
        designerProfileEntity.ifPresentOrElse(
                (designerProfile) -> log.info("Designer Profile with id = {} has been found.", designerProfile.getId()),
                () -> {
                    log.warn("Designer Profile hasn't been found.");
                    throw new EntityNotFoundException("Designer Profile not found!");
                });
        designerProfileRepository.deleteById(id);
        log.info("Designer Profile with id = {} has been deleted.", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<DesignerProfileViewDto> getViewListPage(String page, String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<DesignerProfileViewDto> listDesignerProfiles = listDesignerProfiles(pageable);
        final int totalAmount = numberOfDesignerProfiles();

        return getViewListPage(totalAmount, pageSize, pageNumber, listDesignerProfiles);
    }

    @Override
    public FilteredViewListPage<PostForListDto> getFilteredPostViewListPage(int designerId, String page, String size, String tagName) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<PostForListDto> postForListDtoList = listPosts(designerId, pageable, tagName);
        final int totalAmount = postForListDtoList.size();

        final Map<String, String> filterParameters = new HashMap<>();
        filterParameters.put("tagName", tagName);

        final int totalPages = (int) Math.ceil((double) totalAmount / pageSize);
        return FilteredViewListPage.<PostForListDto>builder()
                .filterParameters(filterParameters)
                .pageSize(pageSize)
                .totalPages(totalPages)
                .totalCount(totalAmount)
                .pageNumber(pageNumber)
                .viewDtoList(postForListDtoList)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DesignerProfileViewDto> listDesignerProfiles(Pageable pageable) {
        final List<DesignerProfileEntity> designerProfileEntities = designerProfileRepository.findAll(pageable).getContent();
        log.info("There have been found {} designer-profiles.", designerProfileEntities.size());
        return designerProfileMapper.designerProfileEntitiesToDesignerProfileViewDtoList(designerProfileEntities);
    }

    @Override
    public int numberOfDesignerProfiles() {
        final long numberOfDesignerProfiles = designerProfileRepository.count();
        log.info("There have been found {} designer-profiles.", numberOfDesignerProfiles);
        return (int) numberOfDesignerProfiles;
    }

    @Override
    public List<PostForListDto> listPosts(final int designerId, final Pageable pageable, final String tagName) {
        final Specification<PostEntity> specification = PostRepository.hasDesignerIdAndTagName(designerId, tagName);
        final List<PostEntity> postEntities = postRepository.findAll(specification, pageable).getContent();
        log.info("There have been found {} designer posts.", postEntities.size());
        return postEntities.stream().map(post -> {
            boolean liked = post.getLikes().stream().anyMatch(like -> like.getId().getUserId() == designerId && Objects.equals(like.getId().getPostId(), post.getId()));
            return postMapper.postEntityToPostForListDto(post, liked);
        }).toList();
    }

}
