package ru.vsu.portalforembroidery.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.portalforembroidery.model.dto.FileDto;
import ru.vsu.portalforembroidery.model.dto.view.FileForListDto;
import ru.vsu.portalforembroidery.model.dto.view.FileViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.service.FileService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Map;

@ApiIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/files")
public class FileRestController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<Integer> createFile(@RequestBody @Valid final FileDto fileDto) {
        final int id = fileService.createFile(fileDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public FileViewDto getFile(@PathVariable final int id) {
        return fileService.getFileViewById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFile(@PathVariable final int id, @RequestBody @Valid final FileDto fileDto) {
        fileService.updateFileById(id, fileDto);
        return new ResponseEntity<>("File was updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable final int id) {
        fileService.deleteFileById(id);
    }

    @GetMapping
    public ViewListPage<FileForListDto> getFiles(@RequestParam(required = false) final Map<String, String> allParams) {
        return fileService.getViewListPage(allParams.get("page"), allParams.get("size"));
    }

}
