package ru.vsu.portalforembroidery.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.portalforembroidery.model.dto.DesignDto;
import ru.vsu.portalforembroidery.model.dto.DesignUpdateDto;
import ru.vsu.portalforembroidery.model.dto.view.*;
import ru.vsu.portalforembroidery.service.DesignService;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/designs")
public class DesignRestController {

    private final DesignService designService;

    @PostMapping
    public ResponseEntity<Integer> createDesign(@RequestBody @Valid final DesignDto designDto) {
        final int id = designService.createDesign(designDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public DesignViewDto getDesign(@PathVariable final int id) {
        return designService.getDesignViewById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDesign(@PathVariable final int id, @RequestBody @Valid final DesignUpdateDto designUpdateDto) {
        designService.updateDesignById(id, designUpdateDto);
        return new ResponseEntity<>("Design was updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteDesign(@PathVariable final int id) {
        designService.deleteDesignById(id);
    }

    @GetMapping
    public ViewListPage<DesignForListDto> getDesigns(@RequestParam(required = false) final Map<String, String> allParams) {
        return designService.getViewListPage(allParams.get("page"), allParams.get("size"));
    }

    @GetMapping("/{id}/files")
    public ViewListPage<FileForListDto> getDesignFiles(@PathVariable final int id,
                                                       @RequestParam(required = false) final Map<String, String> allParams) {
        return designService.getFileViewListPage(id, allParams.get("page"), allParams.get("size"));
    }

}
