package ru.vsu.portalforembroidery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.portalforembroidery.model.entity.ModelPhotoEntity;

@Repository
public interface ModelPhotoRepository extends JpaRepository<ModelPhotoEntity, Integer> {
}
