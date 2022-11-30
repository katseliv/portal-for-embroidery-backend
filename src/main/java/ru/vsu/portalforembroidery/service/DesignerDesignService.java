package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.model.dto.DesignerDesignDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignerDesignViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;

import java.util.List;

public interface DesignerDesignService {

    int createDesignerDesign(DesignerDesignDto designerDesignDto);

    DesignerDesignViewDto getDesignerDesignViewById(int id);

    void updateDesignerDesignById(int id, DesignerDesignDto designerDesignDto);

    void deleteDesignerDesignById(int id);

    ViewListPage<DesignerDesignViewDto> getViewListPage(String page, String size);

    List<DesignerDesignViewDto> listDesignerDesigns(Pageable pageable);

    int numberOfDesignerDesigns();

}
