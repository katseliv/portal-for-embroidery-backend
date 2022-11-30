package ru.vsu.portalforembroidery.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.portalforembroidery.model.dto.CommentDto;
import ru.vsu.portalforembroidery.model.dto.view.CommentViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.service.CommentService;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comments")
public class CommentRestController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Integer> createComment(@RequestBody @Valid final CommentDto commentDto) {
        final int id = commentService.createComment(commentDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public CommentViewDto getComment(@PathVariable final int id) {
        return commentService.getCommentViewById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(@PathVariable final int id, @RequestBody @Valid final CommentDto commentDto) {
        commentService.updateCommentById(id, commentDto);
        return new ResponseEntity<>("Comment was updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable final int id) {
        commentService.deleteCommentById(id);
    }

    @GetMapping
    public ViewListPage<CommentViewDto> getComments(@RequestParam(required = false) final Map<String, String> allParams) {
        return commentService.getViewListPage(allParams.get("page"), allParams.get("size"));
    }

}

