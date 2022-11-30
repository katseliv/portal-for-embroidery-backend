package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.model.dto.ModelPhotoDto;
import ru.vsu.portalforembroidery.model.dto.view.ModelPhotoViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;

import java.util.List;

public interface ModelPhotoService {

    int createModelPhoto(ModelPhotoDto ModelPhotoDto);

    ModelPhotoViewDto getModelPhotoViewById(int id);

    void updateModelPhotoById(int id, ModelPhotoDto ModelPhotoDto);

    void deleteModelPhotoById(int id);

    ViewListPage<ModelPhotoViewDto> getViewListPage(String page, String size);

    List<ModelPhotoViewDto> listModelPhotos(Pageable pageable);

    int numberOfModelPhotos();
    
}
