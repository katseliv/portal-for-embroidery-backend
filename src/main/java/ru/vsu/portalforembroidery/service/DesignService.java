package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.model.dto.DesignDto;
import ru.vsu.portalforembroidery.model.dto.DesignUpdateDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignForListDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignViewDto;
import ru.vsu.portalforembroidery.model.dto.view.FileForListDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;

import java.util.List;

public interface DesignService {

    int createDesign(DesignDto designDto);

    DesignViewDto getDesignViewById(int id);

    void updateDesignById(int id, DesignUpdateDto designUpdateDto);

    void deleteDesignById(int id);

    ViewListPage<DesignForListDto> getViewListPage(String page, String size);

    ViewListPage<DesignForListDto> getViewListPage(int folderId, String page, String size);

    ViewListPage<FileForListDto> getFileViewListPage(int id, String page, String size);

    List<DesignForListDto> listDesigns(Pageable pageable);

    List<DesignForListDto> listDesignsByFolder(int folderId, Pageable pageable);

    int numberOfDesigns();

    int numberOfDesignsByFolder(int folderId);

}
