package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.model.dto.FolderDto;
import ru.vsu.portalforembroidery.model.dto.view.FileForListDto;
import ru.vsu.portalforembroidery.model.dto.view.FolderViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;

import java.util.List;

public interface FolderService {

    int createFolder(FolderDto folderDto);

    FolderViewDto getFolderViewById(int id);

    void updateFolderById(int id, FolderDto folderDto);

    void deleteFolderById(int id);

    ViewListPage<FolderViewDto> getViewListPage(String page, String size);

    ViewListPage<FolderViewDto> getViewListPage(int userId, String page, String size);

    ViewListPage<FolderViewDto> getViewListPageOfChildrenFolders(int folderId, String page, String size);

    ViewListPage<FileForListDto> getViewListPageOfFiles(int id, String page, String size);

    List<FolderViewDto> listFolders(Pageable pageable);

    List<FolderViewDto> listFoldersByUser(int userId, Pageable pageable);

    int numberOfFolders();

    int numberOfFoldersByUser(int userId);

}
