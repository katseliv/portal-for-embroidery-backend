package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.model.dto.CommentDto;
import ru.vsu.portalforembroidery.model.dto.CommentUpdateDto;
import ru.vsu.portalforembroidery.model.dto.view.CommentViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;

import java.util.List;

public interface CommentService {

    int createComment(CommentDto commentDto);

    CommentViewDto getCommentViewById(int id);

    void updateCommentById(int id, CommentUpdateDto commentUpdateDto);

    void deleteCommentById(int id);

    ViewListPage<CommentViewDto> getViewListPage(String page, String size);

    ViewListPage<CommentViewDto> getViewListPage(int postId, String page, String size);

    List<CommentViewDto> listComments(Pageable pageable);

    List<CommentViewDto> listCommentsByPost(int postId, Pageable pageable);

    int numberOfComments();

    int numberOfCommentsByPost(int postId);

}
