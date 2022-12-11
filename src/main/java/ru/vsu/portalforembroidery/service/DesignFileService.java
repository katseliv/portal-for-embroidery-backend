package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.model.dto.DesignFileDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignFileViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;

import java.util.List;

public interface DesignFileService {

    int createDesignFile(DesignFileDto designFileDto);

    DesignFileViewDto getDesignFileViewById(int id);

    void updateDesignFileById(int id, DesignFileDto designFileDto);

    void deleteDesignFileById(int id);

    ViewListPage<DesignFileViewDto> getViewListPage(String page, String size);

    List<DesignFileViewDto> listDesignFiles(Pageable pageable);

    int numberOfDesignFiles();

}
