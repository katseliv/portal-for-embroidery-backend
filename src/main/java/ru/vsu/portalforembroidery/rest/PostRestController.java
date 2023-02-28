package ru.vsu.portalforembroidery.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.portalforembroidery.model.dto.LikeDto;
import ru.vsu.portalforembroidery.model.dto.PostDto;
import ru.vsu.portalforembroidery.model.dto.PostUpdateDto;
import ru.vsu.portalforembroidery.model.dto.TagDto;
import ru.vsu.portalforembroidery.model.dto.view.*;
import ru.vsu.portalforembroidery.service.PostService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostRestController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Integer> createPost(@RequestBody @Valid final PostDto postDto) {
        final int id = postService.createPost(postDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public PostViewDto getPost(@PathVariable final int id) {
        return postService.getPostViewById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable final int id, @RequestBody @Valid final PostUpdateDto postUpdateDto) {
        postService.updatePostById(id, postUpdateDto);
        return new ResponseEntity<>("Post was updated!", HttpStatus.OK);
    }

    @PutMapping("/{id}/tags")
    public ResponseEntity<String> updatePostByTags(@PathVariable final int id, @RequestBody @Valid final List<TagDto> tags) {
        postService.updatePostByIdAndTags(id, tags);
        return new ResponseEntity<>("Post was updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable final int id) {
        postService.deletePostById(id);
    }

    @PostMapping("/{id}/like")
    public void likePost(@PathVariable final int id, @RequestBody @Valid final LikeDto likeDto) {
        postService.likePostById(id, likeDto);
    }

    @PostMapping("/{id}/dislike")
    public void dislikePost(@PathVariable final int id, @RequestBody @Valid final LikeDto likeDto) {
        postService.dislikePostById(id, likeDto);
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<Integer> countLikesOfPost(@PathVariable final int id) {
        return new ResponseEntity<>(postService.countLikes(id), HttpStatus.OK);
    }

    @GetMapping
    public FilteredViewListPage<PostForListDto> getPosts(@RequestParam(required = false) final Map<String, String> allParams) {
        return postService.getFilteredPostViewListPage(allParams.get("page"), allParams.get("size"), allParams.get("tagName"));
    }

    @GetMapping("/{id}/comments")
    public ViewListPage<CommentViewDto> getCommentsPost(@PathVariable final int id,
                                                        @RequestParam(required = false) final Map<String, String> allParams) {
        return postService.getViewListPageOfComments(id, allParams.get("page"), allParams.get("size"));
    }

}
