package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.model.Provider;
import ru.vsu.portalforembroidery.model.dto.DesignerProfileDto;
import ru.vsu.portalforembroidery.model.dto.DesignerProfileRegistrationDto;
import ru.vsu.portalforembroidery.model.dto.view.DesignerProfileViewDto;
import ru.vsu.portalforembroidery.model.dto.view.FilteredViewListPage;
import ru.vsu.portalforembroidery.model.dto.view.PostForListDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;

import java.util.List;

public interface DesignerProfileService {

    int createDesignerProfile(DesignerProfileRegistrationDto designerProfileRegistrationDto, Provider provider);

    DesignerProfileViewDto getDesignerProfileViewById(int id);

    void updateDesignerProfileById(int id, DesignerProfileDto designerProfileDto);

    void deleteDesignerProfileById(int id);

    ViewListPage<DesignerProfileViewDto> getViewListPage(String page, String size);

    FilteredViewListPage<PostForListDto> getFilteredPostViewListPage(int designerId, String page, String size, String tagName);

    List<DesignerProfileViewDto> listDesignerProfiles(Pageable pageable);

    int numberOfDesignerProfiles();

    List<PostForListDto> listPosts(int designerId, Pageable pageable, String tagName);

}
