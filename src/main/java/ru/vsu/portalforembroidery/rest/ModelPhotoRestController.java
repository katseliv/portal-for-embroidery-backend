package ru.vsu.portalforembroidery.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.portalforembroidery.model.dto.ModelPhotoDto;
import ru.vsu.portalforembroidery.model.dto.view.ModelPhotoViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.service.ModelPhotoService;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/model-photos")
public class ModelPhotoRestController {

    private final ModelPhotoService modelPhotoService;

    @PostMapping
    public ResponseEntity<Integer> createModelPhoto(@RequestBody @Valid final ModelPhotoDto modelPhotoDto) {
        final int id = modelPhotoService.createModelPhoto(modelPhotoDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ModelPhotoViewDto getModelPhoto(@PathVariable final int id) {
        return modelPhotoService.getModelPhotoViewById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateModelPhoto(@PathVariable final int id, @RequestBody @Valid final ModelPhotoDto modelPhotoDto) {
        modelPhotoService.updateModelPhotoById(id, modelPhotoDto);
        return new ResponseEntity<>("Model Photo was updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteModelPhoto(@PathVariable final int id) {
        modelPhotoService.deleteModelPhotoById(id);
    }

    @GetMapping
    public ViewListPage<ModelPhotoViewDto> getModelPhotos(@RequestParam(required = false) final Map<String, String> allParams) {
        return modelPhotoService.getViewListPage(allParams.get("page"), allParams.get("size"));
    }

}
