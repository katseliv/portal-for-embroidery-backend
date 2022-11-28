package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.model.dto.DesignDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;

import java.util.List;

public interface DesignService {

    int createDesign(DesignDto designDto);

    DesignViewDto getDesignViewById(int id);

    void updateDesignById(int id, DesignDto designDto);

    void deleteDesignById(int id);

    ViewListPage<DesignViewDto> getViewListPage(String page, String size);

    List<DesignViewDto> listDesigns(Pageable pageable);

    int numberOfDesigns();

}
