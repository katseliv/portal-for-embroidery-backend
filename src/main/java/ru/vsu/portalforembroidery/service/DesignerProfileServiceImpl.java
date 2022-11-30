package ru.vsu.portalforembroidery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.portalforembroidery.exception.EntityAlreadyExistsException;
import ru.vsu.portalforembroidery.exception.EntityCreationException;
import ru.vsu.portalforembroidery.exception.EntityNotFoundException;
import ru.vsu.portalforembroidery.mapper.DesignerProfileMapper;
import ru.vsu.portalforembroidery.model.Provider;
import ru.vsu.portalforembroidery.model.dto.DesignerProfileDto;
import ru.vsu.portalforembroidery.model.dto.DesignerProfileRegistrationDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignerProfileViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.model.entity.DesignerProfileEntity;
import ru.vsu.portalforembroidery.repository.DesignerProfileRepository;
import ru.vsu.portalforembroidery.utils.ParseUtils;

import java.util.List;
import java.util.Optional;

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
    private final DesignerProfileMapper designerProfileMapper;

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
        final Optional<DesignerProfileEntity> designerProfileEntity = designerProfileRepository.findById(id);
        designerProfileEntity.ifPresentOrElse(
                (designerProfile) -> log.info("Designer Profile with id = {} has been found.", designerProfile.getId()),
                () -> {
                    log.warn("Designer Profile hasn't been found.");
                    throw new EntityNotFoundException("Designer Profile not found!");
                });
        Optional.of(designerProfileDto)
                .map(designerProfileMapper::designerProfileDtoToDesignerProfileEntity)
                .map((designerProfile) -> {
                    designerProfile.setId(id);
                    return designerProfileRepository.save(designerProfile);
                });
        log.info("Designer Profile with id = {} has been updated.", id);
    }

    // TODO: 30.11.2022 подумать над логикой удаления дизайнера
    @Override
    @Transactional
    public void deleteDesignerProfileById(int id) {
        final Optional<DesignerProfileEntity> designerProfileEntity = designerProfileRepository.findById(id);
        designerProfileEntity.ifPresentOrElse(
                (designerProfile) -> log.info("Designer Profile with id = {} has been found.", designerProfile.getId()),
                () -> {
                    log.warn("DesignerProfile hasn't been found.");
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
    @Transactional(readOnly = true)
    public List<DesignerProfileViewDto> listDesignerProfiles(Pageable pageable) {
        final List<DesignerProfileEntity> designerProfileEntities = designerProfileRepository.findAll(pageable).getContent();
        log.info("There have been found {} Designer Profiles.", designerProfileEntities.size());
        return designerProfileMapper.designerProfileEntitiesToDesignerProfileViewDtoList(designerProfileEntities);
    }

    @Override
    public int numberOfDesignerProfiles() {
        final long numberOfDesignerProfiles = designerProfileRepository.count();
        log.info("There have been found {} Designer Profiles.", numberOfDesignerProfiles);
        return (int) numberOfDesignerProfiles;
    }

}
