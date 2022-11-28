package ru.vsu.portalforembroidery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vsu.portalforembroidery.model.entity.LikeEntity;
import ru.vsu.portalforembroidery.model.entity.LikeId;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, LikeId> {

    @Modifying
    @Query("UPDATE likes SET deleted = TRUE WHERE id = ?1")
    void markAsDeletedById(LikeId id);

}
