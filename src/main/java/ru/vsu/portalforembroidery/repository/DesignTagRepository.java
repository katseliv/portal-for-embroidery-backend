package ru.vsu.portalforembroidery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.portalforembroidery.model.entity.DesignTagEntity;
import ru.vsu.portalforembroidery.model.entity.DesignTagId;

@Repository
public interface DesignTagRepository extends JpaRepository<DesignTagEntity, DesignTagId> {
}
