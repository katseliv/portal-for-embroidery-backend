package ru.vsu.portalforembroidery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.portalforembroidery.model.entity.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    Page<FileEntity> findAllByFolderId(int folderId, Pageable pageable);

    long countByFolderId(int folderId);

}
