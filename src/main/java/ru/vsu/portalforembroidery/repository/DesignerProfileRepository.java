package ru.vsu.portalforembroidery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.portalforembroidery.model.entity.DesignerProfileEntity;

import java.util.Optional;

@Repository
public interface DesignerProfileRepository extends JpaRepository<DesignerProfileEntity, Integer> {

    Optional<DesignerProfileEntity> findByEmail(String email);

    boolean existsByUsername(String username);

}
