package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.model.dto.LikeDto;
import ru.vsu.portalforembroidery.model.dto.PostDto;
import ru.vsu.portalforembroidery.model.dto.PostUpdateDto;
import ru.vsu.portalforembroidery.model.dto.TagDto;
import ru.vsu.portalforembroidery.model.dto.view.*;

import java.util.List;

public interface PostService {

    int createPost(PostDto postDto);

    PostViewDto getPostViewById(int id);

    void updatePostById(int id, PostUpdateDto postUpdateDto);

    void updatePostByIdAndTags(int id, List<TagDto> tags);

    void deletePostById(int id);

    void likePostById(int id, LikeDto likeDto);

    void dislikePostById(int id, LikeDto likeDto);

    int countLikes(int id);

    ViewListPage<PostForListDto> getViewListPage(String page, String size);

    FilteredViewListPage<PostForListDto> getFilteredPostViewListPage(String page, String size, String tagName);

    ViewListPage<CommentViewDto> getViewListPageOfComments(int id, String page, String size);

    List<PostForListDto> listPosts(Pageable pageable);

    int numberOfPosts();

    List<PostForListDto> listPosts(Pageable pageable, String tagName);

    int numberOfFilteredPosts(String tagName);

}
