package ru.vsu.portalforembroidery.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.portalforembroidery.model.Provider;
import ru.vsu.portalforembroidery.model.dto.DesignerProfileDto;
import ru.vsu.portalforembroidery.model.dto.DesignerProfileRegistrationDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignerProfileViewDto;
import ru.vsu.portalforembroidery.model.dto.view.FilteredViewListPage;
import ru.vsu.portalforembroidery.model.dto.view.PostForListDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.service.DesignerProfileService;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/designer-profiles")
public class DesignerProfileRestController {

    private final DesignerProfileService designerProfileService;

    @PostMapping
    public ResponseEntity<Integer> createDesignerProfile(@RequestBody @Valid final DesignerProfileRegistrationDto designerProfileRegistrationDto) {
        final int id = designerProfileService.createDesignerProfile(designerProfileRegistrationDto, Provider.LOCAL);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public DesignerProfileViewDto getDesignerProfile(@PathVariable final int id) {
        return designerProfileService.getDesignerProfileViewById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDesignerProfile(@PathVariable final int id, @RequestBody @Valid final DesignerProfileDto designerProfileDto) {
        designerProfileService.updateDesignerProfileById(id, designerProfileDto);
        return new ResponseEntity<>("Designer Profile was updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteDesignerProfile(@PathVariable final int id) {
        designerProfileService.deleteDesignerProfileById(id);
    }

    @GetMapping
    public ViewListPage<DesignerProfileViewDto> getDesignerProfiles(@RequestParam(required = false) final Map<String, String> allParams) {
        return designerProfileService.getViewListPage(allParams.get("page"), allParams.get("size"));
    }

    @GetMapping("/{id}/posts")
    public FilteredViewListPage<PostForListDto> getDesignerPosts(@PathVariable final int id, @RequestParam(required = false) final Map<String, String> allParams) {
        return designerProfileService.getFilteredPostViewListPage(id, allParams.get("page"), allParams.get("size"), allParams.get("tagName"));
    }

}

