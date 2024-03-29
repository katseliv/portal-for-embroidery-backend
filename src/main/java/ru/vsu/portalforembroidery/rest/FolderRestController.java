package ru.vsu.portalforembroidery.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.portalforembroidery.model.dto.FolderDto;
import ru.vsu.portalforembroidery.model.dto.FolderUpdateDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignForListDto;
import ru.vsu.portalforembroidery.model.dto.view.FileForListDto;
import ru.vsu.portalforembroidery.model.dto.view.FolderViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.service.FolderService;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/folders")
public class FolderRestController {

    private final FolderService folderService;

    @PostMapping
    public ResponseEntity<Integer> createFolder(@RequestBody @Valid final FolderDto folderDto) {
        final int id = folderService.createFolder(folderDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public FolderViewDto getFolder(@PathVariable final int id) {
        return folderService.getFolderViewById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFolder(@PathVariable final int id, @RequestBody @Valid final FolderUpdateDto folderUpdateDto) {
        folderService.updateFolderById(id, folderUpdateDto);
        return new ResponseEntity<>("Folder was updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteFolder(@PathVariable final int id) {
        folderService.deleteFolderById(id);
    }

    @GetMapping
    public ViewListPage<FolderViewDto> getFolders(@RequestParam(required = false) final Map<String, String> allParams) {
        return folderService.getViewListPage(allParams.get("page"), allParams.get("size"));
    }

    @GetMapping("/{id}/folders")
    public ViewListPage<FolderViewDto> getParentFolderFolders(@PathVariable final int id,
                                                              @RequestParam(required = false) final Map<String, String> allParams) {
        return folderService.getChildrenFolderViewListPage(id, allParams.get("page"), allParams.get("size"));
    }

    @GetMapping("/{id}/designs")
    public ViewListPage<DesignForListDto> getFolderDesigns(@PathVariable final int id,
                                                           @RequestParam(required = false) final Map<String, String> allParams) {
        return folderService.getDesignViewListPage(id, allParams.get("page"), allParams.get("size"));
    }

}
