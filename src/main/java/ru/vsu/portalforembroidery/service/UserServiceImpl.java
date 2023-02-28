package ru.vsu.portalforembroidery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.portalforembroidery.exception.EntityAlreadyExistsException;
import ru.vsu.portalforembroidery.exception.EntityCreationException;
import ru.vsu.portalforembroidery.exception.EntityNotFoundException;
import ru.vsu.portalforembroidery.mapper.PostMapper;
import ru.vsu.portalforembroidery.mapper.UserMapper;
import ru.vsu.portalforembroidery.model.Provider;
import ru.vsu.portalforembroidery.model.Role;
import ru.vsu.portalforembroidery.model.dto.UserDetailsDto;
import ru.vsu.portalforembroidery.model.dto.UserDto;
import ru.vsu.portalforembroidery.model.dto.UserRegistrationDto;
import ru.vsu.portalforembroidery.model.dto.view.*;
import ru.vsu.portalforembroidery.model.entity.PostEntity;
import ru.vsu.portalforembroidery.model.entity.UserEntity;
import ru.vsu.portalforembroidery.repository.PostRepository;
import ru.vsu.portalforembroidery.repository.UserRepository;
import ru.vsu.portalforembroidery.utils.ParseUtils;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, PaginationService<UserForListDto> {

    @Value("${pagination.defaultPageNumber}")
    private int defaultPageNumber;
    @Value("${pagination.defaultPageSize}")
    private int defaultPageSize;

    private final PostService postService;
    private final FolderService folderService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public int createUser(UserRegistrationDto userRegistrationDto, Provider provider) {
        if (userRepository.findByEmail(userRegistrationDto.getEmail()).isPresent()) {
            log.warn("User with email = {} hasn't been created. Such user already exists!", userRegistrationDto.getEmail());
            throw new EntityAlreadyExistsException("User already exists in the database!");
        }
        final String password = passwordEncoder.encode(userRegistrationDto.getPassword());
        final UserEntity userEntity = Optional.of(userRegistrationDto)
                .map(user -> userMapper.userRegistrationDtoToUserEntityWithPassword(user, password))
                .map(user -> {
                    user.setImage(new byte[0]);
                    user.setRole(Role.of(userRegistrationDto.getRoleId())
                            .orElseThrow(() -> new EntityCreationException("User not created!")));
                    user.setProvider(provider);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new EntityCreationException("User not created!"));
        log.info("User with id = {} has been created.", userEntity.getId());
        return userEntity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(int id) {
        final Optional<UserEntity> userEntity = userRepository.findById(id);
        userEntity.ifPresentOrElse(
                (user) -> log.info("User with id = {} has been found.", user.getId()),
                () -> log.warn("User with id = {} hasn't been found.", id));
        return userEntity.map(userMapper::userEntityToUserDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserViewDto getUserViewById(int id) {
        final Optional<UserEntity> userEntity = userRepository.findById(id);
        userEntity.ifPresentOrElse(
                (user) -> log.info("User with id = {} has been found.", user.getId()),
                () -> log.warn("User with id = {} hasn't been found.", id));
        return userEntity.map(userMapper::userEntityToUserViewDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    public UserDetailsDto getUserDetailsById(int id) {
        final Optional<UserEntity> userEntity = userRepository.findById(id);
        userEntity.ifPresentOrElse(
                (user) -> log.info("User with id = {} has been found.", user.getId()),
                () -> log.warn("User with id = {} hasn't been found.", id));
        return userEntity.map(userMapper::userEntityToUserDetailsDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    @Transactional(readOnly = true)
    public int getUserIdByEmail(String email) {
        final Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        userEntity.ifPresentOrElse(
                (user) -> log.info("User with email = {} has been found.", email),
                () -> log.warn("User with email = {} hasn't been found.", email));
        return userEntity.map(userMapper::userEntityToUserDetailsDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found!")).getId();
    }

    @Override
    public UserDetailsDto getUserByEmail(String email) {
        final Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        userEntity.ifPresentOrElse(
                (user) -> log.info("User for email = {} with id = {} has been found.", email, user.getId()),
                () -> log.warn("User for email = {} hasn't been found.", email));
        return userMapper.userEntityToUserDetailsDto(userEntity.orElseThrow(
                () -> new UsernameNotFoundException("No such user in the database!")));
    }

    @Override
    public boolean existsByUserEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            log.info("User with email = {} exists.", email);
            return true;
        } else {
            log.warn("User with email = {} doesn't exist.", email);
            return false;
        }
    }

    @Override
    @Transactional
    public void updateUserById(int id, UserDto userDto) {
        final UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        final String username = userDto.getUsername();
        if (!Objects.equals(username, userEntity.getUsername()) && userRepository.existsByUsername(username)) {
            log.warn("User with username = {} hasn't been updated. Such username already exists in the database!", username);
            throw new EntityAlreadyExistsException("Such username already exists!");
        }

        if (userDto.getBase64StringImage().isEmpty()) {
            userMapper.mergeUserEntityAndUserDtoWithoutPicture(userEntity, userDto);
            userRepository.save(userEntity);
            log.info("User with id = {} has been updated without picture.", id);
        } else {
            userMapper.mergeUserEntityAndUserDto(userEntity, userDto);
            userRepository.save(userEntity);
            log.info("User with id = {} has been updated with picture.", id);
        }
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        final Optional<UserEntity> userEntity = userRepository.findById(id);
        userEntity.ifPresentOrElse(
                (user) -> log.info("User with id = {} has been found.", id),
                () -> {
                    log.warn("User with id = {} hasn't been found.", id);
                    throw new EntityNotFoundException("User not found!");
                });
        userRepository.deleteById(id);
        log.info("User with id = {} has been deleted.", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<UserForListDto> getViewListPage(String page, String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<UserForListDto> listUsers = listUsers(pageable);
        final int totalAmount = numberOfUsers();

        return getViewListPage(totalAmount, pageSize, pageNumber, listUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<FolderViewDto> getFolderViewListPage(int id, String page, String size) {
        return folderService.getViewListPage(id, page, size);
    }

    @Override
    @Transactional(readOnly = true)
    public FilteredViewListPage<PostForListDto> getFilteredPostViewListPage(final int userId, final String page, final String size, final String tagName) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<PostForListDto> postForListDtoList = listPosts(userId, pageable, tagName);
        final int totalAmount = postService.numberOfFilteredPosts(tagName);

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
    public List<UserForListDto> listDesigners(Pageable pageable) {
        final List<UserEntity> userEntities = userRepository.findAllByRole(Role.DESIGNER, pageable);
        log.info("There have been found {} designers.", userEntities.size());
        return userMapper.userEntitiesToUserForListDtoList(userEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserForListDto> listUsers(Pageable pageable) {
        final List<UserEntity> userEntities = userRepository.findAll(pageable).getContent();
        log.info("There have been found {} users.", userEntities.size());
        return userMapper.userEntitiesToUserForListDtoList(userEntities);
    }

    @Override
    public int numberOfUsers() {
        final long numberOfUsers = userRepository.count();
        log.info("There have been found {} users.", numberOfUsers);
        return (int) numberOfUsers;
    }

    @Override
    public List<PostForListDto> listPosts(final int userId, final Pageable pageable, final String tagName) {
        final Specification<PostEntity> specification = PostRepository.hasTagName(tagName);
        final List<PostEntity> postEntities = postRepository.findAll(specification, pageable).getContent();
        log.info("There have been found {} posts.", postEntities.size());
        return postEntities.stream().map(post -> {
            boolean liked = post.getLikes().stream().anyMatch(like -> like.getId().getUserId() == userId && Objects.equals(like.getId().getPostId(), post.getId()));
            return postMapper.postEntityToPostForListDto(post, liked);
        }).toList();
    }

}

