package ru.vsu.portalforembroidery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.portalforembroidery.model.entity.FolderEntity;

@Repository
public interface FolderRepository extends JpaRepository<FolderEntity, Integer> {

    Page<FolderEntity> findAllByCreatorDesignerId(int userId, Pageable pageable);

    Page<FolderEntity> findAllByCreatorDesignerIdAndParentFolderIdIsNull(int userId, Pageable pageable);

    long countByCreatorDesignerId(int userId);

}
