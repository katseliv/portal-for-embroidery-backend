package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.model.dto.FolderDto;
import ru.vsu.portalforembroidery.model.dto.FolderUpdateDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignForListDto;
import ru.vsu.portalforembroidery.model.dto.view.FolderViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;

import java.util.List;

public interface FolderService {

    int createFolder(FolderDto folderDto);

    FolderViewDto getFolderViewById(int id);

    void updateFolderById(int id, FolderUpdateDto folderUpdateDto);

    void deleteFolderById(int id);

    ViewListPage<FolderViewDto> getViewListPage(String page, String size);

    ViewListPage<FolderViewDto> getViewListPage(int userId, String page, String size);

    ViewListPage<FolderViewDto> getChildrenFolderViewListPage(int folderId, String page, String size);

    ViewListPage<DesignForListDto> getDesignViewListPage(int id, String page, String size);

    List<FolderViewDto> listFolders(Pageable pageable);

    List<FolderViewDto> listFoldersByUser(int userId, Pageable pageable);

    int numberOfFolders();

    int numberOfFoldersByUser(int userId);

}
