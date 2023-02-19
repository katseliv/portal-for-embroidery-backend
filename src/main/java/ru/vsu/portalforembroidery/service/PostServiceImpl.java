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
import ru.vsu.portalforembroidery.mapper.PostMapper;
import ru.vsu.portalforembroidery.model.Role;
import ru.vsu.portalforembroidery.model.dto.LikeDto;
import ru.vsu.portalforembroidery.model.dto.PostDto;
import ru.vsu.portalforembroidery.model.dto.PostUpdateDto;
import ru.vsu.portalforembroidery.model.dto.view.CommentViewDto;
import ru.vsu.portalforembroidery.model.dto.view.PostForListDto;
import ru.vsu.portalforembroidery.model.dto.view.PostViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.model.entity.*;
import ru.vsu.portalforembroidery.repository.DesignRepository;
import ru.vsu.portalforembroidery.repository.LikeRepository;
import ru.vsu.portalforembroidery.repository.PostRepository;
import ru.vsu.portalforembroidery.repository.UserRepository;
import ru.vsu.portalforembroidery.utils.ParseUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService, PaginationService<PostForListDto> {

    @Value("${pagination.defaultPageNumber}")
    private int defaultPageNumber;
    @Value("${pagination.defaultPageSize}")
    private int defaultPageSize;

    private final CommentService commentService;
    private final UserRepository userRepository;
    private final DesignRepository designRepository;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    @Transactional
    public int createPost(PostDto postDto) {
        checkExistingOfDesignerAndDesign(postDto);
        final PostEntity postEntity = Optional.of(postDto)
                .map(postMapper::postDtoToPostEntity)
                .map(post -> {
                    post.setCreationDatetime(LocalDateTime.now());
                    return post;
                })
                .map(postRepository::save)
                .orElseThrow(() -> new EntityCreationException("Post hasn't been created!"));
        log.info("Post with id = {} has been created.", postEntity.getId());
        return postEntity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public PostViewDto getPostViewById(int id) {
        final Optional<PostEntity> postEntity = postRepository.findById(id);
        if (postEntity.isEmpty()) {
            log.warn("Post hasn't been found.");
            throw new EntityNotFoundException("Post not found!");
        }
        PostEntity post = postEntity.get();
        log.info("Post with id = {} has been found.", post.getId());
        return postMapper.postEntityToPostViewDto(post);
    }

    @Override
    @Transactional
    public void updatePostById(int id, PostUpdateDto postUpdateDto) {
        final Optional<PostEntity> postEntity = postRepository.findById(id);
        postEntity.ifPresentOrElse(
                (post) -> {
                    log.info("Post with id = {} has been found.", post.getId());
                    postMapper.mergePostEntityAndPostUpdateDto(post, postUpdateDto);
                    postRepository.save(post);
                },
                () -> {
                    log.warn("Post hasn't been found.");
                    throw new EntityNotFoundException("Post not found!");
                });
        log.info("Post with id = {} has been updated.", id);
    }

    private void checkExistingOfDesignerAndDesign(PostDto postDto) {
        final Optional<UserEntity> userEntity = userRepository.findById(postDto.getDesignerId());
        userEntity.ifPresentOrElse(
                (user) -> {
                    if (user.getRole() != Role.DESIGNER) {
                        log.warn("User hasn't been created.");
                        throw new EntityCreationException("User doesn't have role DESIGNER!");
                    }
                    log.info("User has been found.");
                },
                () -> {
                    log.warn("User hasn't been found.");
                    throw new EntityNotFoundException("User not found!");
                }
        );
        final Optional<DesignEntity> designEntity = designRepository.findById(postDto.getDesignId());
        designEntity.ifPresentOrElse(
                (design) -> log.info("Design with id = {} has been found.", design.getId()),
                () -> {
                    log.warn("Design hasn't been found.");
                    throw new EntityNotFoundException("Design not found!");
                });
    }

    @Override
    @Transactional
    public void deletePostById(int id) {
        final Optional<PostEntity> postEntity = postRepository.findById(id);
        postEntity.ifPresentOrElse(
                (post) -> log.info("Post with id = {} has been found.", post.getId()),
                () -> {
                    log.warn("Post hasn't been found.");
                    throw new EntityNotFoundException("Post not found!");
                });
        likeRepository.deleteAllByPostId(id);
        postRepository.deleteById(id);
        log.info("Post with id = {} has been deleted.", id);
    }

    @Override
    @Transactional
    public void likePostById(int id, LikeDto likeDto) {
        checkExistingOfPostAndUserEntities(id, likeDto);
        final LikeId likeId = LikeId.builder().postId(id).userId(likeDto.getUserId()).build();
        final Optional<LikeEntity> likeEntityOptional = likeRepository.findById(likeId);
        likeEntityOptional.ifPresentOrElse(
                (like) -> {
                    log.info("Like with userId = {} и postId = {} has been found.", like.getId().getUserId(), like.getId().getPostId());
                    likeRepository.markAsNotDeletedById(likeId);
                    log.info("Like with userId = {} и postId = {} has been recovered.", likeId.getUserId(), likeId.getPostId());
                },
                () -> {
                    log.warn("Like hasn't been found.");
                    final LikeEntity likeEntity = LikeEntity.builder().id(likeId).build();
                    likeRepository.save(likeEntity);
                }
        );
    }

    @Override
    @Transactional
    public void dislikePostById(int id, LikeDto likeDto) {
        checkExistingOfPostAndUserEntities(id, likeDto);
        final LikeId likeId = LikeId.builder().postId(id).userId(likeDto.getUserId()).build();
        final Optional<LikeEntity> likeEntityOptional = likeRepository.findById(likeId);
        likeEntityOptional.ifPresentOrElse(
                (like) -> {
                    log.info("Like with userId = {} и postId = {} has been found.", like.getId().getUserId(), like.getId().getPostId());
                    likeRepository.markAsDeletedById(likeId);
                    log.info("Like with userId = {} и postId = {} has been deleted.", likeId.getUserId(), likeId.getPostId());
                },
                () -> {
                    log.warn("Like hasn't been found.");
                    final LikeEntity likeEntity = LikeEntity.builder().id(likeId).deleted(true).build();
                    likeRepository.save(likeEntity);
                }
        );
    }

    private void checkExistingOfPostAndUserEntities(int id, LikeDto likeDto) {
        final Optional<PostEntity> postEntity = postRepository.findById(id);
        postEntity.ifPresentOrElse(
                (post) -> log.info("Post with id = {} has been found.", post.getId()),
                () -> {
                    log.warn("Post hasn't been found.");
                    throw new EntityNotFoundException("Post not found!");
                });
        final Optional<UserEntity> userEntity = userRepository.findById(likeDto.getUserId());
        userEntity.ifPresentOrElse(
                (user) -> log.info("User has been found."),
                () -> {
                    log.warn("User hasn't been found.");
                    throw new EntityNotFoundException("User not found!");
                }
        );
    }

    @Override
    @Transactional(readOnly = true)
    public int countLikes(int id) {
        return likeRepository.countByPostId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<PostForListDto> getViewListPage(String page, String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<PostForListDto> listPosts = listPosts(pageable);
        final int totalAmount = numberOfPosts();

        return getViewListPage(totalAmount, pageSize, pageNumber, listPosts);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewListPage<CommentViewDto> getViewListPageOfComments(int id, String page, String size) {
        return commentService.getViewListPage(id, page, size);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostForListDto> listPosts(Pageable pageable) {
        final List<PostEntity> postEntities = postRepository.findAll(pageable).getContent();
        log.info("There have been found {} posts.", postEntities.size());
        return postMapper.postEntitiesToPostViewDtoList(postEntities);
    }

    @Override
    public int numberOfPosts() {
        final int numberOfPosts = (int) postRepository.count();
        log.info("There have been found {} posts.", numberOfPosts);
        return numberOfPosts;
    }

}
