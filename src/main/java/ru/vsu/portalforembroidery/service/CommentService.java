package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.model.dto.CommentDto;
import ru.vsu.portalforembroidery.model.dto.view.CommentViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;

import java.util.List;

public interface CommentService {

    int createComment(CommentDto commentDto);

    CommentViewDto getCommentViewById(int id);

    void updateCommentById(int id, CommentDto commentDto);

    void deleteCommentById(int id);

    ViewListPage<CommentViewDto> getViewListPage(String page, String size);

    List<CommentViewDto> listComments(Pageable pageable);

    int numberOfComments();

}
