package ru.vsu.portalforembroidery.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.portalforembroidery.model.dto.DesignerDesignDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignerDesignViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.service.DesignerDesignService;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/designer-designs")
public class DesignerDesignRestController {

    private final DesignerDesignService designerDesignService;

    @PostMapping
    public ResponseEntity<Integer> createDesignerDesign(@RequestBody @Valid final DesignerDesignDto designerDesignDto) {
        final int id = designerDesignService.createDesignerDesign(designerDesignDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public DesignerDesignViewDto getDesignerDesign(@PathVariable final int id) {
        return designerDesignService.getDesignerDesignViewById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDesignerDesign(@PathVariable final int id, @RequestBody @Valid final DesignerDesignDto designerDesignDto) {
        designerDesignService.updateDesignerDesignById(id, designerDesignDto);
        return new ResponseEntity<>("Designer Design was updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteDesignerDesign(@PathVariable final int id) {
        designerDesignService.deleteDesignerDesignById(id);
    }

    @GetMapping
    public ViewListPage<DesignerDesignViewDto> getDesignerDesigns(@RequestParam(required = false) final Map<String, String> allParams) {
        return designerDesignService.getViewListPage(allParams.get("page"), allParams.get("size"));
    }

}

