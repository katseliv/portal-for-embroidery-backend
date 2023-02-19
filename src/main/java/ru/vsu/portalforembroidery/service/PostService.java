package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.model.dto.LikeDto;
import ru.vsu.portalforembroidery.model.dto.PostDto;
import ru.vsu.portalforembroidery.model.dto.PostUpdateDto;
import ru.vsu.portalforembroidery.model.dto.view.CommentViewDto;
import ru.vsu.portalforembroidery.model.dto.view.PostForListDto;
import ru.vsu.portalforembroidery.model.dto.view.PostViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;

import java.util.List;

public interface PostService {

    int createPost(PostDto postDto);

    PostViewDto getPostViewById(int id);

    void updatePostById(int id, PostUpdateDto postUpdateDto);

    void deletePostById(int id);

    void likePostById(int id, LikeDto likeDto);

    void dislikePostById(int id, LikeDto likeDto);

    int countLikes(int id);

    ViewListPage<PostForListDto> getViewListPage(String page, String size);

    ViewListPage<CommentViewDto> getViewListPageOfComments(int id, String page, String size);

    List<PostForListDto> listPosts(Pageable pageable);

    int numberOfPosts();

}
