package ru.vsu.portalforembroidery.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.portalforembroidery.model.dto.TagDto;
import ru.vsu.portalforembroidery.model.dto.view.TagViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.service.TagService;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/Tags")
public class TagRestController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<Integer> createTag(@RequestBody @Valid final TagDto tagDto) {
        final int id = tagService.createTag(tagDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public TagViewDto getTag(@PathVariable final int id) {
        return tagService.getTagViewById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTag(@PathVariable final int id, @RequestBody @Valid final TagDto tagDto) {
        tagService.updateTagById(id, tagDto);
        return new ResponseEntity<>("Tag was updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable final int id) {
        tagService.deleteTagById(id);
    }

    @GetMapping
    public ViewListPage<TagViewDto> getTags(@RequestParam(required = false) final Map<String, String> allParams) {
        return tagService.getViewListPage(allParams.get("page"), allParams.get("size"));
    }

}
