package ru.vsu.portalforembroidery.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.portalforembroidery.model.Role;
import ru.vsu.portalforembroidery.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllByRole(Role role);

    List<UserEntity> findAllByRole(Role role, Pageable pageable);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
