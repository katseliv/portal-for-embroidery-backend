package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.model.dto.FileDto;
import ru.vsu.portalforembroidery.model.dto.FileUpdateDto;
import ru.vsu.portalforembroidery.model.dto.view.FileForListDto;
import ru.vsu.portalforembroidery.model.dto.view.FileViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;

import java.util.List;

public interface FileService {

    int createFile(FileDto fileDto);

    FileViewDto getFileViewById(int id);

    void updateFileById(int id, FileUpdateDto fileUpdateDto);

    void deleteFileById(int id);

    ViewListPage<FileForListDto> getViewListPage(String page, String size);

    ViewListPage<FileForListDto> getViewListPage(int folderId, String page, String size);

    List<FileForListDto> listFiles(Pageable pageable);

    List<FileForListDto> listFilesByFolder(int folderId, Pageable pageable);

    int numberOfFiles();

    int numberOfFilesByFolder(int folderId);

}
