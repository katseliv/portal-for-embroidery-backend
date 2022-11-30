package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.model.dto.TagDto;
import ru.vsu.portalforembroidery.model.dto.view.TagViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;

import java.util.List;

public interface TagService {

    int createTag(TagDto TagDto);

    TagViewDto getTagViewById(int id);

    void updateTagById(int id, TagDto TagDto);

    void deleteTagById(int id);

    ViewListPage<TagViewDto> getViewListPage(String page, String size);

    List<TagViewDto> listTags(Pageable pageable);

    int numberOfTags();
    
}
