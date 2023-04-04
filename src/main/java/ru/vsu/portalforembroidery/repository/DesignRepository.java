package ru.vsu.portalforembroidery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.portalforembroidery.model.entity.DesignEntity;

@Repository
public interface DesignRepository extends JpaRepository<DesignEntity, Integer> {

    Page<DesignEntity> findAllByFolderId(int folderId, Pageable pageable);

    long countByFolderId(int folderId);

}
